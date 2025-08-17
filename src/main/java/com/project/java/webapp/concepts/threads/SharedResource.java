package com.project.java.webapp.concepts.threads;

import java.util.concurrent.ForkJoinPool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SharedResource {

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
