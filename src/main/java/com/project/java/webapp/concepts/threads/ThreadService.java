package com.project.java.webapp.concepts.threads;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ThreadService {
	
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
	 * 8. 
	 */
	
	
	
	/*
	 * 1. It should be 30000 once all 3 threads complete. But we are seeing anamolies in it.
	 * 2. Solution - add 'synchronized' in CS (critical-section) - Counter.increment()
	 * 			   - add 'synchronized' block directly to that lines of code - synchronized (this) {count += 1;}
	 * 3. Synchronized / Intrinsic / Monitor - All are same. and all are Unfair locks.
	 */			   
	public static String counterThreadsWithSynchronized() {
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
			log.error("Exception occurred: {}", e.getMessage());
		}
		return "Counter value after processing: " + counter.getCount();
	}
	
	
	
	
	/*
	 * Locks - Restricting a CS so that at a time only 1 thread can access it. Conceptually similar like synchronized.
	 * Types - Intrinsic and Explicit.
	 * 1. Intrinsic - They are built into every OBJECT(it puts lock on object) in Java. When we use a synchronized keyword, we are using these automatic locks.
	 *    Also called as Monitor Locks - It blocks the multiple threads of same object. But if multiple threads of different objects are coming it provides access.
	 *    For Eg - Below 'BankAccount' has t1 and t2.. means they both belongs to same object of BankAccount. Here monitor lock will work.
	 *    		   But if t1 - BankAccount1 and t2 - BankAccount2.. there will no locking here.
	 * 
	 * 2. Explicit  - more advanced locks, we can control explicitly control when to lock and unlock using Lock.class
	 * 
	 * 3. Barging problem - It occurs when a waiting thread is notified that a shared resource is available, 
	 * 					    but another newly arriving (or "barging") thread sneaks in and claims the resource first.
	 * 							1. T1 acquires lock
	 *							2. T2 tries → goes to BLOCKED
	 *							3. T1 releases lock
	 *							4. Before T2 gets scheduled again, T3 arrives
	 *							5. T3 acquires the lock
	 *							6. T2 remains blocked.
	 *							7. Same cycle can repeat for T2 for other arriving threads leading to indefinite waiting.
	 *					   - It can cause - Starvation, 
	 *									  - Unfairness - Multithreading environments usually prioritize (FIFO) ordering. Barging breaks this queue.
	 *									  - Timeouts - threads might time out or throw errors because their expected window of execution was stolen.
     *
	 * 
	 * 4. To avoid above issue - Use Explicit locks. Refer 'BankAccount' class.
	 * 
	 */
	public static String locksBankAccount() {
		BankAccount bankAccount = new BankAccount();

		Runnable task = () -> bankAccount.withdraw(50);

		Thread t1 = new Thread(task, "Thread-1");
		Thread t2 = new Thread(task, "Thread-2");

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
		
		return "Locks are working..!!";
	}
	
	
	
	
	/*
	 * Refer 'ReentrantLockExample' to understand 'ReentrantLock' working.
	 */
	public static String reentrantLocks() {

		ReentrantLockExample reentrantLockExample = new ReentrantLockExample();
		Runnable runnable = () -> reentrantLockExample.outerMethod();

		Thread t1 = new Thread(runnable, "t1");
		t1.start();

		try {
			t1.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		return "ReentrantLock is working..!!";
	}
	
	
	
	
	/*
	 * Refer 'MonitorLockExample' to understand 'Unfairness' problem.
	 */
	public static String monitorLocks() {
		
		MonitorLockExample obj = new MonitorLockExample();
		MonitorLockExample obj1 = new MonitorLockExample();

		Thread t1 = new Thread(obj::task1);
		Thread t2 = new Thread(obj::task2);
		Thread t3 = new Thread(obj::task3);
		Thread t4 = new Thread(obj1::task2);

		t1.start();
		t2.start();
		t3.start();
		t4.start();

		
		return "Monitor Lock using synchronized is working";
	}
	

}

