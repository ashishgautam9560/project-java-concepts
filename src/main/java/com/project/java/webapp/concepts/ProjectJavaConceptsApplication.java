package com.project.java.webapp.concepts;

import com.project.java.webapp.concepts.threads.Counter;

public class ProjectJavaConceptsApplication {

	public static void main(String[] args) {

		Counter counter = new Counter();

		Runnable runnable = () -> {
			for (int i = 0; i < 10000; i++) {
				counter.increment();
			}
		};

		Thread t1 = new Thread(runnable);
		Thread t2 = new Thread(runnable);
		Thread t3 = new Thread(runnable);

		t1.start();
		t2.start();
		t3.start();

		try {
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}

		System.out.println(counter.getCount());
	}

}
