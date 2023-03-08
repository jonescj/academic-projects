package cscie88a.fp1;

import cscie88a.basics4.ActionResult;

@FunctionalInterface
public interface ITrainableFP {

	public ActionResult doTrick(String trickName);

	default public ActionResult doTrickForTreat(String trickName, String treatName) {
		System.out.println("I love the " + treatName + 
			" and will happily do the trick [" + trickName + "] !!");
		return ActionResult.SUCCESS;
	}

}
