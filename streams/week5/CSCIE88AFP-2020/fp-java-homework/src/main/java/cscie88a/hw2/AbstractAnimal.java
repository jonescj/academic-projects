package cscie88a.hw2;

public abstract class AbstractAnimal {

	protected String name;
	protected String eyeColor;
	protected String bodyColor;

	public AbstractAnimal() {
		super();
	}

	public AbstractAnimal(String name, String eyeColor, String bodyColor) {
		super();
		this.name = name;
		this.eyeColor = eyeColor;
		this.bodyColor = bodyColor;
	}

	public abstract void sayHiToHuman(String humanName);

	public abstract ActionResult playWithMe(AbstractAnimal aFriend);

	public boolean takeMedicine(boolean withTreat) {
		if (withTreat) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEyeColor() {
		return eyeColor;
	}

	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}

	public String getBodyColor() {
		return bodyColor;
	}

	public void setBodyColor(String bodyColor) {
		this.bodyColor = bodyColor;
	}

}