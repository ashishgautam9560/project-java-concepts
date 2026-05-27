package com.project.java.webapp.concepts.threads;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class MonitorLockExample {

	public synchronized void task1() {
		try {
			log.info("Inside task 1");
			Thread.sleep(3000);
			log.info("Task 1 Completed");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}

	public void task2() {
		log.info("Inside task2, before synchronization");
		synchronized (this) {
			log.info("Inside task2, after synchronization");
		}
	}

	public synchronized void task3() {
		log.info("Inside task 3");
	}
}


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadLockFairness9 {

	/*
	 * Refer 'MonitorLockExample' to understand 'Unfairness' problem.
	 */
	public static String monitorLocks() {

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

		return "Monitor Lock using synchronized is working";
	}

}
