package cscie88a.basics2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class CatTest {

	@Test
	void testSayGreeting() {
		Cat cat1 = new Cat("Demon", "green", "black");
		String saySomethingArg = "Hello!";
		String expectedResponse = "I don't care what you asked me to say - I say MEOW only";
		String resultResponse = cat1.saySomething(saySomethingArg);
		Assertions.assertEquals(expectedResponse, resultResponse);
	}

}
