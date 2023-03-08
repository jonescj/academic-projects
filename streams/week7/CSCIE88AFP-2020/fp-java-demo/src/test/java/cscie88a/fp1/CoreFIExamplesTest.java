package cscie88a.fp1;

import cscie88a.basics4.ActionResult;
import cscie88a.fp1.AbstractAnimalFP;
import cscie88a.fp1.CatFP;
import cscie88a.fp1.CoreFIExamples;
import cscie88a.fp1.ITrainableFP;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class CoreFIExamplesTest {

    @Test
    public void testCalculations(){
        // can be implemented as any FI - via anonymous class
        Function<String, Integer> calcFnSafe = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                if (s != null)
                    return s.length();
                else
                    return 0;
            }
        };
        CoreFIExamples.calculateLength(calcFnSafe, "lab4");

        Function<String, Integer> calcFn1 = inputString -> inputString.length();
        Function<String, Integer> calcFn_plus10 = inputString -> inputString.length() + 10;
        Function<String, Integer> calcFn_double = inputString -> inputString.length() * 2;

        String inputString = "class";
        CoreFIExamples.calculateLength(calcFn1, inputString);
        CoreFIExamples.calculateLength(calcFn_plus10, inputString);
        CoreFIExamples.calculateLength(calcFn_double, inputString);

        // we could also provide an in-line lambda as implementation:
        CoreFIExamples.calculateLength(
                inputStringArg -> inputStringArg.length() + 100,
                inputString);
    }

    @Test
    public void useFunctionToTrain() {
        // we can create a concrete instance as an implementation of the ITrainableFP interface:
        CatFP sneaky = new CatFP("Sneaky");
        sneaky.setGoodMood(true);

        // implementation of the Function<> interface - as an anonymous class
        // and use external state for trick name
        String currentTrickName = "jump";
        Function<ITrainableFP, ActionResult> myTrainingFunction1 = new Function<ITrainableFP, ActionResult>() {
            @Override
            public ActionResult apply(ITrainableFP trainingSubject) {
                return trainingSubject.doTrick(currentTrickName);
            }
        };

        // implementation of the Function<> interface - as a lambda function
        Function<ITrainableFP, ActionResult> myTrainingFunction2 = trainingSubject -> {
            return trainingSubject.doTrick("some trick");
        };

        // implementation of the Function<> interface - as a yet another lambda function
        // always returns FAILURE
        Function<ITrainableFP, ActionResult> myTrainingFunction3 = trainingSubject -> {
            return ActionResult.FAILURE;
        };

        // now we can see how we can use different training functions in the same
        // service method:
        ActionResult result1 = CoreFIExamples.useFunctionToTrain(myTrainingFunction1, sneaky);
        // Sneaky is in a good mood - training should succeed
        assertEquals(ActionResult.SUCCESS, result1);
        sneaky.setGoodMood(false);
        ActionResult result2 = CoreFIExamples.useFunctionToTrain(myTrainingFunction2, sneaky);
        assertEquals(ActionResult.SUCCESS, result2);
        ActionResult result3 = CoreFIExamples.useFunctionToTrain(myTrainingFunction3, sneaky);
        assertEquals(ActionResult.FAILURE, result3);

    }

    // example of implementing Supplier/Consumer FIs using lambdas
    @Test
    public void testSupplierAndConsumer_lambdaImpl(){
        Random random = new Random();
        Supplier<AbstractAnimalFP> catSupplier = () -> {
            boolean hasCurrentShots = random.nextBoolean();
            CatFP newCat = new CatFP("SuppliedCat");
            newCat.setHasCurrentShots(hasCurrentShots);
            return newCat;
        };

        Consumer<AbstractAnimalFP> animalConsumer = animal -> {
            if (animal.isHasCurrentShots()) {
                System.out.println("We can accept this animal");
            } else {
                System.out.println("We can NOT accept this animal");
            }
        };

        CoreFIExamples.chainSupplierAndConsumer(catSupplier, animalConsumer);
    }

    // example of implementing Supplier/Consumer FIs using anonymous classes
    @Test
    public void testSupplierAndConsumer_anonymousImpl(){
        Supplier<AbstractAnimalFP> catSupplier = new Supplier<AbstractAnimalFP>() {
            @Override
            public AbstractAnimalFP get() {
                CatFP newCat = new CatFP("SuppliedCat_Abstract");
                System.out.println("I'm supplying a new cat: " + newCat);
                return newCat;
            }
        };

        Consumer<AbstractAnimalFP> animalConsumer = new Consumer<AbstractAnimalFP>() {
            @Override
            public void accept(AbstractAnimalFP abstractAnimalFP) {
                System.out.println("I'm accepting an animal: " + abstractAnimalFP);
            }
        };

        CoreFIExamples.chainSupplierAndConsumer(catSupplier, animalConsumer);
    }

}