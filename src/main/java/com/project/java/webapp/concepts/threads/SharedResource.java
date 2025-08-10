package com.project.java.webapp.concepts.threads;

public class SharedResource {

	boolean itemAvailable = false;

	public synchronized void addItem() {
		itemAvailable = true;
		System.out.println(
				"Item added by " + Thread.currentThread().getName() + " and invoking all threads which are waiting.");
		notifyAll();
	}

	public synchronized void consumeItem() {
		System.out.println("consumeItem() method invoked by - " + Thread.currentThread().getName());
		while (!itemAvailable) {
			try {
				System.out.println("Thread " + Thread.currentThread().getName() + " is waiting now.");
				wait();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Item consumed by - " + Thread.currentThread().getName());
		itemAvailable = false;

	}
}
