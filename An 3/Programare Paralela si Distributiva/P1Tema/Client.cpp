#include <iostream>
#include <winsock2.h>
#include <thread>
#include <vector>
#include <fstream>
#include <chrono>
#include <mutex>
#include <string>
#include <sstream>
#include <ws2tcpip.h>
#include <iomanip>
#include <unordered_map>

#pragma comment(lib, "Ws2_32.lib")

using namespace std;

const int BUFFER_SIZE = 1024;
const int PORT = 8080;
const string SERVER_IP = "127.0.0.1";

mutex logMutex;

string getCurrentTime() {
    auto now = chrono::system_clock::now();
    auto in_time_t = chrono::system_clock::to_time_t(now);
    stringstream ss;
    ss << put_time(localtime(&in_time_t), "%Y-%m-%d %X");
    return ss.str();
}

void logMessage(const string& message) {
    lock_guard<mutex> lock(logMutex);
    ofstream logFile("client_log.txt", ios::app);
    logFile << "[" << getCurrentTime() << "] " << message << endl;
}

void sendData(SOCKET clientSocket, const unordered_map<string, vector<pair<int, int>>>& data, int delay) {
    for (const auto& countryData : data) {
        const string& country = countryData.first;
        const vector<pair<int, int>>& competitors = countryData.second;

        for (size_t i = 0; i < competitors.size(); i += 20) {
            stringstream ss;
            for (size_t j = i; j < i + 20 && j < competitors.size(); ++j) {
                ss << competitors[j].first << " " << competitors[j].second << " " << country << "\n";
            }
            string block = ss.str();

            // Send data block to server
            send(clientSocket, block.c_str(), block.size(), 0);
            logMessage("[Client] Sent data block to server: " + block);

            this_thread::sleep_for(chrono::seconds(delay));
        }
    }

    // Send DONE signal
    string doneMessage = "DONE";
    send(clientSocket, doneMessage.c_str(), doneMessage.size(), 0);
    logMessage("[Client] Sent DONE message to server.");
}

void receiveFile(SOCKET clientSocket, const string& filename) {
    ofstream outFile(filename, ios::binary);
    char buffer[BUFFER_SIZE];
    int bytesReceived;
    while ((bytesReceived = recv(clientSocket, buffer, BUFFER_SIZE, 0)) > 0) {
        outFile.write(buffer, bytesReceived);
    }
    outFile.close();
    logMessage("[Client] Received file: " + filename);
}

void requestFinalResults(SOCKET clientSocket) {
    string request = "GET_FINAL_RESULTS";
    send(clientSocket, request.c_str(), request.size(), 0);
    logMessage("[Client] Requested final results from server.");

    // Receive final results files
    receiveFile(clientSocket, "final_competitors_ranking.txt");
    receiveFile(clientSocket, "final_country_ranking.txt");
}

unordered_map<string, vector<pair<int, int>>> loadData(const string& filename) {
    unordered_map<string, vector<pair<int, int>>> data;
    ifstream inputFile(filename);
    if (!inputFile) {
        cerr << "Error opening file: " << filename << endl;
        return data;
    }

    int id, score;
    string country;
    while (inputFile >> id >> score >> country) {
        data[country].emplace_back(id, score);
    }
    return data;
}

void sendStopServerMessage() {
    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        cerr << "WSAStartup failed." << endl;
        return;
    }

    SOCKET clientSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (clientSocket == INVALID_SOCKET) {
        cerr << "Socket creation failed." << endl;
        WSACleanup();
        return;
    }

    sockaddr_in serverAddr;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(PORT);
    inet_pton(AF_INET, SERVER_IP.c_str(), &serverAddr.sin_addr);

    if (connect(clientSocket, (sockaddr*)&serverAddr, sizeof(serverAddr)) == SOCKET_ERROR) {
        cerr << "Connection to server failed." << endl;
        closesocket(clientSocket);
        WSACleanup();
        return;
    }

    const char* stopMessage = "STOP_SERVER";
    send(clientSocket, stopMessage, strlen(stopMessage), 0);

    closesocket(clientSocket);
    WSACleanup();
}

int main(int argc, char* argv[]) {
    if (argc < 2) {
        cerr << "Usage: " << argv[0] << " <file_path>" << endl;
        return 1;
    }

    string filepath = argv[1];


    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        cerr << "[Client] WSAStartup failed." << endl;
        return 1;
    }

    SOCKET clientSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (clientSocket == INVALID_SOCKET) {
        cerr << "[Client] Socket creation failed." << endl;
        WSACleanup();
        return 1;
    }

    sockaddr_in serverAddr;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(PORT);
    inet_pton(AF_INET, SERVER_IP.c_str(), &serverAddr.sin_addr);

    if (connect(clientSocket, (sockaddr*)&serverAddr, sizeof(serverAddr)) == SOCKET_ERROR) {
        cerr << "[Client] Connection failed." << endl;
        closesocket(clientSocket);
        WSACleanup();
        return 1;
    }

    logMessage("[Client] Connected to server.");

    // Load data from file
    unordered_map<string, vector<pair<int, int>>> data = loadData(filepath);

    // Send data to server
    sendData(clientSocket, data, 1);

    // Request and receive final results
    requestFinalResults(clientSocket);

    closesocket(clientSocket);
    WSACleanup();

    // Send STOP_SERVER message
    if (filepath == "Italy.txt")
        sendStopServerMessage();

    return 0;
}