package com.project.java.webapp.concepts.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.java.webapp.concepts.threads.BankAccount;
import com.project.java.webapp.concepts.threads.Counter;
import com.project.java.webapp.concepts.threads.MonitorLockExample;
import com.project.java.webapp.concepts.threads.ReentrantLockExample;

@RestController
@RequestMapping(value = "thread")
public class ThreadController {

	@GetMapping(value = "monitor-lock")
	public ResponseEntity<String> analyzeThread() {

		MonitorLockExample obj = new MonitorLockExample();
		MonitorLockExample obj1 = new MonitorLockExample();

		Thread t1 = new Thread(() -> obj.task1());
		Thread t2 = new Thread(() -> obj.task2());
		Thread t3 = new Thread(() -> obj.task3());

		Thread t4 = new Thread(() -> obj1.task2());

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
		Runnable runnable = ()->reentrantLockExample.outerMethod();
		
		Thread t1 = new Thread(runnable, "t1");
		t1.start();
		
		try {
			t1.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
		return new ResponseEntity<>("ReentrantLock is working..!!", HttpStatus.OK);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
