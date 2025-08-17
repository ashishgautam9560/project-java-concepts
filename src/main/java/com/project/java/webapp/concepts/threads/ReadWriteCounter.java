package com.project.java.webapp.concepts.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteCounter {

	private int count = 0;

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();

	// If no threads are writing or reading,
	// only one thread at a moment can lock the lock for writing.
	public void increment() {
		writeLock.lock();

		try {
			count++;
		} finally {
			writeLock.unlock();
		}
	}

	// If there is no thread that has acquired the write lock
	// then multiple threads can lock the lock for reading.
	public int getCount() {
		readLock.lock();

		try {
			return count;
		} finally {
			readLock.unlock();
		}
	}

}
