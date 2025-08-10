package com.project.java.webapp.concepts.threads;

public class ProduceTask implements Runnable {

	private final SharedResource sharedResource;

	public ProduceTask(SharedResource sharedResource) {
		this.sharedResource = sharedResource;
	}

	@Override
	public void run() {
		System.out.println("Producer Thread " + Thread.currentThread().getName());

		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		sharedResource.addItem();
	}

}
