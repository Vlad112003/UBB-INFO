#include <cassert>
#include <iostream>
#include <thread>
#include <chrono>
#include <fstream>
#include <sstream>
#include <vector>
#include <mutex>
#include <barrier>
using namespace std;

int N, M, p, compare;
int n = 3;
vector<vector<int>> mat, convMat;
mutex mtx;

void readInput() {
    ifstream inputFile("date.txt");
    if (!inputFile) {
        cerr << "Error opening file." << endl;
        return;
    }
    mat.resize(N, vector<int>(M));
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            inputFile >> mat[i][j];
        }
    }
    // 1 0 1
    // 0 2 0
    // -1 0 -1
    convMat.resize(n, vector<int>(n));
    convMat.assign({{1, 0, 1}, {0, 2, 0}, {-1, 0, -1}});

    inputFile.close();
}

void writeOutput() {
    ofstream outputFile("output.txt");
    if (!outputFile) {
        cerr << "Error opening file." << endl;
        return;
    }

    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            outputFile << mat[i][j];
            if (j < M - 1) {
                outputFile << " ";
            }
        }
        if (i < N - 1) {
            outputFile << endl;
        }
    }
    outputFile.close();
}

void secvential() {

    vector<int> auxRow(M + n - 1);

    // calculate new values for border cells and save them in separate vectors
    for (int i = 0; i < N; ++i) {
        for (int j = -n / 2; j < M + n / 2; ++j) {
            int x = max(0, min(i, N - 1));
            int y = max(0, min(j, M - 1));
            auxRow[j + n / 2] = mat[x][y];
        }
        // calculate new values for non-border cells and save them directly in the matrix
        for (int j = 0; j < M; ++j) {
            int sum = 0;
            for (int i1 = 0; i1 < n; ++i1) {
                for (int j1 = 0; j1 < n; ++j1) {
                    int x = i - n / 2 + i1;
                    int y = j - n / 2 + j1;
                    if (x >= 0 && y >= 0 && x < N && y < M) {
                        sum += auxRow[y + n / 2] * convMat[i1][j1];
                    }
                }
            }
            mat[i][j] = sum;
        }
    }
}

class ThreadLinii {
private:
    int startRow, endRow, n, p;
    vector<vector<int>>& mat;
    vector<vector<int>>& convMat;
    vector<vector<int>> borderValues;
    bool flag;
    barrier<>* syncBarrier;

public:
    ThreadLinii(int startRow, int endRow, int n, int p, vector<vector<int>>& mat, vector<vector<int>>& convMat, barrier<>* syncBarrier)
        : startRow(startRow), endRow(endRow), n(n), p(p), mat(mat), convMat(convMat), syncBarrier(syncBarrier) {
        borderValues.resize(endRow - startRow + 1, vector<int>(mat[0].size()));
        flag = false;
    }

    void operator()() {
        // calculate new values for border cells and save them in separate vectors
        calculateBorderValues();

        // set flag to true
        flag = true;

        syncBarrier->arrive_and_wait();

        // calculate new values for non-border cells and save them directly in the matrix
        calculateNonBorderValues();

        // check if all neighboring threads have their flags set to true
        syncBarrier->arrive_and_wait();

        // update border elements by copying values from the vectors calculated in step 1
        updateBorderValues();
    }

private:
    // calculate new values for border cells and save them in separate vectors
    // the values will be used to update the border cells in the matrix
    void calculateBorderValues() {
        vector<int> auxRow(mat[0].size() + n - 1);
        for (int i = startRow; i <= endRow; ++i) {
            for (int j = -n / 2; j < mat[0].size() + n / 2; ++j) {
                int x = max(0, min(i, static_cast<int>(mat.size()) - 1));
                int y = max(0, min(j, static_cast<int>(mat[0].size()) - 1));
                auxRow[j + n / 2] = mat[x][y];
            }

            for (int j = 0; j < mat[0].size(); ++j) {
                int sum = 0;
                for (int i1 = 0; i1 < n; ++i1) {
                    for (int j1 = 0; j1 < n; ++j1) {
                        int x = i - n / 2 + i1;
                        int y = j - n / 2 + j1;
                        if (x >= 0 && y >= 0 && x < mat.size() && y < mat[0].size()) {
                            sum += auxRow[y + n / 2] * convMat[i1][j1];
                        }
                    }
                }
                borderValues[i - startRow][j] = sum;
            }
        }
    }
    // calculate new values for non-border cells and save them directly in the matrix
    void calculateNonBorderValues() {
        vector<int> auxRow(mat[0].size() + n - 1);
        for (int i = startRow; i <= endRow; ++i) {
            for (int j = -n / 2; j < mat[0].size() + n / 2; ++j) {
                int x = max(0, min(i, static_cast<int>(mat.size()) - 1));
                int y = max(0, min(j, static_cast<int>(mat[0].size()) - 1));
                auxRow[j + n / 2] = mat[x][y];
            }

            for (int j = 0; j < mat[0].size(); ++j) {
                int sum = 0;
                for (int i1 = 0; i1 < n; ++i1) {
                    for (int j1 = 0; j1 < n; ++j1) {
                        int x = i - n / 2 + i1;
                        int y = j - n / 2 + j1;
                        if (x >= 0 && y >= 0 && x < mat.size() && y < mat[0].size()) {
                            sum += auxRow[y + n / 2] * convMat[i1][j1];
                        }
                    }
                }
                mat[i][j] = sum;
            }
        }
    }
    // update border elements by copying values from the vectors calculated in step 1
    void updateBorderValues() {
        int i = startRow;
        for (const auto& borderValue : borderValues) {
            copy(borderValue.begin(), borderValue.end(), mat[i].begin());
            i++;
        }
    }
};

void linii() {
    int numThreads = p;

    if (numThreads == 0) {
        cerr << "Error: Number of threads is zero." << endl;
        return;
    }

    if (N == 0) {
        cerr << "Error: Number of rows (N) is zero." << endl;
        return;
    }

    // Create a barrier for synchronization
    barrier syncBarrier(numThreads);

    vector<thread> threads;
    int rowsPerThread = N / numThreads;

    // each thread will process a number of rows equal to rowsPerThread
    // the last thread will process the remaining rows
    for (int i = 0; i < numThreads; ++i) {
        int startRow = i * rowsPerThread;
        int endRow = (i == numThreads - 1) ? N - 1 : (startRow + rowsPerThread - 1);
        threads.emplace_back(ThreadLinii(startRow, endRow, n, p, mat, convMat, &syncBarrier));
    }

    for (auto& t : threads) {
        t.join();
    }
}

int main(int argc, char *argv[]) {
    if (argc < 7) {
        cerr << "Usage: " << argv[0] << " <number_of_threads> <matrix_rows> <matrix_cols> <function_name> <conv_matrix_size> <compare-1/0>" << endl;
        return 1;
    }

    p = stoi(argv[1]);
    N = stoi(argv[2]);
    M = stoi(argv[3]);
    string functionName = argv[4];
    n = stoi(argv[5]);
    compare = stoi(argv[6]);

    // block folosit pentru testare
    // p = 8;
    // N = 1000;
    // M = 1000;
    // string functionName = "linii";
    // n = 3;
    // compare = 1;

    auto start = chrono::high_resolution_clock::now();
    readInput();

    if (functionName == "secvential") {
        secvential();
    } else if (functionName == "linii") {
        linii();
    } else {
        cerr << "Invalid function name. Use one of: secvential, linii" << endl;
        return 1;
    }

    auto stop = chrono::high_resolution_clock::now();
    chrono::duration<double, std::micro> duration = stop - start;
    double microseconds = duration.count();
    double milliseconds = microseconds / 1000;

    cout << milliseconds << " milliseconds" << endl;

    writeOutput();
    if (compare == 1) {
        //compare the output with the expected output
        // am cate un fisier expected pt fiecare N, M, adica expected10x10.txt,
        // expected1000x1000.txt si expected10000x10000.txt
        if (N != 10 && N != 1000 && N != 10000) {
            cout << "Invalid matrix size" << endl;
            return 1;
        }

        ifstream outputFile("output.txt");
        ifstream expectedFile = ifstream("expected" + to_string(N) + "x" + to_string(N) + ".txt");

        string outputLine, expectedLine;
        while (getline(outputFile, outputLine) && getline(expectedFile, expectedLine)) {
            //strip both lines of trailing whitespaces
            outputLine.erase(outputLine.find_last_not_of(" \n\r\t") + 1);
            expectedLine.erase(expectedLine.find_last_not_of(" \n\r\t") + 1);
            // cout<<outputLine<<endl;
            // cout<<expectedLine<<endl;
            if (outputLine != expectedLine) {
                cout << "Output is not correct" << endl;
                return 1;
            }
        }
        cout<<"Output is correct"<<endl;
    }

    return 0;
}