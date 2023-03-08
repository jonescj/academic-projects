package cscie88a.basics3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnimalTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testTakeMedicine() {
		Cat sneaky = new Cat("Sneaky", "blue", "gray");
		Dog bolt = new Dog("Bolt", "brown", "whity");
		
		Assertions.assertTrue(bolt.takeMedicine(true));
		assertFalse(bolt.takeMedicine(false));
		assertFalse(sneaky.takeMedicine(true));
		assertFalse(sneaky.takeMedicine(false));
	}

}
