#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <string>

using namespace std;
MPI_Request recv_request[10];

void print(int* a, int n)
{
    for (int i = 0; i < n; ++i) {
        cout << a[i] << ' ';
    }
    cout << endl;
}

int main(int argc, char** argv) {

    //mpiexec -n 4 D:\FacultaAn3Sem1\PPD\InClass3\cmake-build-debug\InClass3.exe
    //in terminal

    int err = 0;
    int total = 0;
    MPI_Request send_request;

    err = MPI_Init(&argc, &argv);
    int world_size;
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);
    int world_rank;

    MPI_Status status;
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);

    const int n = 10;
    int a[n], b[n], c[n];
    int start, end;

    if (world_rank == 0) {
        for (int i = 0; i < n; i++) {
            a[i] = rand() % 10;
            b[i] = rand() % 10;
        }

        if (world_size > 1) {
            int p = world_size - 1;
            int chunk_size = n / p;
            int rest = n % p;
            start = 0, end = chunk_size;

            for (int i = 1; i < world_size; i++) {
                if (rest) {
                    end++;
                    rest--;
                }

                MPI_Send(&start, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
                MPI_Send(&end, 1, MPI_INT, i, 0, MPI_COMM_WORLD);

                MPI_Send(a + start, end - start, MPI_INT, i, 0, MPI_COMM_WORLD);
                MPI_Send(b + start, end - start, MPI_INT, i, 0, MPI_COMM_WORLD);

                start = end;
                end = start + chunk_size;
            }

            for (int i = 1; i < world_size; i++) {
                MPI_Recv(&start, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
                MPI_Recv(&end, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
                MPI_Recv(c + start, end - start, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
            }

            print(a, n);
            print(b, n);
            print(c, n);
        } else {
            cerr << "World size must be greater than 1." << endl;
        }
    } else {
        MPI_Recv(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
        MPI_Recv(&end, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
        MPI_Recv(a + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
        MPI_Recv(b + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

        for (int i = start; i < end; i++) {
            c[i] = a[i] + b[i];
        }

        MPI_Send(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
        MPI_Send(&end, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
        MPI_Send(c + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }

    // print(a, n);
    // print(b, n);

    MPI_Finalize();
    return 0;
}