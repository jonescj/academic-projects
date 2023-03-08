package cscie88a.hw4;

import cscie88a.hw2.ActionResult;

public class AnimalManagerFP {

    // one-argument method
    public static ActionResult trainToRun(ITrainableFP animalToTrain) {
        String trickName = "run";
        System.out.println("About to ask subject [" + animalToTrain.toString() + "] to run ...");
        return animalToTrain.doTrick(trickName);
    }

    // multi-argument method
    public static ActionResult trainForTricks(ITrainableFP animalToTrain, String trickName) {
        System.out.println("About to ask subject [" + animalToTrain.toString() +
                "] to do the trick: " + trickName);
        return animalToTrain.doTrick(trickName);
    }

    public static ActionResult doAnyTrick1(ITrainable1 animalToTrain) {
        animalToTrain.doAnyTrick();
        return ActionResult.SUCCESS;
    }

    public static ActionResult doAnyTrick2(ITrainable2 animalToTrain) {
        return animalToTrain.doAnyTrick();
    }

    public static ActionResult doManyTricks(ITrainable3 animalToTrain1) {
        return animalToTrain1.doManyTricks("jump", "roll");
    }

}
