package ru.idr.datamarkingeditor.model.entity.common;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.entity.arbitrage.KeywordEntity;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.ComplainantEntity;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.FinancialManagerEntity;

public class MoneySumEntityTest {
    
    @Test
    public void getValue() {
        Entity entity = new MoneySumEntity("");

        String testExpected = "(?<MoneySum>"+RegExRepository.regexCourtCaseSum+")";
        Assertions.assertTrue(entity.getValue().equals(testExpected));

    }

    @Test 
    public void merge() {
        Entity entity1 = new MoneySumEntity("");
        entity1.connect(new WordEntity("hello"));

        Entity entity2 = new MoneySumEntity("");
        entity2
            .connect(new WordEntity("hello"))
            .connect(new WordEntity("world"))
            .connect(new KeywordEntity("keyword"))
            .connect(new ComplainantEntity(""));

        entity1.merge(entity2);

        List<Entity> testExpected = List.of(
            new KeywordEntity("keyword"),
            new ComplainantEntity(""),
            new WordEntity("world"),
            new WordEntity("hello")
        );

        Iterator<Entity> testIterator = testExpected.iterator();
        Iterator<Entity> entity1Iterator = entity1.getRelated().iterator();
    
        while (entity1Iterator.hasNext() && testIterator.hasNext()) {
            Assertions.assertTrue(entity1Iterator.next().equals(testIterator.next()));
        }
    }

    @Test 
    public void merge_OneIsNotMoneySumEntity_ThrowsException() {
        Entity entity1 = new MoneySumEntity("");
        Entity entity2 = new FinancialManagerEntity("");

        Assertions.assertThrows(
            IllegalArgumentException.class, 
            () -> entity1.merge(entity2));

    }
}
