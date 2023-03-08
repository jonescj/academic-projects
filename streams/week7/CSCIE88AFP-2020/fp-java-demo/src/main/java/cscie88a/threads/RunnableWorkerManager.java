package cscie88a.threads;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author marinapopova
 *
 */
public class RunnableWorkerManager {

	private ExecutorService workersThreadPoolService = null;
	private int numberOfThreads = 2;
	
	public RunnableWorkerManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void runWorkers(int numberOfWorkers){
		workersThreadPoolService = Executors.newFixedThreadPool(numberOfThreads);
		for (int i=0; i<numberOfWorkers; i++) {
			String workerId = "worker_" + i;
			workersThreadPoolService.submit(new RunnableWorker(workerId));
		}
	   	System.out.println("runWorkers(): submitted all workers, waiting for completion");
		workersThreadPoolService.shutdown();
		try {
			workersThreadPoolService.awaitTermination(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		   	System.out.println("Got InterruptedException while shutting down workers, aborting");
		}
	   	System.out.println("runWorkers(): finished waiting for completion");
	}

	public void runWorkersAndCheckResults(int numberOfWorkers){
		ArrayList<Future> workerResults = new ArrayList<>();
		workersThreadPoolService = Executors.newFixedThreadPool(numberOfThreads);
		for (int i=0; i<numberOfWorkers; i++) {
			String workerId = "worker_" + i;
			Future futureResult = workersThreadPoolService.submit(new RunnableWorker(workerId));
			workerResults.add(futureResult);
		}
		workersThreadPoolService.shutdown();
	   	System.out.println("runWorkers(): submitted all workers, checking the results");
		while (!workersThreadPoolService.isTerminated()) {
		   	System.out.println("checking the results: ");
			// check the status of each worker
			for (Future result: workerResults){
				if (result.isDone()) {
				   	System.out.println("worker's result is completed: " + result.toString());					
				} else {
				   	System.out.println("worker's result is NOT yet completed: " + result.toString());										
				}
			}
			// sleep a bit, let the tasks to continue their work
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// thread sleep is interrupted - get out
			   	System.out.println("runWorkers(): thread sleep is interrupted - get out");
				break;
			}			
		}
	   	System.out.println("runWorkers(): all workers are done!");
	}

	public static void main(String[] args) {
		RunnableWorkerManager manager = new RunnableWorkerManager();
		
		//manager.runWorkers(10);
		manager.runWorkersAndCheckResults(10);
		System.out.println("Main method is finished");

	}

}