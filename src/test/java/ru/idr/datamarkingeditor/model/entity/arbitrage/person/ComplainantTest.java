package ru.idr.datamarkingeditor.model.entity.arbitrage.person;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ru.idr.datamarkingeditor.model.entity.Entity;

public class ComplainantTest {
    
    @Test
    public void getValue() {
        Entity entity = new ComplainantEntity("");

        String testExpected = "(?<Complainant>.+?)(?=())";
        Assertions.assertTrue(entity.getValue().equals(testExpected));
    }
}
