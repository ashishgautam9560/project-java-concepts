package com.project.java.webapp.concepts.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BankAccount {

	private int balance = 100;

	// To avoid below Starvation issue - we can enable this lock as fair
	private  Lock lock = new ReentrantLock(true);
	
	

	public void withdraw(int amount) {
		log.info(Thread.currentThread().getName() + " attempting to withdraw " + amount);

		try {
			
			// Problem:
			// If t1 and t2 arrive at the same time, t1 acquires the lock and completes its work.
			// Meanwhile, t2 finds the lock held, fails to acquire it, and returns without retrying.
			// This means t2’s task is never executed.
			
			// lock.lock();
			// One solution is to use lock(), which forces threads to wait until the lock is available.
			// However, this can lead to starvation.
			
			// How Starvation ?
			// With only t1 and t2, there is no issue: t1 completes, then t2 acquires the lock.
			// But with many threads (t1, t2, ..., tn), there is no guarantee that t2,
			// which arrived second, will acquire the lock next.
			// Instead, whichever thread happens to call lock.lock() at the right moment
			// will get the lock, potentially delaying t2 indefinitely.

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
