package cscie88a.fp1;

import cscie88a.basics4.ActionResult;

public class CatFP extends AbstractAnimalFP implements ITrainableFP{

	private boolean goodMood = false;

	// cats are always of type CAT
	public CatFP(String name) {
		super(AnimalType.CAT, name);
	}

	@Override
	public ActionResult doTrick(String trickName) {
		if (goodMood) {
			System.out.println(getName() + " says: fine, I'll do the trick... ");
			return ActionResult.SUCCESS;
		} else {
			System.out.println(getName() + " says: not in a mood to do tricks");
			return ActionResult.FAILURE;
		}
	}

	@Override
	public ActionResult doTrickForTreat(String trickName, String treatName) {
		System.out.println(getName() + " says: cats do NOT do tricks ");
		return ActionResult.FAILURE;		
	}

	public boolean isGoodMood() {
		return goodMood;
	}

	public void setGoodMood(boolean goodMood) {
		this.goodMood = goodMood;
	}
	
}
