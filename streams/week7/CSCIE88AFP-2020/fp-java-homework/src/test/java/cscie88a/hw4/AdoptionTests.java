package cscie88a.hw4;

import cscie88a.hw2.ActionResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdoptionTests {

	@Test
	public void testMethodReference_static(){
		AdoptionService service = new AdoptionService();
		IAdoptable lambdaMethodRef = AbstractAnimalFP::checkForAdoptionStatusStatic;
		ActionResult result = service.tryToAdopt( lambdaMethodRef );
		assertEquals(ActionResult.SUCCESS, result);
	}

	@Test
	public void testMethodReference_specific_instance(){
		AdoptionService service = new AdoptionService();
		DogFP lilo = new DogFP("Lilo");
		lilo.setHasCurrentShots(true);
		IAdoptable lambdaMethodRef = lilo::checkForAdoptionStatusInstance;

		ActionResult result = service.tryToAdopt( lambdaMethodRef );
		assertEquals(ActionResult.SUCCESS, result);

		lilo.setHasCurrentShots(false);
		result = service.tryToAdopt( lambdaMethodRef );
		assertEquals(ActionResult.FAILURE, result);
	}

}
