package com.project.java.webapp.concepts.threads;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
class Pen {

	public synchronized void writeWithPenAndPaper(Paper paper) {
		log.info(Thread.currentThread().getName() + " is using pen " + this + " and trying to use paper " + paper);
		paper.finishWriting();
	}

	public synchronized void finishWriting() {
		log.info(Thread.currentThread().getName() + " finished using pen " + this);
	}
}

@Slf4j
@ToString
class Paper {

	public synchronized void writeWithPaperAndPen(Pen pen) {
		log.info(Thread.currentThread().getName() + " is using paper " + this + " and trying to use pen " + pen);
		pen.finishWriting();
	}

	public synchronized void finishWriting() {
		log.info(Thread.currentThread().getName() + " finished using pen " + this);
	}

}

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadDeadlock11 {

	// When below 4 conditions are met:
	// 1. Mutual Exclusion
	// 2. Hold and Wait
	// 3. No Preemption
	// 4. Circular Wait
	public static String deadLock() {

		@AllArgsConstructor
		class Task1 implements Runnable {
			private Pen pen;
			private Paper paper;

			@Override
			public void run() {
				pen.writeWithPenAndPaper(paper); // Thread-0 locks pen and tries to lock paper
			}
		}

		@AllArgsConstructor
		class Task2 implements Runnable {
			private Pen pen;
			private Paper paper;

			@Override
			public void run() {
				// solution - 1st have the lock of pen then only put lock on paper
				synchronized (pen) {
					paper.writeWithPaperAndPen(pen);
				}

				// paper.writeWithPaperAndPen(pen); Thread-1 locks paper and tries to lock pen
			}
		}

		Pen pen = new Pen();
		Paper paper = new Paper();

		Thread thread1 = new Thread(new Task1(pen, paper), "Thread-0");
		Thread thread2 = new Thread(new Task2(pen, paper), "Thread-1");

		thread1.start();
		thread2.start();

		return "DeadLock is working..!!";
	}

}
