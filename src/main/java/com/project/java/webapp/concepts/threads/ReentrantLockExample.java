package com.project.java.webapp.concepts.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReentrantLockExample {

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
