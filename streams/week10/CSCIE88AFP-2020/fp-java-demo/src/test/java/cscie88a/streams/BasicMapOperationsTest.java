package cscie88a.streams;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicMapOperationsTest {

    @Test
    void test_mapToCapitalize_anonymous() {
        BasicMapOperations.mapToCapitalize_anonymous();
    }

    @Test
    void test_mapToCapitalize_methodRef() {
        BasicMapOperations.mapToCapitalize_methodRef();
    }

    @Test
    void test_mapToCapitalize_lambda() {
        BasicMapOperations.mapToCapitalize_lambda();
    }
}