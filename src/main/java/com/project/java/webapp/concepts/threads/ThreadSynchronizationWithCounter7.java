package com.project.java.webapp.concepts.threads;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


class Counter {

	@Getter
	private int count = 0;

	public synchronized void increment() {
		count += 1;
	}
}

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadSynchronizationWithCounter7 {

	/*
	 * 1. It should be 30000 once all 3 threads complete. But we are seeing anamolies in it.
	 * 2. Solution - add 'synchronized' in CS (critical-section) - Counter.increment()
	 * 			   - add 'synchronized' block directly to that lines of code - synchronized (this) {count += 1;}
	 * 3. Synchronized / Intrinsic / Monitor - All are same. and all are Unfair locks.
	 */			   
	public static String counterThreadsWithSynchronized() {
		Counter counter = new Counter();
		Runnable runnable = () -> {
			for (int i = 0; i < 10000; i++) {
				counter.increment();
			}
		};

		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);
		Thread t3 = new Thread(runnable);

		t1.start();
		t2.start();
		t3.start();

		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.error("Exception occurred: {}", e.getMessage());
		}
		return "Counter value after processing: " + counter.getCount();
	}

}
