package cscie88a.fp1;

import cscie88a.basics4.ActionResult;

public abstract class AbstractAnimalFP {

	private AnimalType animalType;
	private String name;
	private boolean hasCurrentShots;

	public AbstractAnimalFP(AnimalType animalType, String name) {
		super();
		this.animalType = animalType;
		this.name = name;
	}

	// class level method - always returns TRUE
	public static boolean checkForAdoptionStatusStatic() {
		return true;
	};

	// Instance level method - can use instance state
	public boolean checkForAdoptionStatusInstance() {
		switch (animalType){
			case TIGER:
				return false;
			default:
				if (hasCurrentShots)
					return true;
				else
					return false;
		}
	};

	// class level method - always returns FAILURE
	public static ActionResult doTrickStatic(String trickName) {
		System.out.println("no tricks");
		return ActionResult.FAILURE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AnimalType getAnimalType() {
		return animalType;
	}

	public boolean isHasCurrentShots() {
		return hasCurrentShots;
	}

	public void setHasCurrentShots(boolean hasCurrentShots) {
		this.hasCurrentShots = hasCurrentShots;
	}

}