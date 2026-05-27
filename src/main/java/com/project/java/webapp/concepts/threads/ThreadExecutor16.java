package com.project.java.webapp.concepts.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ThreadExecutor16 {
	
	/*
	 * Executor framework - 
	 * 	1. Introduced in Java5 as part of the java.util.concurrent package.
	 * 	2. It helps by abstracting away many of the complexities involved in creating and managing threads.
	 * 
	 * Problems with Executor framework - 
	 * 	1. Manual Thread Management
	 * 	2. Resource management
	 * 	3. Scalability
	 * 	4. Thread resue
	 * 	5. Error Handling
	 * 
	 * Interfaces in Executor framework - 
	 * 	1. Executor 
	 *  2. ExecutorService
	 *  3. ScheduledExecutorService
	 * 
	 */
	
	public static long factorial(int n) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		long result = 1;
		for (int i = 1; i <= n; i++) {
			result *= i;
		}
		return result;
	}

	public static String executorHardcodingThread() {
		long startTime = System.currentTimeMillis();
		
		for (int i = 1; i < 10; i++) {
			int finalI = i;

			Thread thread = new Thread(() -> {
				long result = factorial(finalI);
				log.info("Factorial of {} = {}", finalI, result);
			});
			
			thread.start();
		}
		log.info("Total time: {}", (System.currentTimeMillis() - startTime));

		/*
		 * 1. We are creating thread directly inside for loop. But we are not tracking them.
		 *    We can create an array to store them.
		 * 
		 * 2. Problem - 
		 * 		Now here is problem - we are creating 9 threads and calling start().
		 * 		But we are not waiting to complete them. As a result the above logger() gets executed first,
		 * 		which is not proper as we need all threads to complete first.
		 * 	  Solution - use .join()  - But then its manual work.
		 */
		
		return "Executor Service Hardcoding thread is working..!!";
	}
	
	
	public static String executorThreadArray() {
		long startTime = System.currentTimeMillis();
		
		Thread[] threads = new Thread[9];
		
		for (int i = 1; i < 10; i++) {
			int finalI = i;

			threads[i-1] = new Thread(() -> {
				long result = factorial(finalI);
				log.info("Factorial of {} = {}", finalI, result);
			});
			
			threads[i-1].start();
		}
		
		for(Thread thread: threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		
		log.info("Total time: {}", (System.currentTimeMillis() - startTime));

		/*
		 * Problem - 
		 * 	1. We are doing everything manually.. creating, storing, tracking and waiting for threads.
		 * 		Solution - Use Executor framework.
		 * 
		 *  2. We are creating new thread everytime, which is not required.
		 *  	Solution - We can reuse the threads. Using Executors.newFixedThreadPool()
		 */
		
		return "Executor Service Array thread is working..!!";
	}
	
	
	
	/*
	 * 1. Executors - Utility class
	 * 		1. executorService =  Executors.newFixedThreadPool(9);
	 * 
	 * 		2. executorService.submit(Runnable / Callable); - the work which we passes here is called as 'Task'.
	 *     
	 *      3. executorService.shutdown();
	 *      	- Initiates an 'orderly' shutdown in which previously submitted tasks are executed, 
	 *      	- No new tasks will be accepted. 
	 *     
	 *      4. executorService.awaitTermination(100, TimeUnit.SECONDS); - 
	 *      	- Use the after shutdown() is called.
	 *          - similar to join().
	 *          - Blocks until all tasks have completed execution after a shutdown request, 
	 *          - or the timeout occurs, means if threads are not completed and 100s passed,
	 *          - or the current thread is interrupted, whichever happens first.
	 *          
	 *          - It returns boolean  - 
	 *          	TRUE  - if this executor terminated / means all threads are completed.
	 *				FALSE - if the timeout occurs before all threads completed.
	 *          
	 *      5. executorService.isShutdown();
	 *      	- Returns true if this executor has been shut down.
	 * 
	 * 		6. executorService.isTerminated();
	 * 			- Returns true if all tasks have completed following shut down.
	 * 
	 * 2. newFixedThreadPool(3) -
	 * 		No need to pass 9 only. We can make thread reuse here. Pass based on resources available.
	 * 		For eg - TRM-Batch . more thread more fast - but then CB can't handle 
	 * 							 So give some proper number. 
	 */
	public static String threadsUsingExecutor() {
		long startTime = System.currentTimeMillis();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		
		
		for (int i = 1; i < 10; i++) {
			int finalI = i;
			executorService.submit(() -> {
				long result = factorial(finalI);
				log.info("Factorial of {} = {}", finalI, result);
			});
		}
		
		executorService.shutdown();
		try {
			executorService.awaitTermination(100, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			Thread.currentThread().interrupt();
		}
		
		/*
		 * 1. For infinite waiting: 
		 *		- waits for 1s , all threads are not completed but timeout occurs -> !FALSE = TRUE
		 *		- Keeps on waiting until all threads are completed.
			while(!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
				log.info("WAITING");
			}
		*/
		
		log.info("Total time: {}", (System.currentTimeMillis() - startTime));

		/*
		 * We are doing everything manually.. creating, storing, tracking and waiting for threads.
		 * Solution - Use Executor framework
		 */
		
		return "ExecutorService is working..!!";
	}
	
	
	
	/*
	 * 1. ExecutorService(I) extends Executor(FI)
	 * 		- Executor - FI        { method = void execute(Runnable) } - return type is void and name is execute().
	 * 		- ExecutorService(I) - { method = we can pass both Runnable and Callable here.
	 * 										Future<?> submit(Runnable task) } - return type is Future<?> and name is submit().
	 * 										<T> Future<T> submit(Callable<T> task);
	 * 
	 * 2. Runnable vs Callable - 
	 * 		- Runnable = void run();                   --> a) No return type | b) No argument passed. | c) No throws
	 * 		- Callable = V call() throws Exception;    --> a) RETURN type    | b) No argument passed. | c) throws Exception
	 *
	 * 
	 * 3. Future - An interface
	 * 		1. future.get() 
	 * 			- Waits if necessary computation is complete, and then retrieves its result.
	 * 			- Throws - InterruptedException and ExecutionException.
	 * 			- So if we are using get(), no need to invoke awaitTermination() as get() is also blocking in nature.
	 * 			
	 * 		   future.get(long timeout, TimeUnit unit)
	 * 			- waits for that time to complete the computation
	 *          - throws InterruptedException, ExecutionException, TimeoutException
	 * 		
	 * 		2. future.isDone()
	 * 			- Returns true if this task completed.
	 * 			- Completion may be due to normal termination, an exception, or cancellation 
	 * 							-- in all of these cases, this method will return true.
	 * 
	 * 		3. future.cancel()
	 * 			- Attempts to cancel execution of this task. 
	 * 			- This method has no effect if the task is already completed or cancelled, 
	 * 					or could not be cancelled for some other reason.
	 * 
	 * 		   future.isCancelled()
	 * 			- Returns true if this task was cancelled before it completed normally.
	 */
	public static String threadExecutorFuture() {

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<Integer> future = executorService.submit(() -> 25);
		
		future.cancel(true);
		
		try {
			Integer result = future.get();
			log.info("result: {}", result);
		} catch (InterruptedException | ExecutionException e) {
			Thread.currentThread().interrupt();
		}


		return "Executor Service Future is working..!!";
	}
	
	
	
	/*
	 * 1. .submit() -
	 * 		a) Future<?> submit(Runnable task);
	 *      b) <T> Future<T> submit(Callable<T> task);
	 *      c) <T> Future<T> submit(Runnable task, T result);
	 *      	- T result => if the runnable task is successfully completed
	 *      				  then our Future will contain that result.
	 */
	
	public static String executorServiceSubmit() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<String> future = executorService.submit(() -> log.info("Hello"), "success");
		
		try {
			String string = future.get();
			log.info(string);
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}

		return "Executor Service Submit is working..!!";

	}
	
	
	
	/*
	 * 1. invokeAll( Collection )
	 * 		- Executes the given tasks, returning a list of Futures holding
	 * 			their status and results when all complete
	 * 		- This is also blocking in nature like get() and join()
	 * 
	 * 2. invokeAll(Collection, 1, TimeUnit.SECONDS)
	 * 		- Completes the task until timeout happens
	 */
	public static String invokeAllExecutorService() {
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		Callable<Integer> callable1 = () -> {
			log.info("Task1");
			return 1;
		};
		
		Callable<Integer> callable2 = () -> {
			log.info("Task2");
			return 2;
		};

		Callable<Integer> callable3 = () -> {
			log.info("Task3");
			return 3;
		};
		
		List<Callable<Integer>> callables = new ArrayList<>();
		callables.add(callable1);
		callables.add(callable2);
		callables.add(callable3);
		
		try {
			List<Future<Integer>> futures = executorService.invokeAll(callables, 1, TimeUnit.SECONDS);
			for (Future<Integer> future : futures) {
				log.info("res: {}", future.get());
			}
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}

		executorService.shutdown();
		
		return "Executor Service Invoke All is working..!!";
	}
	
	
	
	/*
	 * Difference - 
	 * 		1. executorService.invokeAll(callables) --> Its returning List<Future<Integer>> futures
	 * 		2. executorService.invokeAny(callables) --> Its not returning Future, 
	 * 													instead returning result (Integer) directly.
	 * 								Q - it has 3 results which it returns ?
	 * 								A - which ever gets completed first.
	 */
	public static String invokeAnyExecutorService() {
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		Callable<Integer> callable1 = () -> {
			log.info("Task1");
			return 1;
		};
		
		Callable<Integer> callable2 = () -> {
			log.info("Task2");
			return 2;
		};

		Callable<Integer> callable3 = () -> {
			log.info("Task3");
			return 3;
		};
		
		List<Callable<Integer>> callables = new ArrayList<>();
		callables.add(callable1);
		callables.add(callable2);
		callables.add(callable3);
		
		try {
			Integer result = executorService.invokeAny(callables);
			log.info("result: {}", result);
			
		} catch (InterruptedException | ExecutionException e) {
			Thread.currentThread().interrupt();
		}
		
		executorService.shutdown();
		
		return "Executor Service Invoke Any is working..!!";
	}
	
	
	
	
	/*
	 * === Continue at 45:00
	 */
	public static String threadScheduledExecutorService() {

		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
		
		return "Scheduled Executor Service Invoke Any is working..!!";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
