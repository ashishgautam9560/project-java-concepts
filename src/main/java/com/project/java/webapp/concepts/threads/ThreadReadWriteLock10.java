package com.project.java.webapp.concepts.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

class ReadWriteCounter {

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

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadReadWriteLock10 {

	public static String readWriteLock() {

		ReadWriteCounter counter = new ReadWriteCounter();

		Runnable readTask = () -> {
			for (int i = 0; i < 10; i++) {
				log.info(Thread.currentThread().getName() + " read: " + counter.getCount());
			}
		};

		Runnable writeTask = () -> {
			for (int i = 0; i < 10; i++) {
				counter.increment();
				log.info(Thread.currentThread().getName() + " incremented");
			}
		};

		Thread writerThread = new Thread(writeTask, "Thread-0");
		Thread readerThread1 = new Thread(readTask, "Thread-1");
		Thread readerThread2 = new Thread(readTask, "Thread-2");

		writerThread.start();
		readerThread1.start();
		readerThread2.start();

		try {
			writerThread.join();
			readerThread1.join();
			readerThread2.join();

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}

		return "ReadWriteLock is working..!!";
	}

}
