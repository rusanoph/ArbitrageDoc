package ru.idr.datamarkingeditor.model.entity.arbitrage.person;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ru.idr.datamarkingeditor.model.entity.Entity;

public class ThirdPartyTest {
    
    @Test
    public void getValue() {
        Entity entity = new ThirdPartyEntity("");

        String testExpected = "(?<ThirdParty>.+?)(?=())";
        Assertions.assertTrue(entity.getValue().equals(testExpected));
    }
}
