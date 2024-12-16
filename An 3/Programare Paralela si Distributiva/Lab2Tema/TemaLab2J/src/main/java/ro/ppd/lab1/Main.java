package ro.ppd.lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {

    static int N, M, p, compare;
    static int n = 3;
    static int[][] mat;
    static int[][] convMat = {
            {1, 0, 1},
            {0, 2, 0},
            {-1, 0, -1}
    };
    static CyclicBarrier barrier;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
                if (args.length < 6) {
                    System.err.println("Usage: java .jar <number_of_threads> <matrix_rows> <matrix_cols> <function_name> <conv_matrix_size> <compare=1/0>");
                    return;
                }

                p = Integer.parseInt(args[0]);
                N = Integer.parseInt(args[1]);
                M = Integer.parseInt(args[2]);
                String functionName = args[3];
                n = Integer.parseInt(args[4]);
                compare = Integer.parseInt(args[5]);
//        block pt testing
//        p = 8;
//        N = 1000;
//        M = 1000;
//        String functionName = "linii";
//        n = 3;
//        compare = 1;

        readInput();
        barrier = new CyclicBarrier(p);

        long startTime = System.nanoTime();

        switch (functionName) {
            case "secvential":
                Secvential secvential = new Secvential(N, M, n, mat, convMat);
                secvential.run();
                break;
            case "linii":
                Linii linii = new Linii(N, M, n, p, mat, convMat);
                linii.run();
                break;
            default:
                System.err.println("Invalid function name. Use one of: secvential, linii");
                return;
        }

        long endTime = System.nanoTime();
        double milliseconds = (endTime - startTime) / 1_000_000.0;

        System.out.println(milliseconds + " milliseconds");

        saveOutput();

        if(compare == 1){
            //compare the output with the expected output
            // am cate un fisier expected pt fiecare N, M, adica expected10x10.txt,
            // expected1000x1000.txt si expected10000x10000.txt
            Scanner scanner = new Scanner(new File("expected" + N + "x" + M + ".txt"));
            Scanner scanner2 = new Scanner(new File("output.txt"));
            boolean ok = true;
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < M; ++j) {
                    if(scanner.nextInt() != scanner2.nextInt()){
                        ok = false;
                        System.out.println("Different values at " + i + " " + j);
                        break;
                    }
                }
                if(!ok)
                    break;
            }
            if(ok)
                System.out.println("The output is correct");
            else
                System.out.println("The output is incorrect");
        }
    }

    private static void readInput() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("date.txt"));
        mat = new int[N][M];
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < M; ++j)
                mat[i][j] = scanner.nextInt();
    }

    private static void saveOutput() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("output.txt"));
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                writer.print(mat[i][j] + " ");
            }
            writer.println();
        }
        writer.close();
    }
}