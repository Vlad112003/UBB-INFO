package ro.ppd.lab1;

public class Vectorizare {
    int N, M, n, p;
    int[][] mat;
    int[][] convMat;

    public Vectorizare(int N, int M, int n, int p, int[][] mat, int[][] convMat) {
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

        int totalElements = N * M;
        int elementsPerThread = totalElements / p;
        int remainingElements = totalElements % p;

        for (int k = 0; k < p; ++k) {
            int startIndex = k * elementsPerThread;
            int endIndex = (k == p - 1) ? totalElements : startIndex + elementsPerThread;
            if (remainingElements > 0) {
                endIndex++;
                remainingElements--;
            }

            threads[k] = new Thread(new MyThread(startIndex, endIndex, newMat));
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
            for (int index = start; index < end; ++index) {
                int i = index / M;
                int j = index % M;
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