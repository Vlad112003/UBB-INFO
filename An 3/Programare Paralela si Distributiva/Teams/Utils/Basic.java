
public class Basic {

	static class MyThread extends Thread{
		public void run() {
			System.out.println("hello from thread");
		}
	}
	static int y=6;
	static class MyRunnanble implements Runnable{
		public void run() {
			System.out.println("hello from runnable");
		}
	}
	public static void main(String a[]) {
		MyThread t1= new MyThread();
		t1.start();  
		try {
			t1.join();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Thread t2= new Thread( new MyRunnanble());
		t2.start();  
		try {
			t2.join();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int x=9; 
		
		Thread t3= new Thread( ()->{
			//x++;  eroare
			y ++;
			System.out.println("hello from  lambda runnable thread"+x+"  "+y );}   );
		t3.start();  
		try {
			t3.join();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
