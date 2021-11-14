package edu.uic.cs554.main;

/**
 * Main class to initialize thread and perform different transactions.
 * 
 * @author Arvind Gupta
 *
 */
public class Main {

	/**
	 * main method.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		//Different SavingsAccount objects.
		SavingsAccount myAccount = new SavingsAccount(200);
		SavingsAccount yourAccount = new SavingsAccount(100);
		SavingsAccount hisAccount = new SavingsAccount();
		SavingsAccount commonAccount = new SavingsAccount(500);
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				myAccount.deposit(500);
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				myAccount.withdraw(1000, true);
			}
		});
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				yourAccount.deposit(200);
			}
		});
		Thread t4 = new Thread(new Runnable() {
			@Override
			public void run() {
				myAccount.withdraw(100, false);
			}
		});
		Thread t5 = new Thread(new Runnable() {
			@Override
			public void run() {
				yourAccount.withdraw(400, false);
			}
		});
		Thread t6 = new Thread(new Runnable() {
			@Override
			public void run() {
				myAccount.deposit(500);
			}
		});
		Thread t7 = new Thread(new Runnable() {
			@Override
			public void run() {
				yourAccount.deposit(200);
			}
		});
		Thread t8 = new Thread(new Runnable() {
			@Override
			public void run() {
				yourAccount.withdraw(200, true);
			}
		});
		Thread t9 = new Thread(new Runnable() {
			@Override
			public void run() {
				yourAccount.deposit(500);
			}
		});
		Thread t10 = new Thread(new Runnable() {
			@Override
			public void run() {
				hisAccount.withdraw(400, true);
			}
		});
		Thread t11 = new Thread(new Runnable() {
			@Override
			public void run() {
				hisAccount.deposit(600);
			}
		});
		Thread t12 = new Thread(new Runnable() {
			@Override
			public void run() {
				myAccount.transfer(300, commonAccount);
			}
		});
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
		t9.start();
		t10.start();
		t11.start();
		t12.start();
	}
}
