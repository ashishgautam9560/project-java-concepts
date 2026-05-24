package com.project.java.webapp.concepts.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BankAccount {

	private int balance = 100;

	// To avoid below Starvation issue - we can enable this lock as fair
	private final Lock lock = new ReentrantLock(true);
	
	/*
	 * 	1. Lock lock = new ReentrantLock(); 
	 * 	   lock.tryLock(1000, TimeUnit.MILLISECONDS) -
	 * 	   	- Acquires the lock if it is free within the given waiting time.
	 * 		- If the lock is not available then the current thread becomes disabled for thread scheduling purposes.
	 * 
	 * 	    Problem:
	 *		 	 If t1 and t2 arrive at the same time, t1 acquires the lock and completes its work.
	 *			 Meanwhile, t2 finds the lock held, fails to acquire it, and returns without retrying.
	 *			 This means t2’s task is never executed.
	 *
	 *  2. Lock lock = new ReentrantLock(); 
	 *     lock.lock();
	 *     	- waits until lock becomes available.
	 *      - guarantees lock acquisition eventually (unless deadlock/interruption issues)
	 *      - It can leads to `Barging problem` ending up with Starvation, Unfairness, Timeouts(TTL).
	 *      
	 *  3. Lock lock = new ReentrantLock(true);
	 *  	- Enable 'fairness' policy.
	 *  
	 *  
	 *  Other methods - 
	 *  1. lock.unlock() - Releases the lock. When current thread finishes CS , it can release the lock on CS.
	 * 
	 */
	

	public void withdraw(int amount) {
		log.info(Thread.currentThread().getName() + " attempting to withdraw " + amount);

		try {

			if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {

				if (balance >= amount) {

					try {

						log.info(Thread.currentThread().getName() + " proceeding with withdrawal.");
						Thread.sleep(10000);
						balance -= amount;
						log.info(Thread.currentThread().getName() + " completed withdrawal. Remaining balance: "
								+ balance);

					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						e.printStackTrace();

					} finally {

						// Once work is done, its necessary to release the lock.
						// Otherwise it will be infinite lock
						lock.unlock();
					}

				} else {
					log.info(Thread.currentThread().getName() + " insufficient balance.");
				}

			} else {
				log.info(Thread.currentThread().getName() + " could not acquire the lock, will try again later.");
			}

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}

	}

}
