package cscie88a.threads;

/**
 * Sep 14, 2017 
 * @author marinapopova
 *
 */

public class RunnableWorker implements Runnable {

	private String id;
	private long sleepTimeMs = 3000l;

	public RunnableWorker(String id) {
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println("Worker with id=" + id + " is starting to work ....");
		try {
			Thread.sleep(sleepTimeMs);
		} catch (InterruptedException e) {
			System.out.println("Worker with id=" + id + " was woken up from his work - finish up ....");
		}
		System.out.println("Worker with id=" + id + " finished the work!");
	}

}
