package cscie88a.hw2;

public interface ITrainable {
	
	public ActionResult doTrick(String trickName);
	
	default public ActionResult doTrickForTreat(String trickName, String treatName) {
		System.out.println("I love the " + treatName + 
			" and will happily do the trick [" + trickName + "] !!");
		return ActionResult.SUCCESS;
	}

	default public ActionResult doSuperTrick(String trickName){
		System.out.println("I'm not a Superhero, can't do super trick!");
		return ActionResult.FAILURE;
	}
}
