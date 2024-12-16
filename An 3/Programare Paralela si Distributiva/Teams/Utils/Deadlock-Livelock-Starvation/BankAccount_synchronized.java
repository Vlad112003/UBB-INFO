package BankAccounts;
//Order based solution
public class BankAccount_corect {

	double balance;
    int id;
     
    public BankAccount_corect(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }
    synchronized
    void withdraw(double amount) {
        // Wait to simulate io like database access ...
        try {Thread.sleep(20l);} catch (InterruptedException e) {}
        balance -= amount;
    }
    synchronized
    void deposit(double amount) {
        // Wait to simulate io like database access ...
        try {Thread.sleep(20l);} catch (InterruptedException e) {}
        balance += amount;
    }
     
    static void transfer(BankAccount_corect from, BankAccount_corect to, double amount) { 
    	BankAccount_corect firstLock,secondLock;
    	try{
			if (from.id == to.id)
				throw new Exception("Cannot transfer from account to itself");
			else if (from.id < to.id) {   //ORDER!!!
					firstLock = from;
					secondLock = to;
				}
				else {
					firstLock = to;
					secondLock = from;
				}
			synchronized (firstLock) {
				  
				synchronized (secondLock) { 
					if (from.balance>amount) 
					{ 
						from.withdraw(amount); 
						to.deposit(amount);
						System.out.println("succes");
				        }
			    }
			}
    	}
		 catch(Exception e){
		        	 System.out.println("exceptie in "+Thread.currentThread().getName()+"de tip"+e);
		         }
    }
	public static void main(String[] args) {
        final BankAccount_corect fooAccount = new BankAccount_corect(1, 200d);
        final BankAccount_corect barAccount = new BankAccount_corect(2, 100d);
        
        Thread t1 =new Thread() {
            public void run() {
            	for (int i=0; i<5; i++)
            		BankAccount_corect.transfer(fooAccount, barAccount, 10d);
            }
        };
        t1.start();
         
        Thread t2 = new Thread() {
            public void run() {
            	for (int i=0; i<5; i++)
            		BankAccount_corect.transfer(barAccount, fooAccount, 15d);
            }
        };
        t2.start();
        try{
        t1.join(); t2.join();
        }
        catch(InterruptedException e){
        	
        }
        System.out.println("Main success");
        System.out.println(fooAccount.balance);
        System.out.println(barAccount.balance);
         }
        

	
}

	
	
