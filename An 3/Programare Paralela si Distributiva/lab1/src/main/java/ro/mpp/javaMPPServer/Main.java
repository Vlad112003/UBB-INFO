package ro.mpp.javaMPPServer;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int n = 100000000;
        int upperBound = 1000;


        int[] a = generate(n, upperBound);
        int[] b = generate(n, upperBound);
        int[] res = new int[n];

        long start_time = System.currentTimeMillis();

        for(int i = 0; i<n; i++){
            res[i] = (int) Math.sqrt(a[i] * a[i] * a[i] * a[i] * a[i] + b[i] * b[i] * b[i] * b[i] * b[i]);
        }

//        System.out.println(Arrays.toString(a));
//        System.out.println(Arrays.toString(b));
//        System.out.println(Arrays.toString(res));

        long end_time = System.currentTimeMillis();
        System.out.println(end_time - start_time);


        int p = 6;




        Thread[] threads = new MyThread[p];

        int start = 0;
        int end = n/p;
        int rest = n%p;

        int[] c = new int[n];

        start_time = System.currentTimeMillis();


        for(int i = 0; i<p; i++){
            if(rest > 0){
                end++;
                rest--;
            }
            threads[i] = new MyThread(a, b, c, start, end);
            threads[i].start();
            start = end;
            end = start + n/p;
        }

        for(int i = 0; i < p; i++){
            try{
                threads[i].join();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        end_time = System.currentTimeMillis();
        System.out.println(end_time - start_time);

        int[] c2 = new int[n];

        Thread[] threads2 = new MyThread2[p];

        start_time = System.currentTimeMillis();


        for(int i = 0; i<p; i++){
            if(rest > 0){
                end++;
                rest--;
            }
            threads2[i] = new MyThread2(a, b, c2, n, i, p);
            threads2[i].start();
        }

        for(int i = 0; i < p; i++){
            try{
                threads2[i].join();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        end_time = System.currentTimeMillis();
        System.out.println(end_time - start_time);

        System.out.println(Arrays.equals(res, c));
        System.out.println(Arrays.equals(c, c2));



    }

    public static int[] generate(int n, int upperBound){
        int[] res = new int[n];

        Random random = new Random();
        for(int i = 0; i < n; i++){
            res[i] = random.nextInt(upperBound);
        }

        return res;
    }


}