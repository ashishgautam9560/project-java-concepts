package com.project.java.webapp.concepts.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java.webapp.concepts.threads.BankAccount;
import com.project.java.webapp.concepts.threads.Consumer;
import com.project.java.webapp.concepts.threads.Counter;
import com.project.java.webapp.concepts.threads.MonitorLockExample;
import com.project.java.webapp.concepts.threads.Paper;
import com.project.java.webapp.concepts.threads.Pen;
import com.project.java.webapp.concepts.threads.Producer;
import com.project.java.webapp.concepts.threads.ReadWriteCounter;
import com.project.java.webapp.concepts.threads.ReentrantLockExample;
import com.project.java.webapp.concepts.threads.SharedResource;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "thread")
@Slf4j
public class ThreadController {

	@GetMapping(value = "monitor-lock")
	public ResponseEntity<String> analyzeThread() {

		MonitorLockExample obj = new MonitorLockExample();
		MonitorLockExample obj1 = new MonitorLockExample();

		Thread t1 = new Thread(obj::task1);
		Thread t2 = new Thread(obj::task2);
		Thread t3 = new Thread(obj::task3);
		Thread t4 = new Thread(obj1::task2);

		t1.start();
		t2.start();
		t3.start();
		t4.start();

		return new ResponseEntity<>("Monitor Lock using synchronized is working", HttpStatus.OK);
	}

	@GetMapping(value = "counter")
	public ResponseEntity<String> counterSynchronized() {

		Counter counter = new Counter();
		Runnable runnable = () -> {
			for (int i = 0; i < 100000; i++) {
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
		} catch (Exception e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}

		return new ResponseEntity<>("Count after all threads completed their running = " + counter.getCount(),
				HttpStatus.OK);
	}

	@GetMapping(value = "locks")
	public ResponseEntity<String> locks() {
		
		
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

		return new ResponseEntity<>("Locks are working..!!", HttpStatus.OK);
	}

	@GetMapping(value = "reentrant-lock")
	public ResponseEntity<String> reentrantLocks() {

		ReentrantLockExample reentrantLockExample = new ReentrantLockExample();
		Runnable runnable = reentrantLockExample::outerMethod;

		Thread t1 = new Thread(runnable, "t1");
		t1.start();

		try {
			t1.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		return new ResponseEntity<>("ReentrantLock is working..!!", HttpStatus.OK);
	}

	@GetMapping("read-write-lock")
	public ResponseEntity<String> readWriteLock() {
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

		return new ResponseEntity<>("ReadWriteLock is working..!!", HttpStatus.OK);
	}

	// When below 4 conditions are met:
	// 1. Mutual Exclusion
	// 2. Hold and Wait
	// 3. No Preemption
	// 4. Circular Wait
	@GetMapping("dead-lock")
	public ResponseEntity<String> deadLock() {

		@AllArgsConstructor
		class Task1 implements Runnable {
			private Pen pen;
			private Paper paper;

			@Override
			public void run() {
				pen.writeWithPenAndPaper(paper); // Thread-0 locks pen and tries to lock paper
			}
		}

		@AllArgsConstructor
		class Task2 implements Runnable {
			private Pen pen;
			private Paper paper;

			@Override
			public void run() {
				// solution - 1st have the lock of pen then only put lock on paper
				synchronized (pen) {
					paper.writeWithPaperAndPen(pen);
				}

				// paper.writeWithPaperAndPen(pen); Thread-1 locks paper and tries to lock pen
			}
		}

		Pen pen = new Pen();
		Paper paper = new Paper();

		Thread thread1 = new Thread(new Task1(pen, paper), "Thread-0");
		Thread thread2 = new Thread(new Task2(pen, paper), "Thread-1");

		thread1.start();
		thread2.start();

		return new ResponseEntity<>("DeadLock is working..!!", HttpStatus.OK);

	}

	@GetMapping("communication")
	public ResponseEntity<String> threadCommunication() {

		SharedResource sharedResource = new SharedResource();
		Thread producerThread = new Thread(new Producer(sharedResource), "Producer-Thread");
		Thread consumerThread = new Thread(new Consumer(sharedResource), "Consumer-Thread");

		producerThread.start();
		consumerThread.start();

		try {
			producerThread.join();
			consumerThread.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}

		return new ResponseEntity<>("Thread Communication is working..!!", HttpStatus.OK);
	}
}
