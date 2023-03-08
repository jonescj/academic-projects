package cscie88a.hw5;

import java.util.function.Predicate;

public class HealthyAnimalFilter implements Predicate<StreamAnimal> {

    /* Functional Interface to determine if animals have had the necessary shots to be healthy */

    @Override
    public boolean test(StreamAnimal streamAnimal) {
        return streamAnimal.isHasCurrentShots();
    }
}
