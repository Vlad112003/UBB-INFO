package ro.ppd.lab1;

public class Coloane {
    int N, M, n, p;
    int[][] mat;
    int[][] convMat;

    public Coloane(int N, int M, int n, int p, int[][] mat, int[][] convMat) {
        this.N = N;
        this.M = M;
        this.n = n;
        this.p = p;
        this.mat = mat;
        this.convMat = convMat;
    }

    public int[][] run() throws InterruptedException {
        int[][] newMat = new int[N][M];
        Thread[] threads = new Thread[p];

        int colsPerThread = M / p;
        int remainingCols = M % p;

        for (int k = 0; k < p; ++k) {
            int startCol = k * colsPerThread;
            int endCol = (k == p - 1) ? M : startCol + colsPerThread;
            if (remainingCols > 0) {
                endCol++;
                remainingCols--;
            }

            threads[k] = new Thread(new MyThread(startCol, endCol, newMat));
            threads[k].start();
        }

        for (Thread thread : threads)
            thread.join();

//        for (int i = 0; i < N; ++i) {
//            System.out.println();
//            for (int j = 0; j < M; ++j)
//                System.out.print(newMat[i][j] + " ");
//        }

        return newMat;
    }

    class MyThread extends Thread {
        final private int start, end;
        int[][] newMat;

        public MyThread(int start, int end, int[][] newMat) {
            this.start = start;
            this.end = end;
            this.newMat = newMat;
        }

        public void run() {
            for (int j = start; j < end; ++j) {
                for (int i = 0; i < N; ++i) {
                    int sum = 0;
                    for (int i1 = 0; i1 < n; ++i1)
                        for (int j1 = 0; j1 < n; ++j1)
                            if (i - n / 2 + i1 >= 0 && j - n / 2 + j1 >= 0 && i - n / 2 + i1 < N && j - n / 2 + j1 < M)
                                //i - n / 2 + i1 >= 0: Verifică dacă nu iese din matrice pe partea de sus.
                                //j - n / 2 + j1 >= 0: Verifică dacă nu iese din matrice pe partea stângă.
                                //i - n / 2 + i1 < N: Verifică dacă nu iese din matrice pe partea de jos.
                                //j - n / 2 + j1 < M: Verifică dacă nu iese din matrice pe partea dreaptă.
                                sum += mat[i - n / 2 + i1][j - n / 2 + j1] * convMat[i1][j1];
                    newMat[i][j] = sum;
                }
            }
        }
    }
}