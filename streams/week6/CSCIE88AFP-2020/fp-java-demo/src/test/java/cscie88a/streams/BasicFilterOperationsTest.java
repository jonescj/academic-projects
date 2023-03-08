package cscie88a.streams;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicFilterOperationsTest {

    @Test
    void test_filterByNumber_anonymous() {
        BasicFilterOperations.filterByNumber_anonymous();
    }

    @Test
    void test_filterByNumber_lambda() {
        BasicFilterOperations.filterByNumber_lambda();
    }
}