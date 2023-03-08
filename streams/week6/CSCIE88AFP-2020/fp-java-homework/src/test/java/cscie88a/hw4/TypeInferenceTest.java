package cscie88a.hw4;

import cscie88a.hw2.ActionResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TypeInferenceTest {

	@Test
	public void testTypesInference_withReturnType(){
		ITrainableFP lambdaRef1 = trickName -> {
				return ActionResult.FAILURE;
		};
		ActionResult result = AnimalManagerFP.trainToRun(lambdaRef1);

		/*
		Object lambdaRef2 = trickName -> {
			return ActionResult.FAILURE;
		};

		ActionResult result2 = AnimalManagerFP.trainToRun(lambdaRef2);
		/*  */
	}

	@Test
	public void testTypesInference_noArgs(){
		ITrainable1 lambdaRef1 = () -> {};

		//ITrainable1 lambdaRef4 = (trickName) -> {};

		//Object lambdaRef2 = () -> {};

		Object lambdaRef3 = (ITrainable1) () -> {};
	}

}
