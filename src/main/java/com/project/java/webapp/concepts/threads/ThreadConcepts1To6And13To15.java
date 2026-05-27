package com.project.java.webapp.concepts.threads;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadConcepts1To6And13To15 {
	
	/*
	 * 1. A process is an instance of a program that is being executed. Process has its own resources like memory, threads, etc.
	 * 
	 * 2. Thread - is the smallest unit of execution within a process. A process can have multiple threads, which share the same resources but can run independently.
	 * 			 - It means thread is a lightweight process.
	 * 
	 * 3. Starvation - one job has to wait very long. 
	 * 	  Non-Interactive - blocking the CPU if any running job needs I/P
	 *    Deadlock - Infinite waiting.
	 *    
	 * 4. Batch 		   - Starvation ✔️ | Non Interactive ✔️
	 * 	  Multiprogramming - Starvation ✔️ | Non Interactive ❌ ? Here we have single core CPU only.
	 * 	  Multitasking     - Starvation ❌ | Non Interactive ❌ ? It uses process preemption and performs context switching.
	 *    Multiprocessing  - Starvation ❌ | Non Interactive ❌ ? process preemption, also have many CPU cores like DualCore, OctaCore, etc.
	 *    Multithreading   - ability to execute multiple threads within a single process concurrently.
	 *    Time Slicing     - means dividing CPU time into small intervals call time slices. The OS scheduler allocates these time time slices to different process and threads.
	 *    
	 * 5. Concurrency - a same resource can be access by multiple threads. 
	 *    Parallelism - if multiple threads are able to work simultaneously. may or may not be on same resource.
	 *    
	 * 6. Java multithreading is managed by JVM and OS.
	 * 	  Java supports multithreading through its java.lang.Thread class and java.lang.Runnable interface.
	 * 	  Extend Thread - What if we wants to extend any other class also and java don't supports multiple inheritance.
	 * 	  Preferred in Runnable - above solution, lambda, can define as anonymous class.
	 *    When java program starts, one thread begins running immediately - main thread.
	 *    
	 * 7. “A new Java process is created” means - “A new JVM instance (Java Virtual Machine) is started.”
	 * 	  We can pass memory also using JVM memory arguments - java -Xms256m -Xmx2g MyApp. - Initial Heap Size and Maximum Heap Size.
	 *    Each JVM instance can have everything its separate. Refer JVM architecture. 
	 *    For Eg - 1 instance = 1 heap memory which can grow further.
	 *    		              = 1 code segment: contains machine code for that process. Its read only, threads can only read.
	 *    		              = 1 data segment: global and static variables. common for all threads.
	 *                        = many threads:
	 *                        		= Each thread can have - Stack memory. Registers, Program Counter - both are used for context switching.
	 *                        
	 * 8. What if we directly calls run() - It will not run in new thread then and run in current thread only like synchronous flow.
	 *    
	 * 9. Thread Life Cycle - 
	 * 	  a) New           - Thread created but not yet started.
	 * 	  b) Runnable      - After the start() method is called. ready to run but waiting for CPU.
	 *    c) Running       - executing state.
	 *    d) Waiting  	   - waiting for a resource / waiting for another thread to finish / invoking wait().
	 *    			  	   - goes back to Runnable when invoking - notify(), notifyAll().
	 *                     - RELEASES all monitor locks.
	 *    e) Timed Waiting - waiting for specific period of time. sleep(), join().
	 *    				   - does NOT RELEASES any monitor lock.
	 *    f) Blocked       - when a thread is waiting to acquire a lock (monitor).
	 *    g) Terminated    - when thread has completed its execution. run() - completed.
	 *    
	 */
	
	
	
	
	/*
	 * ==== STATIC METHODS ====
	 * 1. MIN_PRIORITY = 1 | NORM_PRIORITY = 5 (Default priority assigned to a thread) |MAX_PRIORITY = 10
	 * 2. activeCount() 		   - It gives an estimate of active threads in the current thread's thread group and its subgroups.Eg- 3 (main, t1, t2)
	 * 3. currentThread() 		   - Returns a reference to the currently executing thread object.
	 * 4. enumerate(new Thread[5]) - is used to copy active threads from the current thread group into an array.
	 * 5. interrupted() 		   - true if the current thread has been interrupted; false otherwise.
	 * 6. yield() 				   - yield() is a hint from the currently running thread to the thread scheduler: 
	 * 							   - “I’m willing to pause now if another thread of same priority wants CPU.” No guarantee - Scheduler may ignore it completely.
	 * 
	 *    Debug Methods - 
	 * 7. dumpStack() 			   - Prints a stack trace of the current thread to the standard error stream.This method is used only for debugging.
	 * 8. getAllStackTraces()      - returns stack traces Map of all live threads in the JVM. key → Thread object | value → stack trace of that thread.   
	 * 
	 * 9. sleep(long millis) 	        - to pause the currently executing thread for a specified time.
	 *    sleep(long millis, int nanos) - this version allows nanosecond precision.
	 *    
	 * 10. wait() 						- “I’ll wait until someone wakes me.” Current thread waits indefinitely until: another thread calls notify() or notifyAll()
	 *     wait(long millis) 			- Waits for specified milliseconds. Thread wakes up: after 3 sec or earlier if notified
	 *     wait(long millis, int nanos) - this version allows nanosecond precision.
	 *     
	 * 11. try-catch - Handles exception inside thread code itself.
	 * 	   UncaughtExceptionHandler - Acts as a global fallback handler when exception was NOT caught.
	 * 	   							- Not much uses.
	 *     getDefaultUncaughtExceptionHandler()
	 *     setDefaultUncaughtExceptionHandler()
	 *     
	 * 13. holdsLock() - 
	 * 14. onSpinWait() -
	 */
	
	/* 
	 * ==== Non Static methods ==== 
	 * 1. start() - submitted for execution.
	 *    run()   - contains logic which that thread has to run.
	 *  
	 * 2. Deprecated methods - 
	 *    stop()    - Forcefully terminates a thread immediately.
	 *    suspend() - Pauses/suspends a thread.
	 *    resume()  - resumes suspended thread.
	 *    Above 3 are handled by JVM now.. if we manually handle them then there is huge chance of Deadlock/Starvation. That's why deprecated.
	 *    
	 *    countStackFrames() - return the number of stack frames/calls in a thread.
	 *    checkAccess()      - used to check whether the current thread has permission to modify another thread.
	 * 
	 * 3. t1.join() 				   - Now 'main' thread (from which this thread is started) has to wait 't1' to finish.
	 * 	  join(long millis)			   - t1.join(3000); - Current thread waits: until t1 finishes OR 3 seconds pass.
	 *    join(long millis, int nanos) - More precise timeout.
	 *    
	 * 4. Setters - 
	 * 	  setDaemon(bool);
	 *	  setName("ApnaThread");
	 *	  setPriority(Thread.MAX_PRIORITY);
	 *	  setUncaughtExceptionHandler((t,e) -> {}); - needs FI(UncaughtExceptionHandler) - void uncaughtException(Thread t, Throwable e);
	 *    setContextClassLoader(ClassLoader) - ClassLoader loads .class files into JVM. Same that theoretical concept.
	 *    
	 * 5. Getters - 
	 * 	  getId() - Returns the identifier of this Thread. The thread ID is a positive long number generated when this thread was created.
	 * 	  getName() - Thread-0
	 *    getPriority() - 5, By default
	 *    getStackTrace() - [Ljava.lang.StackTraceElement;@1ab3a8c8
	 *    getState() - NEW
	 *    getThreadGroup() - java.lang.ThreadGroup[name=main,maxpri=10]
	 *                     - Returns the thread group to which this thread belongs.This method returns null if this thread has died(been stopped).
	 *  
	 * 6. Boolean - 
	 * 	  isAlive()  - Tests if this thread is alive. A thread is alive if it hasbeen started and has not yet died.
	 * 	  isDaemon() - Tests if this thread is a daemon thread.
	 *    isInterrupted() - true if this thread has been interrupted; false otherwise.
	 * 
	 * 7. t1.interrupt() - Interrupts this thread. - void. “Please stop if you can.” and not “Stop immediately.”
	 *                   - It does not forcibly stop a thread. Instead sets interrupt flag of t1.
	 *                   - Case 1: Thread is sleeping/waiting/joining - then interrupt causes: InterruptedException
	 *                   - Case 2: Thread is running normally - then interrupt() only sets interrupt status. Thread keeps running unless it checks flag.
	 *  
	 */
	
	/*
	 * 
	 * 1. Basics - CPU, Core, Program, Process, Thread
	 * 2. How JVM handles Multithreading.
	 * 3. How to create Thread
	 * 4. Thread Lifecycle
	 * 5. Thread vs Runnable
	 * 6. Essential methods
	 * 
	 * 
	 * 13. What is thread safety ?
	 *     means when multiple thread tries to access that resource - there will no 
	 *    	 - race condition
	 *       - no unexpected result
	 *       - can allow concurrent access - Eg ConcurrentHashMap
	 *       
	 * 14. Java Thread - Runnable Lambda
	 * 
	 * 15. Thread Pool
	 *      
	 */
	
	public static String createThread() {

		// 1. Extend Thread class
		Thread1 t1 = new Thread1();
		t1.setName("Thread-1");

		// 2. Implement Runnable interface
		Thread t2 = new Thread(new Thread2());
		t2.setName("Thread-2");

		// 3. Direct pass Runnable and name in constructor.
		Runnable runnable = () -> {
			for (int i = 0; i < 10; i++) {
				log.info("" + i);
			}
		};
		Thread t3 = new Thread(runnable, "Thread-3");
		t3.start();

		return "Threads are created.";
	}
}

@Slf4j
class Thread1 extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			log.info("" + i);
		}
	}
}

@Slf4j
class Thread2 implements Runnable {
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			log.info("" + i);
		}
	}
}
