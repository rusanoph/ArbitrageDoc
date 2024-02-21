package ru.idr.datamarkingeditor.model.entity.arbitrage.person;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ru.idr.datamarkingeditor.model.entity.Entity;

public class DefendantTest {
    
    @Test
    public void getValue() {
        Entity entity = new DefendantEntity("");

        String testExpected = "(?<Defendant>.+?)(?=())";
        Assertions.assertTrue(entity.getValue().equals(testExpected));
    }
}
