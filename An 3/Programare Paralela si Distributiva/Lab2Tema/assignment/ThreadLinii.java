package ro.ppd.lab1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ThreadLinii implements Runnable {
    private int startRow, endRow, n, p;
    private int[][] mat, convMat;
    private int[][] borderValues;
    private boolean flag;
    private CyclicBarrier barrier;

    public ThreadLinii(int startRow, int endRow, int n, int p, int[][] mat, int[][] convMat, CyclicBarrier barrier) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.n = n;
        this.p = p;
        this.mat = mat;
        this.convMat = convMat;
        this.barrier = barrier;
        this.borderValues = new int[endRow - startRow + 1][mat[0].length];
        this.flag = false;
    }

    @Override
    public void run() {
        // calculate new values for border cells and save them in separate vectors
        calculateBorderValues();

        // set flag to true
        flag = true;

        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        // calculate new values for non-border cells and save them directly in the matrix
        calculateNonBorderValues();

        // check if all neighboring threads have their flags set to true
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        // update border elements by copying values from the vectors calculated in step 1
        updateBorderValues();
    }

    // calculate new values for border cells and save them in separate vectors
    private void calculateBorderValues() {
        int auxRow[] = new int[mat[0].length + n - 1];
        for (int i = startRow; i <= endRow; ++i) {
            // copy the row from the matrix to the auxiliary row
            for (int j = -n / 2; j < mat[0].length + n / 2; ++j) {
                int x = Math.max(0, Math.min(i, mat.length - 1));
                int y = Math.max(0, Math.min(j, mat[0].length - 1));
                auxRow[j + n / 2] = mat[x][y];
            }
            // calculate the new value for each border cell
            for (int j = 0; j < mat[0].length; ++j) {
                int sum = 0;
                for (int i1 = 0; i1 < n; ++i1) {
                    for (int j1 = 0; j1 < n; ++j1) {
                        int x = i - n / 2 + i1;
                        int y = j - n / 2 + j1;
                        if (x >= 0 && y >= 0 && x < mat.length && y < mat[0].length) {
                            sum += auxRow[y + n / 2] * convMat[i1][j1];
                        }
                    }
                }
                borderValues[i - startRow][j] = sum;
            }
        }
    }
    // calculate new values for non-border cells and save them directly in the matrix
    private void calculateNonBorderValues() {
        int auxRow[] = new int[mat[0].length + n - 1];
        for (int i = startRow; i <= endRow; ++i) {
            for (int j = -n / 2; j < mat[0].length + n / 2; ++j) {
                int x = Math.max(0, Math.min(i, mat.length - 1));
                int y = Math.max(0, Math.min(j, mat[0].length - 1));
                auxRow[j + n / 2] = mat[x][y];
            }
            // calculate the new value for each non-border cell
            for (int j = 0; j < mat[0].length; ++j) {
                int sum = 0;
                for (int i1 = 0; i1 < n; ++i1) {
                    for (int j1 = 0; j1 < n; ++j1) {
                        int x = i - n / 2 + i1;
                        int y = j - n / 2 + j1;
                        if (x >= 0 && y >= 0 && x < mat.length && y < mat[0].length) {
                            sum += auxRow[y + n / 2] * convMat[i1][j1];
                        }
                    }
                }
                mat[i][j] = sum;
            }
        }
    }
    // update border elements by copying values from the vectors calculated in step 1
    private void updateBorderValues() {
        int i = startRow;
        for (int[] borderValue : borderValues) {
            System.arraycopy(borderValue, 0, mat[i], 0, borderValue.length);
            i++;
        }
    }
}