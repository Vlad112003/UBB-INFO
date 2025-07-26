package BankAccounts;


public class BankAccount_Starvation {
    private double balance;
    int id;
 
    BankAccount_Starvation(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }
     
    synchronized double getBalance() {
        // Wait to simulate io like database access ...
        try {Thread.sleep(10l);} catch (InterruptedException e) {}
        return balance;
    }
     
    synchronized void withdraw(double amount) {
        balance -= amount;
    }
 
    synchronized void deposit(double amount) {
        balance += amount;
    }
 
    synchronized void transfer(BankAccount_Starvation to, double amount) {
            withdraw(amount);
            System.out.format("%s withdraw has been executed from %d %n", Thread.currentThread().getName(),this.id);
            to.deposit(amount);
            System.out.format("%s deposit has been executed to %d %n", Thread.currentThread().getName(), to.id);
    }
 
    public static void main(String[] args) {
        final int MAX=200;
    	System.out.println(Runtime.getRuntime().availableProcessors());
    	
    	
        final BankAccount_Starvation fooAccount = new BankAccount_Starvation(1, 500d);
        final BankAccount_Starvation barAccount = new BankAccount_Starvation(2, 500d);
         
        Thread balanceMonitorThread [] = new Thread[MAX];
       //ORDERED
        Thread transactionThread1 = new Thread(new TransactionS(fooAccount, barAccount, 25d), "Transaction-1");
        Thread transactionThread2 = new Thread(new TransactionS(fooAccount, barAccount, 25d), "Transaction-2");
        
        for (int i=0;i<MAX;i++){
        	balanceMonitorThread [i] = new Thread(new BalanceMonitor(fooAccount), "BalanceMonitor");
        	balanceMonitorThread[i].setPriority(Thread.MAX_PRIORITY);//!!!!!!!!!!!!!!!!!!
        }
        	
       
        transactionThread1.setPriority(Thread.MIN_PRIORITY);//!!!!!!!!!!!!!!!!!!!!!
        transactionThread2.setPriority(Thread.MIN_PRIORITY);//!!!!!!!!!!!!!!!!!!!!!!!
         
        // Start the monitors
        for (int i=0;i<MAX;i++)
        	balanceMonitorThread[i].start();
       
         
        // And later, transaction threads tries to execute.
       try {Thread.sleep(10l);} catch (InterruptedException e) {}
        transactionThread1.start();
        transactionThread2.start();
 
    }
 
}
class BalanceMonitor implements Runnable {
    private BankAccount_Starvation account;
    BalanceMonitor(BankAccount_Starvation account) { this.account = account;}
    boolean alreadyNotified = false;
     
    @Override
    public void run() {
        System.out.format("%s started execution"+ "time= "+System.currentTimeMillis()+"\n", Thread.currentThread().getName());
        while (true) {
            if(account.getBalance() <= 0) {
                break;
            }
        }
        System.out.format("%s : account has gone too low, email sent, exiting.\n", Thread.currentThread().getName());
    }
     
}
class TransactionS implements Runnable {
    private BankAccount_Starvation sourceAccount, destinationAccount;
    private double amount;
 
    TransactionS(BankAccount_Starvation sourceAccount, BankAccount_Starvation destinationAccount, double amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }
 
    public void run() {
    	long start= System.currentTimeMillis();
        System.out.format("%s started execution time= "+System.currentTimeMillis()+"\n", Thread.currentThread().getName());
        for(int i=0;i<10;i++)
        	sourceAccount.transfer(destinationAccount, amount);
        long end= System.currentTimeMillis();
        System.out.printf("%s completed execution time= "+(end-start)+"\n", Thread.currentThread().getName());
    }
 
}

