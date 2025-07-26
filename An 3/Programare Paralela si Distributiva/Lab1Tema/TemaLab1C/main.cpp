#include <iostream>
#include <thread>
#include <chrono>
#include <fstream>
#include <sstream>
using namespace std;

const int MAX_N = 10000;
const int MAX_M = 10000;

int N, M, n, p;
int mat[MAX_N][MAX_M];
int convMat[MAX_N][MAX_M];
int newMat[MAX_N][MAX_M];

void readInput() {
    stringstream fileName;
    fileName << "date.txt";
    ifstream inputFile(fileName.str());
    if (!inputFile) {
        cerr << "Error opening file." << endl;
        return;
    }

    // inputFile >> N; //read the size of the matrix
    // inputFile >> M; //read the size of the matrix

    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            inputFile >> mat[i][j];
        }
    }

    // inputFile >> n; //read the size of the convolution matrix

    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            inputFile >> convMat[i][j];
        }
    }

    inputFile.close();
}

void secvential() {
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            int sum = 0;
            for (int i1 = 0; i1 < n; ++i1) {
                for (int j1 = 0; j1 < n; ++j1) {
                    if (i - n / 2 + i1 >= 0 && j - n / 2 + j1 >= 0 && i - n / 2 + i1 < N && j - n / 2 + j1 < M) {
                        sum += mat[i - n / 2 + i1][j - n / 2 + j1] * convMat[i1][j1];
                    }
                }
            }
            newMat[i][j] = sum;
        }
    }
}

class ThreadLinii {
private:
    int start, stop;

public:
    ThreadLinii(int start, int stop) : start(start), stop(stop) {}

    void operator()() {
        for (int i = start; i < stop; ++i) {
            for (int j = 0; j < M; ++j) {
                int sum = 0;
                for (int i1 = 0; i1 < n; ++i1) {
                    for (int j1 = 0; j1 < n; ++j1) {
                        if (i - n / 2 + i1 >= 0 && j - n / 2 + j1 >= 0 &&
                            i - n / 2 + i1 < N && j - n / 2 + j1 < M) {
                            sum += mat[i - n / 2 + i1][j - n / 2 + j1] * convMat[i1][j1];
                        }
                    }
                }
                newMat[i][j] = sum;
            }
        }
    }
};

void linii() {
    int startRow = 0;
    int endRow = -1;
    int rowsPerThread = N / p;
    int remainingRows = N % p;

    thread threads[p];

    for (int k = 0; k < p; ++k) {
        endRow = startRow + rowsPerThread;
        if (remainingRows > 0) {
            endRow++;
            remainingRows--;
        }

        threads[k] = thread(ThreadLinii(startRow, endRow));
        startRow = endRow;
    }

    for (int i = 0; i < p; ++i)
        threads[i].join();
}

class ThreadColoane {
private:
    int start, stop;

public:
    ThreadColoane(int start, int stop) : start(start), stop(stop) {}

    void operator()() {
        for (int j = start; j < stop; ++j) {
            for (int i = 0; i < N; ++i) {
                int sum = 0;
                for (int i1 = 0; i1 < n; ++i1) {
                    for (int j1 = 0; j1 < n; ++j1) {
                        if (i - n / 2 + i1 >= 0 && j - n / 2 + j1 >= 0 &&
                            i - n / 2 + i1 < N && j - n / 2 + j1 < M) {
                            sum += mat[i - n / 2 + i1][j - n / 2 + j1] * convMat[i1][j1];
                        }
                    }
                }
                newMat[i][j] = sum;
            }
        }
    }
};

void coloane() {
    int startCol = 0;
    int endCol = -1;
    int colsPerThread = M / p;
    int remainingCols = M % p;

    thread threads[p];

    for (int k = 0; k < p; ++k) {
        endCol = startCol + colsPerThread;
        if (remainingCols > 0) {
            endCol++;
            remainingCols--;
        }

        threads[k] = thread(ThreadColoane(startCol, endCol));
        startCol = endCol;
    }

    for (int i = 0; i < p; ++i)
        threads[i].join();
}

class ThreadVectorizare {
private:
    int start, stop;

public:
    ThreadVectorizare(int start, int stop) : start(start), stop(stop) {}

    void operator()() {
        for (int index = start; index < stop; index++) {
            int i = index / M;
            int j = index % M;
            int sum = 0;
            for (int i1 = 0; i1 < n; ++i1)
                for (int j1 = 0; j1 < n; ++j1)
                    if (i - n / 2 + i1 >= 0 && j - n / 2 + j1 >= 0 && i - n / 2 + i1 < N && j - n / 2 + j1 < M)
                        //i - n / 2 + i1 >= 0: Verifică dacă i nu iese din matrice pe partea de sus.
                        //j - n / 2 + j1 >= 0: Verifică dacă j nu iese din matrice pe partea stângă.
                        //i - n / 2 + i1 < N: Verifică dacă i nu  iese din matrice pe partea de jos.
                        //j - n / 2 + j1 < M: Verifică dacă j nu iese din matrice pe partea dreaptă.
                        sum += mat[i - n / 2 + i1][j - n / 2 + j1] * convMat[i1][j1];
            newMat[i][j] = sum;
        }
    }
};

void vectorizare() {
    int totalElements = N * M;
    int elementsPerThread = totalElements / p;
    int rest = totalElements % p;

    thread threads[p];

    for (int k = 0; k < p; ++k) {
        int startIndex = k * elementsPerThread;
        int endIndex = (k == p - 1) ? totalElements : (k + 1) * elementsPerThread;

        if (rest != 0) {
            --rest;
            ++endIndex;
        }

        threads[k] = thread(ThreadVectorizare(startIndex, endIndex));
    }

    for (int i = 0; i < p; ++i)
        threads[i].join();
}

int main(int argc, char *argv[]) {
    if (argc < 5) {
        cerr << "Usage: " << argv[0] << " <number_of_threads> <matrix_rows> <matrix_cols> <function_name>" << endl;
        return 1;
    }

    p = stoi(argv[1]);
    N = stoi(argv[2]);
    M = stoi(argv[3]);
    string functionName = argv[4];
    n = stoi(argv[5]);

    // cout << "Number of threads: " << p << endl;
    // cout << "Matrix rows: " << N << endl;
    // cout << "Matrix cols: " << M << endl;
    // cout << "Function name: " << functionName << endl;
    // cout << "Convolution matrix size: " << n << endl;

    auto start = chrono::high_resolution_clock::now();
    readInput();

    if (functionName == "secvential") {
        secvential();
    } else if (functionName == "linii") {
        linii();
    } else if (functionName == "coloane") {
        coloane();
    } else if (functionName == "vectorizare") {
        vectorizare();
    } else {
        cerr << "Invalid function name. Use one of: secvential, linii, coloane, vectorizare" << endl;
        return 1;
    }

    auto stop = chrono::high_resolution_clock::now();
    chrono::duration<double, std::micro> duration = stop - start;
    double microseconds = duration.count();
    double milliseconds = microseconds / 1000;

    cout << milliseconds << " milliseconds" << endl;

    return 0;
}