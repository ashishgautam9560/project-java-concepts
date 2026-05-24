package com.project.java.webapp.concepts.threads;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonitorLockExample {

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
