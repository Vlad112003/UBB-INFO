package BankAccounts;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
 /*
  * have to be changed!!!!!!!!!!!
  */
public class BankAccount_LiveLock {
    double balance;
    int id;
    Lock lock = new ReentrantLock();
 
    BankAccount_LiveLock(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }
 
    boolean withdraw(double amount) {
    	System.out.println("se incearca blocare cont "+id+"de catre"+Thread.currentThread().getId()); 
        if (this.lock.tryLock()) {
        	System.out.println("s-a blocat cont  "+id+"de catre"+Thread.currentThread().getId()); 
            // Wait to simulate io like database access ...
            try {Thread.sleep(1000l);} catch (InterruptedException e) {}
            balance -= amount;
            lock.unlock();
            System.out.println("s-a deblocat cont "+id+"de catre"+Thread.currentThread().getId()); 
            return true;
        }
        return false;
    }
 
    boolean deposit(double amount) {
    	System.out.println("se incearca blocare cont  "+id+"de catre"+Thread.currentThread().getId()); 
        if (this.lock.tryLock()) {
        	System.out.println("s-a blocat cont "+id+"de catre"+Thread.currentThread().getId()); 
            // Wait to simulate io like database access ...
            try {Thread.sleep(1000l);} catch (InterruptedException e) {}
            balance += amount;
            lock.unlock();
            System.out.println("s-a deblocat cont "+id+"de catre"+Thread.currentThread().getId()); 
            return true;
        }
        System.out.println("nu s-a reusit blocare cont  "+id+"de catre"+Thread.currentThread().getId()); 
        return false;
    }
 
    public boolean tryTransfer(BankAccount_LiveLock destinationAccount, double amount) {
    	boolean finalized=false;
        if (this.lock.tryLock()){
    	//lock.lock();
        	if(destinationAccount.lock.tryLock()){
        		System.out.printf("s-au blocat conturile %d si %d de catre threadul %s",id,destinationAccount.id, Thread.currentThread().getName()); 
        		this.withdraw(amount); 
        		destinationAccount.deposit(amount);    		
        		destinationAccount.lock.unlock();
        		finalized = true;
        	}
          lock.unlock();
        }
        return finalized;
//        	 if (this.withdraw(amount))
//	            if (destinationAccount.deposit(amount)) {
//	                return true;
//	            } else {
//	                // destination account busy, refund source account.
//	                this.deposit(amount);
//	            }
//        	 
//        	 return false;
    }
 
    public static void main(String[] args) {
        final BankAccount_LiveLock fooAccount = new BankAccount_LiveLock(1, 500d);
        final BankAccount_LiveLock barAccount = new BankAccount_LiveLock(2, 500d);
 
        Thread t1,t2;
        t1 = new Thread(new Transaction(fooAccount, barAccount, 10d), "transaction-1");
        t2 = new Thread(new Transaction(barAccount, fooAccount, 10d), "transaction-2");
        t1.start(); t2.start();
        try {
			t1.join();t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
 
    }
 
}
class Transaction implements Runnable {
    private BankAccount_LiveLock sourceAccount, destinationAccount;
    private double amount;
 
    Transaction(BankAccount_LiveLock sourceAccount, BankAccount_LiveLock destinationAccount, double amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }
 
    public void run() {
        while (!sourceAccount.tryTransfer(destinationAccount, amount)){
        	System.out.printf("threadul %s nu a reusit transferul\n", Thread.currentThread().getName());
            continue;
            
        }
        System.out.printf("%s completed ", Thread.currentThread().getName());
    }
 
}

