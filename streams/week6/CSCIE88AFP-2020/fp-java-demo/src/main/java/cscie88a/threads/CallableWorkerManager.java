package cscie88a.threads;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallableWorkerManager {

	private static final Logger logger = LoggerFactory.getLogger(CallableWorkerManager.class);
	private ExecutorService workersThreadPoolService = null;
	private int numberOfThreads = 2;
	private Map<String, String> allResults = new HashMap<>();
	
	public CallableWorkerManager() {
		logger.info("CallableWorkerManager is created");
	}
	
	public void runWorkersAndCheckResults(int numberOfWorkers){
		Map<String,Future<String>> workerResults = new HashMap<>();
		workersThreadPoolService = Executors.newFixedThreadPool(numberOfThreads);
		for (int i=0; i<numberOfWorkers; i++) {
			String workerId = "worker_" + i;
			Future<String> futureResult = workersThreadPoolService.submit(new CallableWorker(workerId));
			workerResults.put(workerId, futureResult);
		}
		workersThreadPoolService.shutdown();
		logger.info("runWorkers(): submitted all workers, checking the results");
		while (!workersThreadPoolService.isTerminated()) {
			logger.info("checking the results: ");
			checkAndUpdateResults(workerResults);
			// sleep a bit, let the tasks to continue their work
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// thread sleep is interrupted - get out
				logger.warn("runWorkers(): thread sleep is interrupted - get out");
				break;
			}			
		}
		// check the last time - in case last workers finished after the last check
		// but before the pool was marked as 'terminated'
		checkAndUpdateResults(workerResults);		
		logger.info("runWorkers(): all workers are done!");
	}

	private void checkAndUpdateResults(Map<String,Future<String>> workerResults) {
		// check the status of each worker
		for (String workerId: workerResults.keySet()){
			Future<String> result = workerResults.get(workerId);
			if (result.isDone()) {
			   	try {
			   		allResults.put(workerId, result.get());
			   		logger.info("worker {} is done and the result is: {}", 
			   				workerId, result.get());
				} catch (InterruptedException | ExecutionException e) {
					logger.error("Exception checking this worker - skip it: {}", result.toString());
				}					
			} else {
				logger.info("worker {} is NOT yet completed ...", workerId);										
			}
		}
	}
	
	public void printFinalResults() {
		logger.info("Final results: ");
		for (String workerId: allResults.keySet()) {
			logger.info("worker: {} --> result: {}", workerId, allResults.get(workerId));
		}
	}

	public static void main(String[] args) {
		CallableWorkerManager manager = new CallableWorkerManager();
		manager.runWorkersAndCheckResults(10);
		manager.printFinalResults();
		logger.info("Main method is finished");

	}

	public Map<String, String> getAllResults() {
		return allResults;
	}

}
