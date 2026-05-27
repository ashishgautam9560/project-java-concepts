package com.project.java.webapp.concepts.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class BankAccount {

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




@Slf4j
class ReentrantLockExample {

	private final Lock lock = new ReentrantLock();

	public void outerMethod() {
		lock.lock();
		
		try {
			log.info("Outer Method");
			innerMethod();
		} finally {
			lock.unlock();
		}
	}

	
	
	/*
	 * 1. T1 already have acquire the lock() in outerMethod() itself.
	 * 		Now we are putting lock() back here, means T1 has to wait for itself to release lock 
	 * 		It is DeadLock.. T1 is depending on t1 to release the lock.
	 * 
	 * 2. Here Reentrant Lock helps - 
	 * 		ReentrantLock -> Re Enter -> As lock is on itself so T1 can re-enter the lock it already holds. 
	 * 		This is the key feature of a "reentrant" lock.
	 * 
	 * 3. Behavior - 
	 * 		The number of lock() calls must match the number of unlock() calls.
	 * 		If unlock() > lock() -> java.lang.IllegalMonitorStateException.
	 * 		If lock() > unlock() -> no exception.. but we are not releasing the lock. So not good for future threads.
	 * 		
	 * 
	 */
	public void innerMethod() {
		lock.lock();

		try {
			log.info("Inner Method");
		} finally {
			lock.unlock();
		}
	}
}



@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadReentrantLock8 {
	
	/*
	 * Locks - Restricting a CS so that at a time only 1 thread can access it. Conceptually similar like synchronized.
	 * Types - Intrinsic and Explicit.
	 * 1. Intrinsic - They are built into every OBJECT(it puts lock on object) in Java. When we use a synchronized keyword, we are using these automatic locks.
	 *    Also called as Monitor Locks - It blocks the multiple threads of same object. But if multiple threads of different objects are coming it provides access.
	 *    For Eg - Below 'BankAccount' has t1 and t2.. means they both belongs to same object of BankAccount. Here monitor lock will work.
	 *    		   But if t1 - BankAccount1 and t2 - BankAccount2.. there will no locking here.
	 * 
	 * 2. Explicit  - more advanced locks, we can control explicitly control when to lock and unlock using Lock.class
	 * 
	 * 3. Barging problem - It occurs when a waiting thread is notified that a shared resource is available, 
	 * 					    but another newly arriving (or "barging") thread sneaks in and claims the resource first.
	 * 							1. T1 acquires lock
	 *							2. T2 tries → goes to BLOCKED
	 *							3. T1 releases lock
	 *							4. Before T2 gets scheduled again, T3 arrives
	 *							5. T3 acquires the lock
	 *							6. T2 remains blocked.
	 *							7. Same cycle can repeat for T2 for other arriving threads leading to indefinite waiting.
	 *					   - It can cause - Starvation, 
	 *									  - Unfairness - Multithreading environments usually prioritize (FIFO) ordering. Barging breaks this queue.
	 *									  - Timeouts - threads might time out or throw errors because their expected window of execution was stolen.
     *
	 * 
	 * 4. To avoid above issue - Use Explicit locks. Refer 'BankAccount' class.
	 * 
	 */
	public static String locksBankAccount() {
		BankAccount bankAccount = new BankAccount();

		Runnable task = () -> bankAccount.withdraw(50);

		Thread t1 = new Thread(task, "Thread-1");
		Thread t2 = new Thread(task, "Thread-2");

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
		return "Locks are working..!!";
	}

	
	/*
	 * Refer 'ReentrantLockExample' to understand 'ReentrantLock' working.
	 */
	public static String reentrantLocks() {

		ReentrantLockExample reentrantLockExample = new ReentrantLockExample();
		Runnable runnable = () -> reentrantLockExample.outerMethod();

		Thread t1 = new Thread(runnable, "t1");
		t1.start();

		try {
			t1.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		return "ReentrantLock is working..!!";
	}

}
