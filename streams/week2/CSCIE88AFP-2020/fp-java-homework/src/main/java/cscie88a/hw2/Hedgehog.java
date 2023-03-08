package cscie88a.hw2;

public class Hedgehog extends AbstractAnimal implements ITrainable {

    public Hedgehog() {}

    public Hedgehog(String name, String eyeColor, String bodyColor) {
        super(name, eyeColor, bodyColor);
    }

    @Override
    public void sayHiToHuman(String humanName) {
        System.out.println(name + " says: Hi, " + humanName + ", have you met Sonic?");
    }

    @Override
    public ActionResult playWithMe(AbstractAnimal aFriend) {
        System.out.println(name + " says: I think I'll just lay in " + aFriend.getName()+"'s hands and be cute.");
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult playWithToy(Toy toy) {
        if(toy.isHasFeathers() && !(toy.isBouncy() || toy.isSqueaky())){
            System.out.println("It doesn't seem too scary... I'll play!");
            toy.doFunStuff();
            return ActionResult.SUCCESS;
        }else{
            System.out.println("I'm too afraid to play...");
            return ActionResult.FAILURE;
        }
    }

    @Override
    public ActionResult doTrick(String trickName) {
        System.out.println(name + " says: I don't do tricks! I won't do " + trickName + "!");
        return ActionResult.FAILURE;
    }

    @Override
    public ActionResult doTrickForTreat(String trickName, String treatName) {
        System.out.println(name + " says: I will eat the " + treatName + ", but I won't do " + trickName + "...");
        return ActionResult.FAILURE;
    }
}
