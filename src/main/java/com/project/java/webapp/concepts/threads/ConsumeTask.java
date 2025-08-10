package com.project.java.webapp.concepts.threads;

public class ConsumeTask implements Runnable {

	private final SharedResource sharedResource;

	public ConsumeTask(SharedResource sharedResource) {
		this.sharedResource = sharedResource;
	}

	@Override
	public void run() {
		System.out.println("Consumer Thread " + Thread.currentThread().getName());
		sharedResource.consumeItem();
	}

}
