package com.project.java.webapp.concepts.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReentrantLockExample {

	private final Lock lock = new ReentrantLock();

	public void outerMethod() {

		try {
			log.info("Outer Method");
			innerMethod();
		} finally {
			lock.unlock();
		}
	}

	public void innerMethod() {

		// We are putting lock back. means t1 has to wait for itself to release lock
		// It is DeadLock.. t1 is depending on t1 to release the lock.

		// So ReentrantLock -> Re Enter -> as lock is on itself so t1 can re-enter the
		// lock it already holds.
		// That is the key feature of a "reentrant" lock.

		// **The number of lock() calls must match the number of unlock() calls.\
		// If unlock() > lock() -> java.lang.IllegalMonitorStateException
		// If lock() > unlock() -> no exception.. but we are not releasing the lock.
		// So not good for future threads.
		lock.lock();

		try {
			log.info("Inner Method");
		} finally {
			lock.unlock();
		}
	}

}
