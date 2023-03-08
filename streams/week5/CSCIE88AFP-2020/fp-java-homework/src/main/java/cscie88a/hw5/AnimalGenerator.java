package cscie88a.hw5;

import cscie88a.hw4.AnimalType;

import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class AnimalGenerator {

    // Generate a list of equally distributed animals by type
    public static Stream<StreamAnimal> generateStreamOfAnimalsFromCollection(int numberOfItems){
        List<StreamAnimal> animalList = new ArrayList<StreamAnimal>();
        Random random = new Random();
        int animalID = random.nextInt(3);
        for(int i = 0; i < numberOfItems; i++){
            if(animalID == 0){
                animalList.add(new StreamAnimal(AnimalType.CAT, "Cat#"+UUID.randomUUID(), random.nextBoolean(), random.nextInt(21)));
            }else if(animalID == 1){
                animalList.add(new StreamAnimal(AnimalType.DOG, "Dog#"+UUID.randomUUID(), random.nextBoolean(), random.nextInt(21)));
            }else{
                animalList.add(new StreamAnimal(AnimalType.HEDGEHOG, "Hedgehog#"+UUID.randomUUID(), random.nextBoolean(), random.nextInt(21)));
            }
            animalID = random.nextInt(3);
        }
        return animalList.stream();
    }

    // Create a new animal with equal distribution across type
    public static StreamAnimal getNewAnimal(){
        Random random = new Random();
        int animalID = random.nextInt(3);
        if(animalID == 0){
            return new StreamAnimal(AnimalType.CAT, "Cat#"+UUID.randomUUID(), random.nextBoolean(), random.nextInt(21));
        }else if(animalID == 1){
            return new StreamAnimal(AnimalType.DOG, "Dog#"+UUID.randomUUID(), random.nextBoolean(), random.nextInt(21));
        }else{
            return new StreamAnimal(AnimalType.HEDGEHOG, "Hedgehog#"+UUID.randomUUID(), random.nextBoolean(), random.nextInt(21));
        }
    }

    // Stream of animals generated via internal lambda
    public static Stream<StreamAnimal> generateStreamOfAnimals_lambda(){
        Random r = new Random();
        Stream<StreamAnimal> resultStream = Stream.generate(
                () -> {
                    Random random = new Random();
                    int animalID = random.nextInt(3);
                    if(animalID == 0){
                        return new StreamAnimal(AnimalType.CAT, "Cat#"+UUID.randomUUID(), random.nextBoolean(), random.nextInt(21));
                    }else if(animalID == 1){
                        return new StreamAnimal(AnimalType.DOG, "Dog#"+UUID.randomUUID(), random.nextBoolean(), random.nextInt(21));
                    }else{
                        return new StreamAnimal(AnimalType.HEDGEHOG, "Hedgehog#"+UUID.randomUUID(), random.nextBoolean(), random.nextInt(21));
                    }
                }
        );
        return resultStream;
    }

    // Stream of animals generated via getNewAnimal method
    public static Stream<StreamAnimal> generateStreamOfAnimals_methodRef(){
        Stream<StreamAnimal> resultStream = Stream.generate(
            AnimalGenerator::getNewAnimal
        );
        return resultStream;
    }
}
