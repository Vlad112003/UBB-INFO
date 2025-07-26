#include <iostream>
#include <vector>
#include <cuda_runtime.h>
#include <fstream>
#include <sstream>
#include <chrono>

using namespace std;

#define CHECK_CUDA(call)                                                    \
    {                                                                       \
        cudaError_t err = call;                                             \
        if (err != cudaSuccess) {                                           \
            cerr << "CUDA error in " << __FILE__ << ":" << __LINE__        \
                 << " - " << cudaGetErrorString(err) << endl;              \
            exit(err);                                                      \
        }                                                                   \
    }

const int BLOCK_SIZE = 16;

int N, M, p, compare;
int n = 3;
vector<vector<int>> mat, convMat;

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

// CUDA kernel for convolution
__global__ void convolveKernel(int *d_mat, int *d_convMat, int *d_result, int N, int M, int n) {
    int row = blockIdx.y * blockDim.y + threadIdx.y;
    int col = blockIdx.x * blockDim.x + threadIdx.x;

    if (row < N && col < M) {
        int sum = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                int x = row - n / 2 + i;
                int y = col - n / 2 + j;
                if (x >= 0 && y >= 0 && x < N && y < M) {
                    sum += d_mat[x * M + y] * d_convMat[i * n + j];
                }
            }
        }
        d_result[row * M + col] = sum;
    }
}

void linii() {
    size_t matSize = N * M * sizeof(int);
    size_t convMatSize = n * n * sizeof(int);

    // Allocate device memory
    int *d_mat, *d_convMat, *d_result;
    CHECK_CUDA(cudaMalloc(&d_mat, matSize));
    CHECK_CUDA(cudaMalloc(&d_convMat, convMatSize));
    CHECK_CUDA(cudaMalloc(&d_result, matSize));

    // Flatten host matrices for device transfer
    vector<int> flatMat(N * M);
    vector<int> flatConvMat(n * n);
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            flatMat[i * M + j] = mat[i][j];
        }
    }
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            flatConvMat[i * n + j] = convMat[i][j];
        }
    }

    // Copy data to device
    CHECK_CUDA(cudaMemcpy(d_mat, flatMat.data(), matSize, cudaMemcpyHostToDevice));
    CHECK_CUDA(cudaMemcpy(d_convMat, flatConvMat.data(), convMatSize, cudaMemcpyHostToDevice));

    // Define grid and block dimensions
    dim3 blockSize(16, 16);
    dim3 gridSize((M + 15) / 16, (N + 15) / 16);

    // Launch the kernel
    convolveKernel<<<gridSize, blockSize>>>(d_mat, d_convMat, d_result, N, M, n);
    CHECK_CUDA(cudaGetLastError());

    // Copy results back to host
    vector<int> flatResult(N * M);
    CHECK_CUDA(cudaMemcpy(flatResult.data(), d_result, matSize, cudaMemcpyDeviceToHost));

    // Reshape flatResult into 2D result matrix
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            mat[i][j] = flatResult[i * M + j];
        }
    }

    // Free device memory
    CHECK_CUDA(cudaFree(d_mat));
    CHECK_CUDA(cudaFree(d_convMat));
    CHECK_CUDA(cudaFree(d_result));
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

int main(int argc, char *argv[]) {
    if (argc < 4) {
        cerr << "Usage: " << argv[0] << " <matrix_rows> <matrix_cols> <function_name: secvential/linii> <compare-1/0>" << endl;
        return 1;
    }

    // p = stoi(argv[1]);
    N = stoi(argv[1]);
    M = stoi(argv[2]);
    string functionName = argv[3];
    n = 3;
    compare = stoi(argv[4]);

    // block folosit pentru testare
    // p = 8;
    // N = 100;
    // M = 100;
    // string functionName = "linii";
    // n = 3;
    // compare = 0;

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
        if (N != 100 && N != 1000 && N != 10000) {
            cout << "Invalid matrix size" << endl;
            return 1;
        }

        ifstream outputFile("output.txt");
        ifstream expectedFile = ifstream("expected" + to_string(N) + "x" + to_string(N) + ".txt");

        string outputLine, expectedLine;
        while (getline(outputFile, outputLine) && getline(expectedFile, expectedLine)) {
            outputLine.erase(outputLine.find_last_not_of(" \n\r\t") + 1);
            expectedLine.erase(expectedLine.find_last_not_of(" \n\r\t") + 1);
            if (outputLine != expectedLine) {
                cout << "Output is not correct" << endl;
                return 1;
            }
        }
        cout << "Output is correct" << endl;
    }

    return 0;
}