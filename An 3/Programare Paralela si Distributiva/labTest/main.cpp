#include <iostream>
#include <fstream>
#include <mpi.h>
#include <vector>
using namespace std;

void h(int &numar, int &x) {
    if (numar < x) {
        numar += x;
    } else {
        numar /= 2;
        x /= 2;
    }
}

vector<int> readNumbers(const string &filename) {
    ifstream fin(filename);
    vector<int> numbers;
    int nr;
    while (fin >> nr) {
        numbers.push_back(nr);
    }
    return numbers;
}

int main(int argc, char* argv[]) {
    if (argc != 3) {
        cout << "Usage: " << argv[0] << " <numbers filepath> <compare>" << endl;
        cout<<argc;
        return 1;
    }



    string filepath = argv[1];
    string functie = argv[2];
    int compare = atoi(argv[3]);


    MPI_Init(&argc, &argv);


    int rank, size;
    // get the rank of the current process and the total number of processes
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    int X;
    vector<int> numbers;

    if (rank == 0) {
        ifstream fin(filepath);
        fin >> X;
        numbers = readNumbers(filepath);


        int segment_size = numbers.size() / size; //for distributing work among workers
        for (int i = 1; i < size; ++i) {
            int start = i * segment_size;
            int end = (i == size - 1) ? numbers.size() : start + segment_size;
            MPI_Send(&X, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
            MPI_Send(&numbers[start], end - start, MPI_INT, i, 0, MPI_COMM_WORLD);
        }
        numbers.resize(segment_size);
    } else {
        MPI_Recv(&X, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        int segment_size = (rank == size - 1) ? numbers.size() - rank * (numbers.size() / size) : numbers.size() / size;
        numbers.resize(segment_size);
        MPI_Recv(numbers.data(), segment_size, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    }

    int local_sum = 0;
    for (int &num : numbers) {
        local_sum += num;
        h(num, X);
    }

    if (rank != 0) {
        MPI_Send(numbers.data(), numbers.size(), MPI_INT, 0, 0, MPI_COMM_WORLD);
    } else {
        for (int i = 1; i < size; ++i) {
            int segment_size = (i == size - 1) ? numbers.size() - i * (numbers.size() / size) : numbers.size() / size;
            vector<int> segment(segment_size);
            MPI_Recv(segment.data(), segment_size, MPI_INT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            numbers.insert(numbers.end(), segment.begin(), segment.end());
        }
    }

    //suma in cascada
    int total_sum = 0;
    if (rank == 0) {
        total_sum = local_sum;
        MPI_Send(&total_sum, 1, MPI_INT, 1, 0, MPI_COMM_WORLD);
    } else {
        int received_sum;
        MPI_Recv(&received_sum, 1, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        total_sum = received_sum + local_sum;
        if (rank != size - 1) {
            MPI_Send(&total_sum, 1, MPI_INT, rank + 1, 0, MPI_COMM_WORLD);
        } else {
            MPI_Send(&total_sum, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
        }
    }

    //procesul 0 primeste suma totala si scrie in fisier
    if (rank == 0) {
        MPI_Recv(&total_sum, 1, MPI_INT, size - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        ofstream fout("result.txt");
        for (const int &num : numbers) {
            fout << num << endl;
        }
        fout.close();
        cout << "Total sum: " << total_sum << endl;
        cout<<numbers[150];
    }

    if (compare == 1) {
        cout<<"comparing...";
    }

    MPI_Finalize();
    return 0;
}