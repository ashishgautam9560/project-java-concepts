package com.project.java.webapp.concepts.threads;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
public class Pen {

	public synchronized void writeWithPenAndPaper(Paper paper) {
		log.info(Thread.currentThread().getName() + " is using pen " + this + " and trying to use paper " + paper);
		paper.finishWriting();
	}

	public synchronized void finishWriting() {
		log.info(Thread.currentThread().getName() + " finished using pen " + this);
	}
}
