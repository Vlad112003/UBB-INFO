package ro.ppd.lab1;

public class Secvential {
    private final int N, M, n;
    private final int[][] mat;
    private final int[][] convMat;

    public Secvential(int N, int M, int n, int[][] mat, int[][] convMat) {
        this.N = N;
        this.M = M;
        this.n = n;
        this.mat = mat;
        this.convMat = convMat;
    }

    public void run() {
        int[] auxRow = new int[M + n - 1]; //randul auxiliar pentru a nu iesi din matrice
        //
        for (int i = 0; i < N; ++i) {
            for (int j = -n / 2; j < M + n / 2; ++j) {
                int x = Math.max(0, Math.min(i, N - 1));
                int y = Math.max(0, Math.min(j, M - 1));
                auxRow[j + n / 2] = mat[x][y];
            }

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
}