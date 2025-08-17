package com.project.java.webapp.concepts.threads;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Consumer implements Runnable {

	private SharedResource sharedResource;

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			sharedResource.consume();
		}
	}
}