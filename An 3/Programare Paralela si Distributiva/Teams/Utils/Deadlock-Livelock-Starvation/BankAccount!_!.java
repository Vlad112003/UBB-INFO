package BankAccounts;
//deadlock with synchronized
public class BankAccount {
	    double balance;
	    int id;
	    BankAccount(int id, double balance) {
	        this.id = id;
	        this.balance = balance;
	    }
	    public  String toString(){
	    	 return "cont "+id;
	     }
	   // synchronized 
	    void  withdraw(double amount) {
	    	synchronized(this)
	    	{
	        // Wait to simulate io like database access ...
	        try {Thread.sleep(10l);} catch (InterruptedException e) {}
	        balance -= amount;
	    	}
	    }
	     
	    synchronized 
	    void  deposit(double amount) {
	        // Wait to simulate io like database access ...
	        try {Thread.sleep(10l);} catch (InterruptedException e) {}
	        balance += amount;
	    }
	     
	   // synchronized 
	    static void transfer(BankAccount from, BankAccount to, double amount) {
	    	System.out.println("se incearca blocare  "+from+"de catre "+Thread.currentThread().getId()); 
	        synchronized(from) {
	        	System.out.println("s-a blocat  "+from+"de catre "+Thread.currentThread().getId()); 
	        	//from.withdraw(amount); ///!!nu e locul potrivit
	            try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	System.out.println("se incearca blocare  "+to+"de catre "+Thread.currentThread().getId());
	            synchronized(to) {
	            	from.withdraw(amount);
	            	System.out.println("s-a blocat  "+to+ "de catre "+Thread.currentThread().getId()); 
	                to.deposit(amount);
	            }
	        }
	    }
	     
	    public static void main(String[] args) {
	        final BankAccount fooAccount = new BankAccount(1, 100d);
	        final BankAccount barAccount = new BankAccount(2, 100d);
	         
	        Thread t1= new Thread() {
	            public void run() {
//	            	fooAccount.deposit(100);
//	            	System.out.println(Thread.currentThread().getId()+ "Foo balance="+fooAccount.balance);
	               BankAccount.transfer(fooAccount, barAccount, 10d);
	            }
	        };
	       
	         
	        Thread t2=  new Thread() {
	            public void run() {

//	            	barAccount.deposit(100);
//            	fooAccount.deposit(100);
	            	System.out.println(Thread.currentThread().getId()+ "Foo balance="+fooAccount.balance);
//	            	System.out.println("by "+Thread.currentThread().getId()+ "Bar balance="+barAccount.balance);
	              BankAccount.transfer(barAccount, fooAccount, 10d);
	            }
	        };
	        t1.start();
	        t2.start();
	     
	        
	        try {
				t1.join();t2.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	         
	         System.out.println("success! ");
	    }
	
	
	
}

