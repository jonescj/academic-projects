package cscie88a.hw2;

public class AnimalManager {

	public AnimalManager() {
	}

	public static ActionResult trainForTricks(ITrainable animalToTrain, String trickName) {
		return animalToTrain.doTrick(trickName);
	}
	
	public static ActionResult setupPlaydate(AbstractAnimal hostAnimal, AbstractAnimal friend) {		
		return hostAnimal.playWithMe(friend);
	}

}
