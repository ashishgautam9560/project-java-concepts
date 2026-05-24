package com.project.java.webapp.concepts.threads;

import lombok.Getter;

public class Counter {
	
	@Getter
	private int count = 0;

	public synchronized void increment() {
		count += 1;
	}
}
