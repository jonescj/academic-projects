package cscie88a.basics4;

public interface ITrainable {
	
	public ActionResult doTrick(String trickName);
	
	default public ActionResult doTrickForTreat(String trickName, String treatName) {
		System.out.println("I love the " + treatName + 
			" and will happily do the trick [" + trickName + "] !!");
		return ActionResult.SUCCESS;
	}

}
