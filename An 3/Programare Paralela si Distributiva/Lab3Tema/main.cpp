#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>
#include <chrono>
#include <mpi.h>
using namespace std;

vector<int> readNumber(const string& filename) {
    ifstream fin(filename);
    int size;
    fin >> size;
    vector<int> number(size);
    for (int i = 0; i < size; i++) {
        // fin >> number[size - 1 - i]; // read digits in reverse order
        fin >> number[i]; // read digits in correct order
    }
    return number;
}

string convertVectorToString(const vector<int>& vec) {
    string str;
    for (int i = vec.size() - 1; i >= 0; --i) {
        str += to_string(vec[i]);
    }
    return str;
}

bool compareResults(const vector<int>& result, const string& expected) {
    string resultStr = convertVectorToString(result);
    return resultStr == expected;
}

vector<int> sequentialAddition(vector<int> num1, vector<int> num2) {
    // determine the size of the result
    int maxSize = max(num1.size(), num2.size());
    // create a vector to store the result
    vector<int> result(maxSize + 1, 0);
    int carry = 0;

    for (int i = 0; i < maxSize; ++i) {
        // get the digits from the two numbers
        int digit1 = (i < num1.size()) ? num1[i] : 0;
        int digit2 = (i < num2.size()) ? num2[i] : 0;
        // add the digits and the carry
        int sum = digit1 + digit2 + carry;
        // store the result in the vector
        result[i] = sum % 10;
        // update the carry
        carry = sum / 10;
    }

    if (carry) {
        // if there is a carry left, add it to the result
        result[maxSize] = carry;
    } else {
        // if there is no carry left, remove the last digit
        result.pop_back();
    }

    return result;
}

void printNumber(const vector<int>& number) {
    bool leadingZero = true;
    for (int i = number.size() - 1; i >= 0; --i) {
        if (leadingZero && number[i] == 0) {
            continue;
        }
        leadingZero = false;
        cout << number[i];
    }
    if (leadingZero) {
        cout << 0;
    }
    cout << endl;
}

string readExpected(const string& filename) {
    ifstream fin(filename);
    string expected;
    fin >> expected;
    return expected;
}

void writeToFile(const vector<int>& number, const string& filename) {
    ofstream fout(filename);
    for (int i = number.size() - 1; i >= 0; --i) {
        fout << number[i] << " ";
    }
}

void writeToExpectedFile(const vector<int>& number, const string& filename) {
    ofstream fout(filename);
    for (int i = number.size() - 1; i >= 0; --i) {
        fout << number[i];
    }
}

int main(int argc, char* argv[]) {
    if (argc != 5) {
        cout << "Usage: " << argv[0] << " <numar1 filepath> <numar2 filepath> <secvential/paralel> <compare>" << endl;
        return 1;
    }

    string filename1 = argv[1];
    string filename2 = argv[2];
    string functie = argv[3];
    int compare = atoi(argv[4]);

    // string filename1 = "Numar1T4.txt";
    // string filename2 = "Numar2T4.txt";
    // string functie = "secvential";
    // int compare = 1;

    vector<int> number1, number2, result;

    int rank, size;

    MPI_Init(&argc, &argv);
    // get the rank of the current process and the total number of processes
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (functie == "secvential") {
        if (rank == 0) {
            auto start = chrono::high_resolution_clock::now();
            number1 = readNumber(filename1);
            number2 = readNumber(filename2);
            result = sequentialAddition(number1, number2);
            writeToFile(result, "Numar3.txt");
            auto stop = chrono::high_resolution_clock::now();
            chrono::duration<double, std::milli> duration = stop - start;
            cout << duration.count() << " milliseconds" << endl;
        }
    } else if (functie == "send") {
        if (rank == 0) {
            number1 = readNumber(filename1);
            number2 = readNumber(filename2);

            int maxSize = max(number1.size(), number2.size());  // Determine the size of the result
            int chunkSize = (maxSize + size - 2) / (size - 1); // Distribute work among workers

            auto start = chrono::high_resolution_clock::now();

            for (int i = 0; i < maxSize; i += chunkSize) {
                // determine the actual size of the chunk
                int actualChunkSize = min(chunkSize, maxSize - i);
                // create chunks of the two numbers
                vector<int> chunk1(actualChunkSize, 0), chunk2(actualChunkSize, 0);

                // copy the digits from the numbers to the chunks
                for (int j = 0; j < actualChunkSize; ++j) {
                    if (i + j < number1.size()) chunk1[j] = number1[i + j];
                    if (i + j < number2.size()) chunk2[j] = number2[i + j];
                }
                // send the chunks to the workers
                MPI_Send(&actualChunkSize, 1, MPI_INT, (i / chunkSize) + 1, 0, MPI_COMM_WORLD);
                MPI_Send(chunk1.data(), actualChunkSize, MPI_INT, (i / chunkSize) + 1, 1, MPI_COMM_WORLD);
                MPI_Send(chunk2.data(), actualChunkSize, MPI_INT, (i / chunkSize) + 1, 2, MPI_COMM_WORLD);
            }

            result.resize(maxSize + 1, 0);
            for (int i = 1; i < size; ++i) {
                int actualChunkSize;
                // receive the actual size of the chunk
                MPI_Recv(&actualChunkSize, 1, MPI_INT, i, 3, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

                vector<int> partialResult(actualChunkSize + 1, 0);
                // receive the partial result
                MPI_Recv(partialResult.data(), actualChunkSize + 1, MPI_INT, i, 4, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                // add the partial result to the final result
                for (int j = 0; j < actualChunkSize; ++j) {
                    result[(i - 1) * chunkSize + j] += partialResult[j];
                }
            }

            int carry = 0;
            for (int i = 0; i < result.size(); ++i) {
                result[i] += carry;
                carry = result[i] / 10;
                result[i] %= 10;
            }
            if (carry) {
                result.push_back(carry);
            }

            writeToFile(result, "Numar3.txt");
            auto stop = chrono::high_resolution_clock::now();
            chrono::duration<double, std::milli> duration = stop - start;
            cout << duration.count() << " milliseconds" << endl;

        } else {
            int actualChunkSize;
            // receive the actual size of the chunk
            MPI_Recv(&actualChunkSize, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            // create chunks for the two numbers and a vector for the partial result
            vector<int> chunk1(actualChunkSize, 0), chunk2(actualChunkSize, 0), partialResult(actualChunkSize + 1, 0);
            // receive the chunks
            MPI_Recv(chunk1.data(), actualChunkSize, MPI_INT, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            MPI_Recv(chunk2.data(), actualChunkSize, MPI_INT, 0, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

            int carry = 0;
            for (int i = 0; i < actualChunkSize; ++i) {
                int sum = chunk1[i] + chunk2[i] + carry;
                partialResult[i] = sum % 10;
                carry = sum / 10;
            }
            partialResult[actualChunkSize] = carry;
            // send the partial result back to the master
            MPI_Send(&actualChunkSize, 1, MPI_INT, 0, 3, MPI_COMM_WORLD);
            MPI_Send(partialResult.data(), actualChunkSize + 1, MPI_INT, 0, 4, MPI_COMM_WORLD);
        }
    } else if (functie == "scatter") {
    auto start = chrono::high_resolution_clock::now();
    number1 = readNumber(filename1);
    number2 = readNumber(filename2);

    int maxSize = max(number1.size(), number2.size());
    int chunkSize = (maxSize + size - 1) / size; // Distribute work among all processes

    vector<int> chunk1(size * chunkSize, 0), chunk2(size * chunkSize, 0), partialResult(chunkSize, 0);
    vector<int> recvChunk1(chunkSize, 0), recvChunk2(chunkSize, 0);

    if (rank == 0) {
        // Fill chunk1 and chunk2 with the numbers to scatter
        for (int i = 0; i < number1.size(); ++i) {
            chunk1[i] = number1[i];
        }
        for (int i = 0; i < number2.size(); ++i) {
            chunk2[i] = number2[i];
        }
    }

    // Scatter the chunks to all processes, including the master
    MPI_Scatter(chunk1.data(), chunkSize, MPI_INT, recvChunk1.data(), chunkSize, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Scatter(chunk2.data(), chunkSize, MPI_INT, recvChunk2.data(), chunkSize, MPI_INT, 0, MPI_COMM_WORLD);

    int localCarry = 0;
    for (int i = 0; i < chunkSize; ++i) {
        int sum = recvChunk1[i] + recvChunk2[i] + localCarry;
        partialResult[i] = sum % 10;
        localCarry = sum / 10;
    }

    // Use MPI_Scan to propagate the carry globally
    int globalCarry = 0;
    MPI_Scan(&localCarry, &globalCarry, 1, MPI_INT, MPI_SUM, MPI_COMM_WORLD);

    // Adjust the carry for the current process
    if (rank != size - 1) {
        // For processes other than the last one, don't add the carry to the last digit yet
        partialResult[chunkSize - 1] += globalCarry - localCarry;
    } else {
        // The last process includes the global carry
        localCarry = globalCarry;
    }

    vector<int> gatheredResult(chunkSize * size, 0);
    // Gather the partial results from all processes
    MPI_Gather(partialResult.data(), chunkSize, MPI_INT, gatheredResult.data(), chunkSize, MPI_INT, 0, MPI_COMM_WORLD);

    if (rank == 0) {
        result.resize(maxSize + 1, 0); // Ensure space for potential carry overflow
        int carry = 0;

        // Assemble the result from the gathered chunks
        for (int i = 0; i < maxSize; ++i) {
            result[i] += gatheredResult[i] + carry;  // Add carry to the current digit
            carry = result[i] / 10;                  // Compute carry for the next digit
            result[i] %= 10;                         // Reduce to a single digit
        }

        // Handle any remaining carry
        if (carry) {
            result[maxSize] = carry;
        }

        // Reverse the result to put the digits in the correct order
        reverse(result.begin(), result.end());

        // Write the result to a file
        writeToFile(result, "Numar3.txt");

        auto stop = chrono::high_resolution_clock::now();
        chrono::duration<double, std::milli> duration = stop - start;
        cout << duration.count() << " milliseconds" << endl;
    }
}
    else
    {
        if (rank == 0) {
            cerr << "Unknown function: " << functie << ". Use 'secvential' ,'send' or 'scatter'." << endl;
        }
    }

    if(compare) {
        string expectedString = "expected" + to_string(number1.size()) + "x" + to_string(number2.size()) + ".txt";

        // writeToExpectedFile(result, expectedString);
        string expectedSum = readExpected(expectedString);
        if (compareResults(result, expectedSum)) {
            cout << "The sum is correct." << endl;
        } else {
            cout << "The sum is incorrect." << endl;
            cout << "Expected sum: " << expectedSum << endl;
        }
    }

    MPI_Finalize();
    return 0;
}