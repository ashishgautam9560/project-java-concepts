package com.project.java.webapp.concepts.threads;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
public class Paper {

	public synchronized void writeWithPaperAndPen(Pen pen) {
		log.info(Thread.currentThread().getName() + " is using paper " + this + " and trying to use pen " + pen);
		pen.finishWriting();
	}

	public synchronized void finishWriting() {
		log.info(Thread.currentThread().getName() + " finished using pen " + this);
	}

}
