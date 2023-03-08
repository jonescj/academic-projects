package cscie88a.streams;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateOperationsTest {

    @Test
    public void testGroupByRegularOneArg() {
        StateOperations.groupByRegularOneArg(20);
    }

    @Test
    public void testGroupByRegularThreeArg(){
        StateOperations.groupByRegularThreeArg(20);
    }

    @Test
    public void testGroupByConcurrentOneArg(){
        StateOperations.groupByConcurrentOneArg(20);
    }

    @Test
    public void testGroupByConcurrentThreeArg(){
        StateOperations.groupByConcurrentThreeArg(20);
    }

}