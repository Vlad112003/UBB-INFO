package ro.ppd.lab1;

public class Linii {
    int N, M, n, p;
    int[][] mat;
    int[][] convMat;

    public Linii(int N, int M, int n, int p, int[][] mat, int[][] convMat) {
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

        int rowsPerThread = N / p;
        int remainingRows = N % p;

        for (int k = 0; k < p; ++k) {
            int startRow = k * rowsPerThread;
            int endRow = (k == p - 1) ? N : startRow + rowsPerThread;
            if (remainingRows > 0) {
                endRow++;
                remainingRows--;
            }

            threads[k] = new Thread(new MyThread(startRow, endRow, newMat));
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
            for (int i = start; i < end; ++i) {
                for (int j = 0; j < M; ++j) {
                    int sum = 0;
                    for (int i1 = 0; i1 < n; ++i1)
                        for (int j1 = 0; j1 < n; ++j1)
                            if (i - n / 2 + i1 >= 0 && j - n / 2 + j1 >= 0 && i - n / 2 + i1 < N && j - n / 2 + j1 < M)
                                sum += mat[i - n / 2 + i1][j - n / 2 + j1] * convMat[i1][j1];
                    newMat[i][j] = sum;
                }
            }
        }
    }
}