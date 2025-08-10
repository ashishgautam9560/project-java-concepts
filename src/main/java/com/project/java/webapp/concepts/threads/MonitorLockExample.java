package com.project.java.webapp.concepts.threads;

public class MonitorLockExample {

	public synchronized void task1() {
		try {
			System.out.println("Inside task 1");
			Thread.sleep(3000);
			System.out.println("Task 1 Completed");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void task2() {
		System.out.println("Inside task2, before synchronization");
		synchronized (this) {
			System.out.println("Inside task2, after synchronization");
		}

	}

	public synchronized void task3() {
		System.out.println("Inside task 3");
	}

}
