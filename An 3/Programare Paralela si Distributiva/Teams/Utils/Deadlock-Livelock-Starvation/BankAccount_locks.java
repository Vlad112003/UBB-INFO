package BankAccounts;
//deadlock with Lock
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
 
public class BankAccount3 {
    double balance;
    final int id;
    final Lock lock = new ReentrantLock();
    
    BankAccount3(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }
     
    void withdraw(double amount) {
    	lock.lock();
        // Wait to simulate io like database access ...
     //   try {Thread.sleep(100l);} catch (InterruptedException e) {}
        balance -= amount;
        lock.unlock();
    }
     
    void deposit(double amount) {
    	lock.lock();
        // Wait to simulate io like database access ...
     //   try {Thread.sleep(100l);} catch (InterruptedException e) {}
        balance += amount;
        lock.unlock();
    }
     
    static void transfer(BankAccount3 from, BankAccount3 to, double amount) {
    	//deadlock
    	
    	BankAccount3 first = (from.id>to.id)? from:to;
    	BankAccount3 second = (from.id>to.id)? to:from;
        first.lock.lock();
           // from.withdraw(amount);
        try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}    
        second.lock.lock();
      
                from.withdraw(amount);
                to.deposit(amount);
                
            second.lock.unlock();
        first.lock.unlock();
    }
     
    public static void main(String[] args) {
        final BankAccount3 fooAccount = new BankAccount3(1, 100d);
        final BankAccount3 barAccount = new BankAccount3(2, 100d);
       for(int i=0;i<5;i++)  {
        Thread t1 = new Thread() {
            public void run() {
                BankAccount3.transfer(fooAccount, barAccount, 10d);
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                BankAccount3.transfer(barAccount, fooAccount, 10d);
            }
        };

        t1.start();
        t2.start();
        try {
        	t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
       }
        System.out.println("succes!"+fooAccount.balance+" "+barAccount.balance);
         
    }
}

