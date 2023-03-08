package cscie88a.hw2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

class AllAnimalTests {

	private Cat sneaky;
	private Dog bolt;
	private Hedgehog spike;
	private Toy squeakyToy, notSqueakyToy;


	@BeforeEach
	void setUp() throws Exception {
		sneaky = new Cat("Sneaky", "blue", "gray");
		bolt = new Dog("Bolt", "brown", "white");
		spike = new Hedgehog("Spike","brown","brown");
		squeakyToy = new Toy(true,false,false);
		notSqueakyToy = new Toy(false,false,false);
	}

	@Test
	void testTakeMedicine() {		
		assertEquals(bolt.takeMedicine(true), ActionResult.SUCCESS);
		assertEquals(bolt.takeMedicine(false), ActionResult.FAILURE);
		assertEquals(sneaky.takeMedicine(true), ActionResult.FAILURE);
		assertEquals(sneaky.takeMedicine(false), ActionResult.FAILURE);
	}

	@Test
	public void testSayHi() {
		String humanName = "Marina";
		sneaky.sayHiToHuman(humanName);
		bolt.sayHiToHuman(humanName);
	}

	@Test
	public void testAbstractClassCreation() {
		// demo error creating AbstractAnimal directly
		// will NOT compile
		//AbstractAnimal unknownAnimal = new AbstractAnimal();
	}
	
	@Test
	public void testDoTrick() {
		ActionResult result = sneaky.doTrick("sit");
		assertEquals(ActionResult.FAILURE, result);

		result = bolt.doTrick("sit");
		assertEquals(ActionResult.SUCCESS, result);
	}

	@Test
	public void testDoTrickForTreat() {
		ActionResult result = sneaky.doTrickForTreat("sit", "yummyTreat");
		assertEquals(ActionResult.FAILURE, result);

		result = bolt.doTrickForTreat("sit", "yummyTreat");
		assertEquals(ActionResult.SUCCESS, result);
	}

	@Test 
	public void testPlayWithMe() {
		// unfriendly cat will not play with anyone
		sneaky.setFriendly(false);
		ActionResult result = sneaky.playWithMe(bolt);
		assertEquals(ActionResult.FAILURE, result);
		
		// a friendly cat will play with others
		sneaky.setFriendly(true);
		result = sneaky.playWithMe(bolt);
		assertEquals(ActionResult.SUCCESS, result);
		
		// dog will play with anybody, always
		result = bolt.playWithMe(sneaky);
		assertEquals(ActionResult.SUCCESS, result);
	}

	@Test
	public void testPlayWithToy() {
		assertEquals(sneaky.playWithToy(squeakyToy), ActionResult.SUCCESS);
		assertEquals(sneaky.playWithToy(notSqueakyToy), ActionResult.FAILURE);
		assertEquals(bolt.playWithToy(squeakyToy), ActionResult.SUCCESS);
		assertEquals(bolt.playWithToy(notSqueakyToy), ActionResult.SUCCESS);
	}

	@Test
	public void testWhoAreYou(){
		assertEquals(sneaky.whoAreYou(),"I am Sneaky!");
		assertEquals(bolt.whoAreYou(), "I am Bolt!");
	}

	@Test
	public void testPlayWithMeForHedgehog(){
		assertEquals(spike.playWithMe(bolt), ActionResult.FAILURE);
		assertEquals(spike.playWithMe(sneaky), ActionResult.FAILURE);
	}
}
