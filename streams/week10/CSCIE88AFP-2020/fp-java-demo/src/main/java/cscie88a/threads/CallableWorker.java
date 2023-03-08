package cscie88a.threads;

/**
* Sep 16, 2017 marinapopova
*/
import java.util.Random;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallableWorker implements Callable<String> {
	
	private static final Logger logger = LoggerFactory.getLogger(CallableWorker.class);
	private static final Random randomGen = new Random();

	private String id;
	private long sleepTimeMs = 5000l;

	public CallableWorker(String id) {
		this.id = id;
	}

	@Override
	public String call() throws Exception {
		logger.info("CallableWorker with id={} is starting to work ....", id);
		try {
			Thread.sleep(sleepTimeMs);
		} catch (InterruptedException e) {
			logger.warn("InterruptedException in call()....", e);
			e.printStackTrace();
		}
		// generate a random result
		int randomValue = Math.abs(randomGen.nextInt());
		String result = id + "_" + randomValue;
		logger.info("CallableWorker with id={} finished the work! My work result is: {}",
				id, result);
		return result;
	}

}