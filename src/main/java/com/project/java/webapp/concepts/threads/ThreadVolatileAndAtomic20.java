package com.project.java.webapp.concepts.threads;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ThreadVolatileAndAtomic20 {
	
	
	/*
	 * 1. volatile keyword -
	 * 		is used to make sure that changes made by one thread to a variable are immediately visible to other threads.
	 * 
	 * 2. each thread keeps its own cached copy of variables for performance.
	 * 		So both threads have their own copy of 'flag'. 
	 * 		WriteThread has made the changes to flag=true. But ReaderThread is still contains old value in its cache , flag = false.
	 *      and readerThread will stuck in that infinite while loop.
	 * 		That's where we can use 'volatile'.
	 * 
	 * 2. Observation - 
	 * 		Even without volatile we can see that ReaderThread is able to see update on flag done by WriterThread. how ?
	 * 			A - Without volatile, there is no guarantee of visibility it may or may not happen.
	 * 				But with volatile there is guarantee of visibility.
	 */
	public static String threadVolatile() {

		SharedObj sharedObj = new SharedObj();

		Thread writerThread = new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			sharedObj.setFlagTrue();
		});

		Thread readerThread = new Thread(() -> sharedObj.printIfFlagTrue());

		writerThread.start();
		readerThread.start();

		return "Working with volatile.!!";
	}

	public static String threadAtomic() {
		AtomicCounter atomicCounter = new AtomicCounter();

		Runnable runnable = () -> {
			for (int i = 0; i < 10000; i++) {
				atomicCounter.increment();
			}
		};

		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		int result = atomicCounter.getCounter();
		log.info("result = {}", result);
		return "Working with Atomic.!!";
	}
}




/*
 * 1. When we are creating class here it is DEFAULT (means visible to only this package). 
 *    Because in one .java file only 1 public class can be there.
 */
@Slf4j
class SharedObj {

	/*
	 * private boolean flag = false;
	 */
	private volatile boolean flag = false;

	public void setFlagTrue() {
		flag = true;
		log.info("Write thread made the flag as 'true'");
	}

	public void printIfFlagTrue() {
		while (!flag) {
			// do nothing
		}
		log.info("Flag is true !!");
	}
}



/*
 * Volatile - now threads will only read the value of counter from MM and not from their cache.
 * 			  But still they both are doing write on it. So race conditions are there.
 * 
 * Solution - Use synchronized, or Atomic
 * 				Most commonly used:
 * 					AtomicInteger
 * 					AtomicLong
 * 					AtomicBoolean
 * 					AtomicReference
 *       
 */
class VolatileCounter {

	private volatile int counter = 0;

	public void increment() {
		counter++;
	}
	
	public int getCounter() {
		return counter;
	}
}

class AtomicCounter {

	private AtomicInteger counter = new AtomicInteger(0);

	public void increment() {
		counter.incrementAndGet();
	}

	public int getCounter() {
		return counter.get();
	}

}

