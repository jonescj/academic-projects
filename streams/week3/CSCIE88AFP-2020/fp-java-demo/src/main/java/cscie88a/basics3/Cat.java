package cscie88a.basics3;

public class Cat extends Animal {

	public static String whatISay = "I don't care what you asked me to say - I say MEOW only";

	public Cat() {}

	public Cat(String name, String eyeColor, String bodyColor) {
		super(name, eyeColor, bodyColor);
	}
	
	public static String saySomething(String somethingToSay){
		return whatISay;
	}

	// this method is overwritten !
	public boolean takeMedicine(boolean withTreat) {
		System.out.println("you won't trick me - I'm not taking it!");
		return false;		
	}

	public static void main(String[] args) throws Exception {
		Cat demon = new Cat("Demon", "green", "black");
		Cat sneaky = new Cat("Sneaky", "blue", "gray");
		String somethingToSay = "Hello!";
		System.out.println("Demon says: " + demon.saySomething(somethingToSay));
		System.out.println("Sneaky says: " + sneaky.saySomething(somethingToSay));
		
		System.out.println("All cats say: " + Cat.saySomething(somethingToSay));
	}

	@Override
	public String toString() {
		return "Cat [name=" + name + ", eyeColor=" + eyeColor + ", bodyColor=" + bodyColor + "]";
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getEyeColor() {
		return eyeColor;
	}

	@Override
	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}

	@Override
	public String getBodyColor() {
		return bodyColor;
	}

	@Override
	public void setBodyColor(String bodyColor) {
		this.bodyColor = bodyColor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bodyColor == null) ? 0 : bodyColor.hashCode());
		result = prime * result + ((eyeColor == null) ? 0 : eyeColor.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cat other = (Cat) obj;
		if (bodyColor == null) {
			if (other.bodyColor != null)
				return false;
		} else if (!bodyColor.equals(other.bodyColor))
			return false;
		if (eyeColor == null) {
			if (other.eyeColor != null)
				return false;
		} else if (!eyeColor.equals(other.eyeColor))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}	

}
