package cscie88a.hw5;

import cscie88a.hw4.AnimalType;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalGeneratorTest {

    @Test
    public void generateStreamOfAnimalsFromCollection_test(){
        Stream<StreamAnimal> resultsStream = AnimalGenerator.generateStreamOfAnimalsFromCollection(10);
        resultsStream.forEach(System.out::println);
    }

    @Test
    public void getNewAnimal_test(){
        StreamAnimal result = AnimalGenerator.getNewAnimal();
        assertEquals(
                result.getAnimalType() == AnimalType.CAT ||
                        result.getAnimalType() == AnimalType.DOG ||
                        result.getAnimalType() == AnimalType.HEDGEHOG,
                true);
        assertEquals(
                result.getName().contains("Cat#") ||
                        result.getName().contains("Dog#") ||
                        result.getName().contains("Hedgehog#"),
                true);
        assertEquals(
                result.getAge() >= 0 && result.getAge() <= 20, true);
    }

    @Test
    public void generateStreamOfAnimals_lambda_test(){
        Stream<StreamAnimal> resultsStream = AnimalGenerator.generateStreamOfAnimals_lambda();
        resultsStream.limit(10).forEach(System.out::println);
    }

    @Test
    public void generateStreamOfAnimals_methodRef_test(){
        Stream<StreamAnimal> resultsStream = AnimalGenerator.generateStreamOfAnimals_methodRef();
        resultsStream.limit(10).forEach(System.out::println);
    }
}
