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

    if (world_rank == 0) {
        for (int i = 0; i < n; i++) {
            a[i] = rand() % 10;
            b[i] = rand() % 10;
        }
    }

    int chunk_size = n / world_size;
    int *aux_a = new int[chunk_size];
    int *aux_b = new int[chunk_size];
    int *aux_c = new int[chunk_size];

    MPI_Scatter(a, chunk_size, MPI_INT, aux_a, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);  // nu redistribuie restul!!
    MPI_Scatter(b, chunk_size, MPI_INT, aux_b, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);

    for(int i=0; i<chunk_size; i++)
        aux_c[i] = aux_a[i] + aux_b[i];

    MPI_Gather(aux_c, chunk_size, MPI_INT, c, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);

    if (world_rank == 0) {
        print(a, n);
        print(b, n);
        print(c, n);
    }

    // print(a, n);
    // print(b, n);

    MPI_Finalize();
    return 0;
}
