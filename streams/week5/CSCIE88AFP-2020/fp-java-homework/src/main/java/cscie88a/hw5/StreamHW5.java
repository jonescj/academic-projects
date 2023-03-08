package cscie88a.hw5;

import cscie88a.hw4.AnimalType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamHW5 {

    // Given a stream of animals, identifies which are healthy
     public static void testForHealthyAnimals(int numberOfItems){
        Stream<StreamAnimal> resultStream = AnimalGenerator.generateStreamOfAnimalsFromCollection(numberOfItems);
        HealthyAnimalFilter isHealthy = new HealthyAnimalFilter();
        resultStream.filter(isHealthy).forEach(System.out::println);
    }

    // Given a stream of animals, identifies which are healthy cats
    public static void testForHealthyCats(int numberOfItems){
        Stream<StreamAnimal> resultStream = AnimalGenerator.generateStreamOfAnimalsFromCollection(numberOfItems);
        HealthyAnimalFilter isHealthy = new HealthyAnimalFilter();
        resultStream.filter(isHealthy).filter(
                animal -> animal.getAnimalType() == AnimalType.CAT
        ).forEach(System.out::println);
    }

    // Given a stream of animals, prints out the names of those that are healthy
    public static void getNamesOfHealthyAnimals() {
        HealthyAnimalFilter isHealthy = new HealthyAnimalFilter();
        Stream<StreamAnimal> resultStream = AnimalGenerator.generateStreamOfAnimalsFromCollection(20);
        resultStream.filter(isHealthy).map(StreamAnimal::getName).forEach(System.out::println);
    }

    // Given a stream of animals, identifies the (first) oldest
    public static StreamAnimal getOldestAnimal(){
        Stream<StreamAnimal> resultStream = AnimalGenerator.generateStreamOfAnimalsFromCollection(20);
        return resultStream.reduce(
                null,
                (oldestSoFar,nextAnimal)
                        -> (oldestSoFar != null) && (oldestSoFar.getAge() > nextAnimal.getAge()) ? oldestSoFar : nextAnimal
        );
    }

    // Given a stream of animals, identifies the average age amongst them
    public static int getAverageAge(){
        int numberOfItems = 20;
        Stream<StreamAnimal> resultStream = AnimalGenerator.generateStreamOfAnimalsFromCollection(numberOfItems);
        return resultStream.reduce(
                0,
                (runningTotal,nextAnimal) -> runningTotal + nextAnimal.getAge(),
                (sum1,sum2) -> sum1 + sum2
        ) / numberOfItems;
    }

    // Given a stream of animals, create a list of them
    public static void getAnimalNamesAsList(){
        Stream<StreamAnimal> resultStream = AnimalGenerator.generateStreamOfAnimalsFromCollection(20);
        List<StreamAnimal> animalList = resultStream.collect(
                ArrayList::new,
                (a,b) -> a.add(b),
                (a,b) -> a.addAll(b)
        );
        System.out.println(animalList);
    }

    // Given a stream of animals, count how many animals are of each type
    public static void getAnimalGroupCounts(){
        Stream<StreamAnimal> resultStream = AnimalGenerator.generateStreamOfAnimalsFromCollection(100);
        Map<AnimalType, Long> animalCounts = resultStream.parallel().collect(
                Collectors.groupingBy(
                        StreamAnimal::getAnimalType,
                        Collectors.counting()
                )
        );
        System.out.println(animalCounts);
    }

    // Given a stream of animals, count how many animals are of each type (using custom thread pool)
    public static void getAnimalGroupCounts2(){
        ForkJoinPool customThreadPool = new ForkJoinPool(3);
        Stream<StreamAnimal> resultStream = AnimalGenerator.generateStreamOfAnimalsFromCollection(100);
        customThreadPool.submit(() -> {
                Map<AnimalType, Long> animalCounts = resultStream.parallel().collect(
                Collectors.groupingBy(
                        StreamAnimal::getAnimalType,
                        Collectors.counting()
                ));
                System.out.println(animalCounts);
        }).join();
    }

    // Replace random character of given string with another random character
    public static String randomizeString(String inputString){
        Random random = new Random();
        String acceptableChars = "1234567890!@#$%^&*()_-+=:;'.?/\\[]{}";
        char x = inputString.charAt(random.nextInt(inputString.length()));
        char y = acceptableChars.charAt(random.nextInt(acceptableChars.length()));
        return inputString.replace(x,y);
    }

    // Using an infinite stream, display 10 randomizeString calls applied to the string "CSCIE88A Rocks!"
    public static void createInfiniteStream_methodRef(){
        Stream<String> resultStream = Stream.iterate(
                "CSCIE88A Rocks!",
                StreamHW5::randomizeString
        ).limit(10);
        resultStream.forEach(System.out::println);
    }
}
