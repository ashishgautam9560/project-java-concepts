package com.project.java.webapp.concepts.threads;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Producer implements Runnable {

	private SharedResource sharedResource;

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			sharedResource.produce(i);
		}
	}

}
