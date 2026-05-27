package com.project.java.webapp.concepts.threads;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class SharedResource {

	private int data;
	private boolean hasData;

	// wait() and notify() are not synchronized - so explicitly made them
	public synchronized void produce(int value) {

		while (hasData) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}

		data = value;
		hasData = true;
		log.info("Produced: " + value);
		notifyAll();
	}

	public synchronized int consume() {
		while (!hasData) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}

		hasData = false;
		log.info("Consumed: " + data);
		notifyAll();
		return data;
	}
}

@AllArgsConstructor
class Producer implements Runnable {

	private SharedResource sharedResource;

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			sharedResource.produce(i);
		}
	}
}

@AllArgsConstructor
class Consumer implements Runnable {

	private SharedResource sharedResource;

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			sharedResource.consume();
		}
	}
}

public class ThreadCommunication12 {

	public String threadCommunication() {

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

		return "Thread Communication is working..!!";
	}

}
