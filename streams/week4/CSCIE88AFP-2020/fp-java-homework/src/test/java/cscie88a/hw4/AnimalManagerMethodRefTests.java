package cscie88a.hw4;

import cscie88a.hw2.ActionResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalManagerMethodRefTests {

	/**
	 * use static method reference as a Lambda function
	 */
	@Test
	public void testDoRun_static_method(){
		ITrainableFP lambdaMethodRef = AbstractAnimalFP::doTrickStatic;
		ActionResult result = AnimalManagerFP.trainToRun(lambdaMethodRef);
		assertEquals(ActionResult.FAILURE, result);
	}

	/**
	 * use instance method reference as a Lambda function
	 */
	@Test
	public void testDoRun_instance_method(){
		CatFP sneaky = new CatFP("Sneaky");
		sneaky.setGoodMood(true);
		ITrainableFP lambdaMethodRef1 = sneaky::doTrick;
		ActionResult result1 = AnimalManagerFP.trainToRun(lambdaMethodRef1);
		assertEquals(ActionResult.SUCCESS, result1);
		sneaky.setGoodMood(false);
		ITrainableFP lambdaMethodRef2 = sneaky::doTrick;
		ActionResult result2 = AnimalManagerFP.trainToRun(lambdaMethodRef2);
		assertEquals(ActionResult.FAILURE, result2);
	}

}
