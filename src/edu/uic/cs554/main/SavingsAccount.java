package edu.uic.cs554.main;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * implemented to take care of all the transactions such as deposit and
 * withdrawals from the account.
 * 
 * @author Arvind Gupta
 *
 */
public class SavingsAccount {
	Lock lock = new ReentrantLock();
	Condition minBalanceCondition = lock.newCondition();
	Condition preferedWithdrawalCondition = lock.newCondition();

	int currentBalance = 0;
	int countPreferedWithdrawals = 0;

	/**
	 * constructor.
	 * 
	 * @param currentBalance
	 */
	public SavingsAccount(int currentBalance) {
		this.currentBalance = currentBalance;
	}

	/**
	 * constructor
	 */
	public SavingsAccount() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * implemented to deposit the given amount into the account if and only if there
	 * is no other operation being performed on that account.
	 * 
	 * @param depositAmount
	 */
	public void deposit(int depositAmount) {
		lock.lock();
		try {
			currentBalance += depositAmount;
			minBalanceCondition.signal();
		} finally {
			lock.unlock();
		}

		System.out.println("Deposited amount : " + depositAmount + ", current Balance : " + currentBalance);
	}

	/**
	 * implemented to withdraw the given amount from the account based on if it is
	 * an ordinary withdrawal or preferred withdrawal.
	 * 
	 * @param withdrawalAmount
	 * @param isPreferedWithdrawal
	 */
	public void withdraw(int withdrawalAmount, boolean isPreferedWithdrawal) {

		if (isPreferedWithdrawal) {
			preferedWithdrawal(withdrawalAmount);
		} else {

			lock.lock();
			try {
				while (currentBalance < withdrawalAmount) {
					try {
						minBalanceCondition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					while (countPreferedWithdrawals > 0) {
						try {
							preferedWithdrawalCondition.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				currentBalance -= withdrawalAmount;
			} finally {
				lock.unlock();
			}
			System.out.println("Withdrawn amount : " + withdrawalAmount + ", current balance : " + currentBalance);
		}
	}

	/**
	 * implemented to withdraw the given amount from the account if it is a
	 * preferred withdrawal.
	 * 
	 * @param withdrawalAmount
	 */
	public void preferedWithdrawal(int withdrawalAmount) {
		lock.lock();
		countPreferedWithdrawals++;

		try {

			while (currentBalance < withdrawalAmount) {
				try {
					minBalanceCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			currentBalance -= withdrawalAmount;
			countPreferedWithdrawals--;
			preferedWithdrawalCondition.signalAll();

		} finally {
			lock.unlock();
		}
		System.out.println("Withdrawn amount : " + withdrawalAmount + ", current balance : " + currentBalance);
	}

	/**
	 * implemented to transfer the given amount from reserve to current account
	 * object.
	 * 
	 * @param transferAmount
	 * @param reserve
	 */
	void transfer(int transferAmount, SavingsAccount reserve) {
		lock.lock();
		try {
			reserve.withdraw(transferAmount, false);
			deposit(transferAmount);
		} finally {
			lock.unlock();
		}
	}
}