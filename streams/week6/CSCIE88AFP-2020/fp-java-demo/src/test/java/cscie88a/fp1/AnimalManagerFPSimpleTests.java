package cscie88a.fp1;

import cscie88a.basics4.ActionResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalManagerFPSimpleTests {

	/**
	 * the regular implementation way
	 */
	@Test
	public void testDoRun_concrete(){
		CatFP sneaky = new CatFP("Sneaky");
		ActionResult result = AnimalManagerFP.trainToRun(sneaky);
		assertEquals(ActionResult.FAILURE, result);
	}

	/**
	 * the anonymous class way
	 */
	@Test
	public void testDoRun_anonymous(){
		ActionResult result = AnimalManagerFP.trainToRun(
				new ITrainableFP() {
					@Override
					public ActionResult doTrick(String trickName) {
						System.out.println("I hate running!");
						return ActionResult.FAILURE;
					}
				}
		);
		assertEquals(ActionResult.FAILURE, result);
	}

	/**
	 * the first Lambda function - as an implementation of the ITrainable interface;
	 * as close to the class syntax as possible
	 */
	@Test
	public void testDoRun_lambda1(){
		ActionResult result = AnimalManagerFP.trainToRun(
				(String trickName) -> {
						System.out.println("I hate running!");
						return ActionResult.FAILURE;
				}
		);
		assertEquals(ActionResult.FAILURE, result);
	}

	/**
	 * the first Lambda function - as an implementation of the ITrainable interface;
	 * simplifying syntax: omitting brackets and type for a single method argument
	 */
	@Test
	public void testDoRun_lambda2(){
		ActionResult result = AnimalManagerFP.trainToRun(
				trickName -> {
					System.out.println("I hate running!");
					return ActionResult.FAILURE;
				}
		);
		assertEquals(ActionResult.FAILURE, result);
	}

	/**
	 * the first Lambda function - as an implementation of the ITrainable interface;
	 * simplifying syntax: one line body
	 */
	@Test
	public void testDoRun_lambda3(){
		ActionResult result = AnimalManagerFP.trainToRun(
				trickName -> ActionResult.FAILURE
		);
		assertEquals(ActionResult.FAILURE, result);
	}

	/**
	 * Lambda function implementing ITrainable1 interface -
	 * with no arguments and no return value;
	 * no action in the body
	 */
	@Test
	public void testDoAnyTrick1_doNothing_really(){
		ActionResult result = AnimalManagerFP.doAnyTrick1(
				() -> { }
		);
		assertEquals(ActionResult.SUCCESS, result);
	}

	/**
	 * Lambda function implementing ITrainable1 interface -
	 * with no arguments and no return value;
	 * some work is done in the body - but nothing is returned
	 */
	@Test
	public void testDoAnyTrick1_doNothing(){
		ActionResult result = AnimalManagerFP.doAnyTrick1(
				() -> {
					String myTest = "marina";
					int length = myTest.length();
					System.out.println("length = " + length);
				}
		);
		assertEquals(ActionResult.SUCCESS, result);
	}

	/**
	 * Lambda function implementing ITrainable2 interface -
	 * with no arguments but with a return value
	 */
	@Test
	public void testDoAnyTrick2(){
		ActionResult result = AnimalManagerFP.doAnyTrick2(
				() -> ActionResult.SUCCESS
		);
		assertEquals(ActionResult.SUCCESS, result);
	}

	/**
	 * Lambda function implementing ITrainable3 interface -
	 * with multiple arguments
	 */
	@Test
	public void testDoAnyTrick3(){
		ActionResult result = AnimalManagerFP.doManyTricks(
				(trick1, trick2) -> ActionResult.SUCCESS
		);
		assertEquals(ActionResult.SUCCESS, result);
	}

	/**
	 * Lambda function implementing ITrainable1 interface - with no arguments and no return value;
	 * some action in the body - could be stateful == interfering !
	 */
	@Test
	public void testDoAnyTrick1_doSomethingBad(){
		ActionResult result = AnimalManagerFP.doAnyTrick1(
				() -> {
					System.out.println("I'm going to write something into a DB or file system or S3 ...");
					// do something bad
				}
		);
		assertEquals(ActionResult.SUCCESS, result);
	}

}
