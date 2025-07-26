package ro.mpp.javaMPPServer;

public class MyThread extends Thread{

    private int[] a;
    private int[] b;
    private int[] c;
    private int start;
    private int end;



    MyThread(int[] a, int[] b, int[] c, int start, int end){
        this.a=a;
        this.b=b;
        this.c=c;
        this.start=start;
        this.end=end;

    }

    @Override
    public void run(){
         for(int i = this.start; i < this.end; i++){
             this.c[i] = (int)Math.sqrt(this.a[i]*a[i]*a[i]*a[i]*a[i] + b[i]*b[i]*b[i]*b[i]*b[i]);
         }
    }
}
