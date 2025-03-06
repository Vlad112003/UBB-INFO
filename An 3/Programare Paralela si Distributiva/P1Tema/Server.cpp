#include <algorithm>
#include <iostream>
#include <winsock2.h>
#include <thread>
#include <vector>
#include <fstream>
#include <chrono>
#include <string>
#include <sstream>
#include <ws2tcpip.h>
#include <future>
#include <mutex>
#include <condition_variable>
#include <queue>
#include <unordered_map>
#include <unordered_set>
#include <atomic>
#include <iomanip>
#include <functional>

#pragma comment(lib, "Ws2_32.lib")

using namespace std;

atomic<bool> serverRunning(true);

const int BUFFER_SIZE = 1024;
const int PORT = 8080;
const string SERVER_IP = "127.0.0.1";


mutex logMutex;
mutex countryScoresMutex;
mutex blacklistMutex;
atomic<bool> processingDone(false);
condition_variable cv;

struct Competitor {
    int id;
    int score;
    string country;
};

vector<Competitor> allCompetitors;  // Global vector to store all competitors across clients

struct Ranking {
    string country;
    int total_score;
};

class ThreadSafeQueue {
private:
    queue<Competitor> queue;
    mutex mtx;
    condition_variable cv;
public:
    void push(const Competitor& competitor) {
        lock_guard<mutex> lock(mtx);
        queue.push(competitor);
        cv.notify_one();
    }

    bool pop(Competitor& competitor) {
        unique_lock<mutex> lock(mtx);
        cv.wait(lock, [this] { return !queue.empty(); });
        competitor = queue.front();
        queue.pop();
        return true;
    }
};

class ThreadPool {
public:
    ThreadPool(size_t numThreads);
    ~ThreadPool();
    void enqueue(function<void()> task);

private:
    vector<thread> workers;
    queue<function<void()>> tasks;
    mutex queueMutex;
    condition_variable condition;
    bool stop;
};

ThreadPool::ThreadPool(size_t numThreads) : stop(false) {
    for (size_t i = 0; i < numThreads; ++i) {
        workers.emplace_back([this] {
            for (;;) {
                function<void()> task;
                {
                    unique_lock<mutex> lock(this->queueMutex);
                    this->condition.wait(lock, [this] { return this->stop || !this->tasks.empty(); });
                    if (this->stop && this->tasks.empty()) return;
                    task = move(this->tasks.front());
                    this->tasks.pop();
                }
                task();
            }
        });
    }
}

ThreadPool::~ThreadPool() {
    {
        unique_lock<mutex> lock(queueMutex);
        stop = true;
    }
    condition.notify_all();
    for (thread &worker : workers) worker.join();
}

void ThreadPool::enqueue(function<void()> task) {
    {
        unique_lock<mutex> lock(queueMutex);
        tasks.push(move(task));
    }
    condition.notify_one();
}

ThreadSafeQueue competitorQueue;
unordered_map<string, int> countryScores;
unordered_set<int> blacklist;
future<vector<Ranking>> rankingsFuture;
chrono::steady_clock::time_point lastRankingTime;

string getCurrentTime() {
    auto now = chrono::system_clock::now();
    auto in_time_t = chrono::system_clock::to_time_t(now);
    stringstream ss;
    ss << put_time(localtime(&in_time_t), "%Y-%m-%d %X");
    return ss.str();
}

void logMessage(const string& message) {
    lock_guard<mutex> lock(logMutex);
    ofstream logFile("server_log.txt", ios::app);
    logFile << "[" << getCurrentTime() << "] " << message << endl;
     cout << "[" << getCurrentTime() << "] " << message << endl;
}

void addToBlacklist(const Competitor& competitor) {
    ofstream clearBlacklist("blacklist.txt", ios::out);
    clearBlacklist<<""; // Clear the blacklist file
    clearBlacklist.flush();
    clearBlacklist.close();
    lock_guard<mutex> lock(blacklistMutex);
    blacklist.insert(competitor.id);
    ofstream blacklistFile("blacklist.txt", ios::app);
    blacklistFile << competitor.id << " " << competitor.country << "\n";
    blacklistFile.close();
    logMessage("[Server] Added competitor ID " + to_string(competitor.id) + " to blacklist.");
}

void processCompetitorData(int deltaT) {
    while (!processingDone) {
        Competitor competitor;
        if (competitorQueue.pop(competitor)) {
            if (competitor.score == -1) {
                addToBlacklist(competitor);
            } else {
                lock_guard<mutex> lock(countryScoresMutex);
                countryScores[competitor.country] += competitor.score;
                logMessage("[Server] Processed competitor ID " + to_string(competitor.id) + " from country " + competitor.country);
            }
            this_thread::sleep_for(chrono::milliseconds(deltaT));
        }
    }
}

vector<Ranking> calculateRankings() {
    vector<Ranking> rankings;
    {
        lock_guard<mutex> lock(countryScoresMutex);
        for (const auto& entry : countryScores) {
            rankings.push_back({entry.first, entry.second});
        }
    }
    sort(rankings.begin(), rankings.end(), [](const Ranking& a, const Ranking& b) {
        return a.total_score > b.total_score;
    });
    logMessage("[Server] Calculated rankings.");
    return rankings;
}

void saveRankingsToFile(const vector<Ranking>& rankings, const string& filename) {
    // Specify the full path for the file
    string fullPath = "D:\\An3Sem1\\Programare Paralela si Distributiva\\P1Tema\\" + filename;
    ofstream outFile(filename);

    // Print the rankings to the terminal for debugging
    logMessage("[Server] Country Rankings:");
    for (const auto& ranking : rankings) {
        cout << ranking.country << ": " << ranking.total_score << "\n";
        // Write the rankings to the file
        outFile << ranking.country << ": " << ranking.total_score << "\n";
    }

    outFile.flush(); // Ensure the buffer is flushed
    outFile.close(); // Ensure the file is properly closed
    logMessage("[Server] Finished writing rankings to file: " + filename);
}

void saveCompetitorsToFile(vector<Competitor>& competitors, const string& filename) {
    string fullPath = "D:\\An3Sem1\\Programare Paralela si Distributiva\\P1Tema\\" + filename;
    ofstream outFile(filename);

    logMessage("[Server] Competitors Ranking:");
    for (const auto& competitor : competitors) {
//        cout << competitor.id << " " << competitor.score << " " << competitor.country << "\n";
        outFile << competitor.id << " " << competitor.score << " " << competitor.country << "\n";
    }

    outFile.flush();
    outFile.close();
    logMessage("[Server] Finished writing competitors to file: " + filename);
}

void sendFile(SOCKET clientSocket, const string& filename) {
    ifstream inFile(filename, ios::binary);
    if (!inFile) {
        logMessage("[Server] Error opening file: " + filename);
        return;
    }
    char buffer[BUFFER_SIZE];
    while (inFile.read(buffer, BUFFER_SIZE)) {
        send(clientSocket, buffer, inFile.gcount(), 0);
    }
    inFile.close();
    logMessage("[Server] Sent file: " + filename);
}

void handleClient(SOCKET clientSocket) {
    char buffer[BUFFER_SIZE];
    int bytesReceived;
    vector<Competitor> competitors;

    // Receive data from client
    while ((bytesReceived = recv(clientSocket, buffer, BUFFER_SIZE - 1, 0)) > 0) {
        buffer[bytesReceived] = '\0';
        string data(buffer);
        if (data == "DONE") {
            logMessage("[Server] Received DONE message from client.");
            break;
        }
        if (data == "STOP_SERVER") {
            logMessage("[Server] Received STOP_SERVER message from client.");
            serverRunning = false;
            break;
        }
        istringstream stream(data);
        string country;
        int id, score;

        while (stream >> id >> score >> country) {
            // Check if competitor is blacklisted
            {
                lock_guard<mutex> lock(blacklistMutex);
                if (blacklist.find(id) != blacklist.end()) {
                    logMessage("[Server] Ignored blacklisted competitor ID " + to_string(id) + " from country " + country);
                    continue;
                }
            }

            // Push competitor data to queue
            Competitor competitor{id, score, country};
            competitorQueue.push(competitor);
            allCompetitors.push_back(competitor);
            logMessage("[Server] Received data from " + country + ": " + to_string(id) + " " + to_string(score));
        }
    }

    // Handle final results request
    bytesReceived = recv(clientSocket, buffer, BUFFER_SIZE - 1, 0);
    buffer[bytesReceived] = '\0';
    string request(buffer);
    if (request == "GET_FINAL_RESULTS") {
        logMessage("[Server] Received final results request from client.");

        // Calculate and save final rankings
        rankingsFuture = async(launch::async, calculateRankings);
        vector<Ranking> rankings = rankingsFuture.get();
        std::sort(allCompetitors.begin(), allCompetitors.end(), [](const Competitor& a, const Competitor& b) {
            return a.score > b.score;
        });
        saveRankingsToFile(rankings, "final_country_rank.txt");
        saveCompetitorsToFile(allCompetitors, "final_competitors_ranking.txt");

        // Send final rankings files to client
        sendFile(clientSocket, "final_competitors_ranking.txt");
        sendFile(clientSocket, "final_country_rank.txt");
    }


    closesocket(clientSocket);
}

//compare output function
bool compareSHA(const string& file1, const string& file2) {
    ifstream f1(file1);
    ifstream f2(file2);
    if (!f1 || !f2) {
        cerr << "Error opening files." << endl;
        return false;
    }
    string line1, line2;
    while (true) {
        bool f1End = !getline(f1, line1);
        bool f2End = !getline(f2, line2);
        if (f1End && f2End) {
            return true;
        }
        if (f1End || f2End) {
            return false;
        }
        if (line1 != line2) {
            return false;
        }
    }
}

int main(int argc, char* argv[]) {
    if (argc < 5) {
        cerr << "Usage: " << argv[0] << " <num_readers> <num_workers> <deltaT> <compare=1/0>" << endl;
        return 1;
    }

    auto start = chrono::high_resolution_clock::now();

    int PR = stoi(argv[1]);
    int PW = stoi(argv[2]);
    int DT = stoi(argv[3]);
    int compare = stoi(argv[4]);

    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        cerr << "[Server] WSAStartup failed." << endl;
        return 1;
    }

    SOCKET serverSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (serverSocket == INVALID_SOCKET) {
        cerr << "[Server] Socket creation failed." << endl;
        WSACleanup();
        return 1;
    }

    sockaddr_in serverAddr;
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(PORT);
    inet_pton(AF_INET, SERVER_IP.c_str(), &serverAddr.sin_addr);

    if (bind(serverSocket, (sockaddr*)&serverAddr, sizeof(serverAddr)) == SOCKET_ERROR) {
        cerr << "[Server] Binding failed." << endl;
        closesocket(serverSocket);
        WSACleanup();
        return 1;
    }

    if (listen(serverSocket, SOMAXCONN) == SOCKET_ERROR) {
        cerr << "[Server] Listening failed." << endl;
        closesocket(serverSocket);
        WSACleanup();
        return 1;
    }

    logMessage("[Server] Started on port 8080");

    // Start the data processing threads
    ThreadPool processingPool(PW);
    for (int i = 0; i < PW; ++i) {
        processingPool.enqueue([DT]() { processCompetitorData(DT); });
    }

    // Handle clients concurrently with thread pool
    ThreadPool clientPool(PR);
    for (int i = 0; i < PR; ++i) {
        clientPool.enqueue([&]() {
            while (serverRunning) {
                SOCKET clientSocket = accept(serverSocket, NULL, NULL);
                if (clientSocket == INVALID_SOCKET) {
                    if (serverRunning) {
                        cerr << "[Server] Accepting connection failed." << endl;
                    }
                    continue;
                }
                logMessage("[Server] New client connected.");
                handleClient(clientSocket);
            }
        });
    }



    // Wait for the server to stop
    while (serverRunning) {
        this_thread::sleep_for(chrono::milliseconds(100));
    }



    // Join all threads
    processingDone = true;
    cv.notify_all();
    closesocket(serverSocket);
    WSACleanup();

    if (compare == 1)
    {
        if (compareSHA("final_competitors_ranking.txt", "expected_competitor.txt")) {
            cout<<"[Server] Competitor Ranking == expected.\n";
        }
        else {
            cout<<"[Server] Competitor Ranking != expected.\n";
        }
        if (compareSHA("final_country_rank.txt", "expected_country.txt")) {
            cout<<"[Server] Country Ranking == expected.\n";
        }
        else {
            cout<<"[Server] Country Ranking != expected.\n";
        }
    }

    if (!serverRunning)
    {
        exit(0);
    }

    return 0;
}