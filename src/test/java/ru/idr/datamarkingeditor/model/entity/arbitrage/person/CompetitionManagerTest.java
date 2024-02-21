package ru.idr.datamarkingeditor.model.entity.arbitrage.person;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ru.idr.datamarkingeditor.model.entity.Entity;

public class CompetitionManagerTest {
    
    @Test
    public void getValue() {
        Entity entity = new CompetitionManagerEntity("");

        String testExpected = "(?<CompetitionManager>.+?)(?=())";
        Assertions.assertTrue(entity.getValue().equals(testExpected));
    }
}
