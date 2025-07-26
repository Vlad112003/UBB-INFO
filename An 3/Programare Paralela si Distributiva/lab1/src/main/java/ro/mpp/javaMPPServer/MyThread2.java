package ro.mpp.javaMPPServer;

public class MyThread2 extends Thread{
    private int[] a;
    private int[] b;
    private int[] c;
    private int id;
    private int n;
    private int p;

    MyThread2(int[] a, int[] b, int[] c, int n, int id, int p){
        this.a = a;
        this.b = b;
        this.c = c;
        this.n = n;
        this.id = id;
        this.p = p;
    }

    @Override public void run() {
        for (int i = id; i < n; i += p)
            c[i] = (int) Math.sqrt(a[i] * a[i] * a[i] * a[i] * a[i] + b[i] * b[i] * b[i] * b[i] * b[i]);
    }

    }
