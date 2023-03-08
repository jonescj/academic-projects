package cscie88a.hw5;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;


public class StreamHW5Test {

    @Test
    public void testForHealthyAnimals_test(){
        StreamHW5.testForHealthyAnimals(10);
    }

     @Test
    public void testForHealthyCats_test(){
        StreamHW5.testForHealthyCats(10);
    }

    @Test
    public void getNamesOfHealthyAnimals_test(){
        StreamHW5.getNamesOfHealthyAnimals();
    }

    @Test
    public void getOldestAnimal_test(){
        StreamHW5.getOldestAnimal();
    }

    @Test
    public void getAverageAge_test(){
        System.out.println(StreamHW5.getAverageAge());
    }

    @Test
    public void getAnimalNamesAsList_test(){
        StreamHW5.getAnimalNamesAsList();
    }

    @Test
    public void getAnimalGroupCounts_test(){
        StreamHW5.getAnimalGroupCounts();
    }

    @Test
    public void getAnimalGroupCounts2_test(){
        StreamHW5.getAnimalGroupCounts2();
    }

    @Test
    public void randomizeString_test(){
        StreamHW5.randomizeString("Cats");
    }

    @Test
    public void createInfiniteStream_methodRef_test(){
        StreamHW5.createInfiniteStream_methodRef();
    }
}
