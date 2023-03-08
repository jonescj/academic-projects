package cscie88a.basics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CatAggregatorTest {
	private static CatAggregator catAggregator;
	private static Collection<Cat> testCats;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		generateTestData();
		catAggregator = new CatAggregator();
	}

	static private void generateTestData() {
		testCats = new ArrayList<Cat>();
		testCats.add(new Cat("Demon", "green", "black"));
		testCats.add(new Cat("Sneaky", "blue", "gray"));
		testCats.add(new Cat("Angel", "blue", "white"));
		testCats.add(new Cat("Pirate", "green", "black"));
		testCats.add(new Cat("Scruffy", "golden", "black"));
		testCats.add(new Cat("Princess", "green", "white"));
	}
	
	@Test
	void testCountCatsByColorImperative() {
		long expectedNumber = 2l;
		long resultNumber = catAggregator.countCatsByColorImperative(testCats, "black", "green");
		assertEquals(expectedNumber, resultNumber);
	}

	@Test
	void testCountCatsByColorFunctional() {
		long expectedNumber = 2l;
		long resultNumber = catAggregator.countCatsByColorFunctional(testCats, "black", "green");
		assertEquals(expectedNumber, resultNumber);
	}

}
