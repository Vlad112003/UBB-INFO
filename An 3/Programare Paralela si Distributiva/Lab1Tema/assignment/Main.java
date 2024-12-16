package ro.ppd.lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    static int N, M, n, p;
    static int[][] mat;
    static int[][] convMat;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        if (args.length < 5) {
            System.err.println("Usage: java Main <number_of_threads> <matrix_rows> <matrix_cols> <function_name> <conv_matrix_size>");
            return;
        }

        p = Integer.parseInt(args[0]);
        N = Integer.parseInt(args[1]);
        M = Integer.parseInt(args[2]);
        String functionName = args[3];
        n = Integer.parseInt(args[4]);

        System.out.println("Number of threads: " + p);
        System.out.println("Matrix rows: " + N);
        System.out.println("Matrix cols: " + M);
        System.out.println("Function name: " + functionName);
        System.out.println("Convolution matrix size: " + n);

        readInput();
//
//        //print the matrix
//
//        for (int i = 0; i < N; ++i) {
//            System.out.println();
//            for (int j = 0; j < M; ++j)
//                System.out.print(mat[i][j] + " ");
//        }

        long startTime = System.nanoTime();

        switch (functionName) {
            case "secvential":
                Secvential secvential = new Secvential(N, M, n, p, mat, convMat);
                secvential.run();
                break;
            case "linii":
                Linii linii = new Linii(N, M, n, p, mat, convMat);
                linii.run();
                break;
            case "coloane":
                Coloane coloane = new Coloane(N, M, n, p, mat, convMat);
                coloane.run();
                break;
            case "vectorizare":
                Vectorizare vectorizare = new Vectorizare(N, M, n, p, mat, convMat);
                vectorizare.run();
                break;
            default:
                System.err.println("Invalid function name. Use one of: secvential, linii, coloane, vectorizare");
                return;
        }

        long endTime = System.nanoTime();
        double milliseconds = (endTime - startTime) / 1_000_000.0;

        System.out.println(milliseconds + " milliseconds");
    }

    private static void readInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("date.txt"));
//        N = scanner.nextInt();
//        M = scanner.nextInt();
        mat = new int[N][M];
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < M; ++j)
                mat[i][j] = scanner.nextInt();

//        n = scanner.nextInt();
        convMat = new int[n][n];
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < n; ++j)
                convMat[i][j] = scanner.nextInt();

//        p = scanner.nextInt();
    }
}