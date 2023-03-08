package cscie88a.fp1;

import cscie88a.basics4.ActionResult;
import cscie88a.fp1.AbstractAnimalFP;
import cscie88a.fp1.ITrainableFP;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class CoreFIExamples {

    /**
     * Example of using a Function FI - with simple types
     */
    public static void calculateLength(Function<String, Integer> calculatorFunction, String inputString) {
        Integer resultLength = calculatorFunction.apply(inputString);
        System.out.println("Calculated length for string: " + inputString + " --> " + resultLength);
    }

    /**
     * Example of using a Function FI - with complex types
     */
    public static ActionResult useFunctionToTrain(Function<ITrainableFP, ActionResult> fn, ITrainableFP trainingSubject) {
        ActionResult result = fn.apply(trainingSubject);
        return result;
    }

    /* a more verbose but easier to inspect/debug implementation
     */
    public static void chainSupplierAndConsumer(Supplier<AbstractAnimalFP> animalSupplier,
                                                Consumer<AbstractAnimalFP> animalConsumer) {
        int numberOfRuns = 5;
        for (int i=0; i<numberOfRuns; i++){
            System.out.println("Run #" + i);
            AbstractAnimalFP animal = animalSupplier.get();
            System.out.println("supplied animal: " + animal);
            animalConsumer.accept(animal);
        }
    }

}
