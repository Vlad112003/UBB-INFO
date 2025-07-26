#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <unordered_map>
#include <queue>
#include <list>
#include <mutex>
#include <thread>
#include <condition_variable>
#include <atomic>
#include <random>
#include <algorithm>
#include <chrono>

using namespace std;

struct Node {
    int id;
    int score;
    Node* next;

    Node(int id, int score) : id(id), score(score), next(nullptr) {}
};

// thread-safe list for leaderboard
class ThreadSafeList {
private:
    Node* head;
    mutex mtx;

public:
    ThreadSafeList() : head(nullptr) {}

    // insert in a sorted list in descending order
    void insertOrUpdate(int id, int score) {
        lock_guard<mutex> lock(mtx);

        Node** curr = &head;
        while (*curr && (*curr)->id != id && (*curr)->score >= score) {
            curr = &((*curr)->next);
        }

        if (*curr && (*curr)->id == id) {
            // node found, update the score
            if (score == -1) {
                //delete node
                Node* toDelete = *curr;
                *curr = (*curr)->next;
                delete toDelete;
            } else {
                (*curr)->score += score;
            }
        } else {
            // Adaugă un nou nod
            Node* newNode = new Node(id, score);
            newNode->next = *curr;
            *curr = newNode;
        }
    }

    void writeToFile(const string& filename) {
        lock_guard<mutex> lock(mtx);
        ofstream outputFile(filename);
        if (!outputFile) {
            cerr << "Error opening output file." << endl;
            return;
        }

        Node* curr = head;
        while (curr) {
            outputFile << "ID: " << curr->id << ", Score: " << curr->score << endl;
            curr = curr->next;
        }

        outputFile.close();
    }
};

/// thread-safe queue
class ThreadSafeQueue {
private:
    queue<pair<int, int>> q;
    mutex mtx;
    condition_variable cv;
    bool finished = false;

public:
    void push(pair<int, int> data) {
        {
            lock_guard<mutex> lock(mtx);
            q.push(data);
        }
        cv.notify_one();
    }

    bool pop(pair<int, int>& data) {
        unique_lock<mutex> lock(mtx);
        while (q.empty() && !finished) {
            cv.wait(lock);
        }

        if (q.empty() && finished) {
            return false;
        }

        data = q.front();
        q.pop();
        return true;
    }

    void setFinished() {
        {
            lock_guard<mutex> lock(mtx);
            finished = true;
        }
        cv.notify_all();
    }
};

// generate test data
void generateTestData(const vector<string>& countries, int problems, int minParticipants, int maxParticipants) {
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> participantsDist(minParticipants, maxParticipants);
    uniform_int_distribution<> scoreDist(0, 100);
    uniform_int_distribution<> fraudDist(1, 100);

    for (const auto& country : countries) {
        int participants = participantsDist(gen);

        for (int p = 1; p <= problems; ++p) {
            string filename = "Rezultate" + country + "_P" + to_string(p) + ".txt";
            ofstream outFile(filename);

            if (!outFile) {
                cerr << "Error creating file: " << filename << endl;
                continue;
            }

            for (int i = 0; i < participants; ++i) {
                int id = i + 1;
                int randomChance = fraudDist(gen);

                if (randomChance <= 10) {  // 10% șanse să nu rezolve
                    continue;
                } else if (randomChance <= 12) {  // 2% șanse fraudă
                    outFile << id << " -1\n";
                } else {
                    outFile << id << " " << scoreDist(gen) << "\n";
                }
            }

            outFile.close();
        }
    }
}

// sequential function
void sequentialProcessing(const vector<string>& files, ThreadSafeList& leaderboard) {
    for (const auto& file : files) {
        ifstream inputFile(file);
        if (!inputFile) {
            cerr << "Error opening file: " << file << endl;
            continue;
        }

        int id, score;
        while (inputFile >> id >> score) {
            leaderboard.insertOrUpdate(id, score);
        }

        inputFile.close();
    }
}

// reader thread function
void readerFunction(const vector<string>& files, ThreadSafeQueue& queue) {
    for (const auto& file : files) {
        ifstream inputFile(file);
        if (!inputFile) {
            cerr << "Error opening file: " << file << endl;
            continue;
        }

        int id, score;
        while (inputFile >> id >> score) {
            queue.push({id, score});
        }

        inputFile.close();
    }
}

// worker thread function
void workerFunction(ThreadSafeQueue& queue, ThreadSafeList& leaderboard) {
    pair<int, int> data;
    while (queue.pop(data)) {
        leaderboard.insertOrUpdate(data.first, data.second);
    }
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
    while (getline(f1, line1) && getline(f2, line2)) {
        if (line1 != line2) {
            return false;
        }
    }
    return true;
}

int main(int argc, char *argv[]) {
    if (argc < 6) {
        cerr << "Usage: " << argv[0] << " <function_name> <num_threads> <num_readers> <generate=1/0> <compare=1/0>" << endl;
        return 1;
    }

    string functie = argv[1];
    int numThreads = stoi(argv[2]);
    int numReaders = stoi(argv[3]);
    int numWorkers = numThreads - numReaders;
    int generate = stoi(argv[4]);
    int compare = stoi(argv[5]);

    // block used for testing
    // string functie = "paralel";
    // int numReaders = 2;
    // int numWorkers = 4;
    // int generate = 0;
    // int compare = 1;

    // configuration
    vector<string> countries = {"C1", "C2", "C3", "C4", "C5"};
    int problems = 10;
    int minParticipants = 80;
    int maxParticipants = 100;
    vector<string> files;

    if (generate == 1) {
        generateTestData(countries, problems, minParticipants, maxParticipants);

        for (const auto& country : countries) {
            for (int p = 1; p <= problems; ++p) {
                files.push_back("Rezultate" + country + "_P" + to_string(p) + ".txt");
            }
        }
    }

    if (functie == "secvential") {
        auto start = chrono::high_resolution_clock::now();

        ThreadSafeList leaderboardSeq;

        sequentialProcessing(files, leaderboardSeq);

        leaderboardSeq.writeToFile("Clasament_Secvential.txt");

        auto end = chrono::high_resolution_clock::now();
        chrono::duration<double, std::micro> elapsed = end - start;
        double microseconds = elapsed.count();
        double milliseconds = microseconds / 1000;

        cout << "Secvential: " << milliseconds << " milliseconds" << endl;
    }
    else  if (functie == "paralel") {
        auto start = chrono::high_resolution_clock::now();

        ThreadSafeQueue queue;
        ThreadSafeList leaderboardPar;

        vector<thread> readers, workers;


        // launch reader threads
        for (int i = 0; i < numReaders; ++i) {
            readers.emplace_back(readerFunction, vector<string>(files.begin() + i * files.size() / numReaders, files.begin() + (i + 1) * files.size() / numReaders), ref(queue));
        }

        // launch worker threads
        for (int i = 0; i < numWorkers; ++i) {
            workers.emplace_back(workerFunction, ref(queue), ref(leaderboardPar));
        }

        for (auto& t : readers) {
            t.join();
        }

        queue.setFinished();

        for (auto& t : workers) {
            t.join();
        }



        leaderboardPar.writeToFile("Clasament_Paralel.txt");


        auto end = chrono::high_resolution_clock::now();
        chrono::duration<double, std::micro> elapsed = end - start;
        double microseconds = elapsed.count();
        double milliseconds = microseconds / 1000;
        cout << "Paralel: " << milliseconds << " milliseconds" << endl;
    }

    if (compare == 1) {
        if (functie == "secvential") {
            if (!compareSHA("Clasament_Secvential.txt", "expected.txt"))
                cout << "secvential != output." << endl;
            else cout<<"secvential == output."<<endl;
        }
        else{
            if (!compareSHA("Clasament_Paralel.txt", "expected.txt"))
                cout << "paralel != output." << endl;
            else cout<<"paralel == output."<<endl;
        }
    }

    return 0;
}
