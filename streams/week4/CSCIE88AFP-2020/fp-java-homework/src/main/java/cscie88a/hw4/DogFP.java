package cscie88a.hw4;

import cscie88a.hw2.ActionResult;

public class DogFP extends AbstractAnimalFP/* implements ITrainableFP*/{

    // dogs are always of type DOG
    public DogFP(String name){
        super(AnimalType.DOG, name);
    }

    @Override
    public ActionResult doTrick(String trick) {
        System.out.println(getName() + " says: Ok! I'll do the trick... ");
        return ActionResult.SUCCESS;
    }
}
