package ro.ppd.lab1;

import java.util.concurrent.CyclicBarrier;

public class Linii {
    private int N, M, n, p;
    private int[][] mat, convMat;

    public Linii(int N, int M, int n, int p, int[][] mat, int[][] convMat) {
        this.N = N;
        this.M = M;
        this.n = n;
        this.p = p;
        this.mat = mat;
        this.convMat = convMat;
    }

    public void run() throws InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(p);
        Thread[] threads = new Thread[p];
        int rowsPerThread = N / p;
        // create threads
        // each thread will calculate new values for border cells and save them in separate vectors
        // then, each thread will calculate new values for non-border cells and save them directly in the matrix
        for (int i = 0; i < p; i++) {
            // calculate start and end row for each thread
            int startRow = i * rowsPerThread;
            // if it's the last thread, the end row is N - 1
            int endRow = (i == p - 1) ? N - 1 : (startRow + rowsPerThread - 1);
            threads[i] = new Thread(new ThreadLinii(startRow, endRow, n, p, mat, convMat, barrier));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}