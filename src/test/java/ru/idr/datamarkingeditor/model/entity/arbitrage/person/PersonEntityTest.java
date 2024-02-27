package ru.idr.datamarkingeditor.model.entity.arbitrage.person;

import org.junit.jupiter.api.Assertions;

import java.util.Iterator;

import org.junit.Test;

import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.entity.arbitrage.KeywordEntity;
import ru.idr.datamarkingeditor.model.entity.common.WordEntity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;

public class PersonEntityTest {
    // Complainant
    // Defendant
    // ThirdParty
    // CompetitionManager
    // FinancialManager
    
    
    //#region Process Test
    @Test
    public void processPerson_HasBeenNotProcessed() {
        // Может иметь или нет статическую часть в виде обычного регулярного выражения (обязательно в начале)
        // Может иметь или нет часть собирающую любые данные, оканчивающиеся на заданный regex
        // Должна быть проверка на окончание не только на слова, но и на другие виды токенов

        Entity entity = new ThirdPartyEntity("");
        entity
            .connect(new WordEntity("A1", CommonToken.Word))
            .connect(new WordEntity("A2", CommonToken.Word))
            .connect(new WordEntity("A3", CommonToken.Word))
            .connect(new KeywordEntity("keyword1", ArbitrageToken.Keyword))
            .connect(new KeywordEntity("keyword2", ArbitrageToken.Keyword));

        String testExpected = "((^|\\s*)a1($|\\s+))|((^|\\s*)a2($|\\s+))|((^|\\s*)a3($|\\s+))|(keyword2)|(keyword1)";
        Assertions.assertEquals("(?<ThirdParty>.+?)(?=("+testExpected+"))", entity.getValue());
        Assertions.assertEquals(testExpected, entity.getInnerRegexMap().getAll(CommonToken.Word, ArbitrageToken.Keyword));
    }

    @Test 
    public void processComplainant_HasMultipleValues() {
        Entity entity = new ComplainantEntity("some value", ArbitrageToken.Complainant);
        entity
            .connect(new WordEntity("this"))
            .connect(new WordEntity("value"))
            .connect(new WordEntity("already"))
            .connect(new WordEntity("here"))
            .connect(new KeywordEntity("keyw2"));

        String testExpectedBefore = "(already)|(this)|(here)|(value)|(keyw2)";
        Assertions.assertEquals("(?<Complainant>.+?)(?=("+testExpectedBefore+"))", entity.getValue());
        Assertions.assertEquals(
            testExpectedBefore, 
            entity.getInnerRegexMap().getAll(CommonToken.Word, ArbitrageToken.Keyword)
        );

        entity
            .connect(new WordEntity("A1 (a11)"))
            .connect(new WordEntity("ABC2"))
            .connect(new WordEntity("ABC3"))
            .connect(new KeywordEntity("keyw1"))
            .connect(new KeywordEntity("keyw2"));

        String testExpectedAfter = "(already)|(a1 \\(a11\\))|(this)|(abc3)|(abc2)|(here)|(value)|(keyw2)|(keyw1)";

        Assertions.assertEquals("(?<Complainant>.+?)(?=("+testExpectedAfter+"))", entity.getValue());
        Assertions.assertEquals(
            testExpectedAfter, 
            entity.getInnerRegexMap().getAll(CommonToken.Word, ArbitrageToken.Keyword)
        );
    }

    @Test
    public void processPerson_HasOnlyKeywordAndNotProcessed() {
        Entity entity = new DefendantEntity("");
        entity
            .connect(new KeywordEntity("keyword1"))
            .connect(new KeywordEntity("keyword2"));


        String testExpected = "(keyword2)|(keyword1)";
        Assertions.assertEquals("(?<Defendant>.+?)(?=("+testExpected+"))", entity.getValue());
        Assertions.assertEquals(testExpected, entity.getInnerRegexMap().get(ArbitrageToken.Keyword));
    }
    //#endregion

    //#region Merge Test
    @Test
    public void merge_OneHasOnlyKeywords() {
        Entity entity1 = new DefendantEntity("");
        entity1
            .connect(new WordEntity("hello"))
            .connect(new KeywordEntity("repeated keyword"));
        
        Entity entity2 = new DefendantEntity("");
        entity2
            .connect(new KeywordEntity("repeated keyword"))
            .connect(new KeywordEntity("keyword2"))
            .connect(new KeywordEntity("keyword3"));

        entity1.merge(entity2);

        System.out.println(entity1);

        Entity testEntity = new DefendantEntity("");
        testEntity
            .connect(new WordEntity("hello"))
            .connect(new KeywordEntity("repeated keyword"))
            .connect(new KeywordEntity("repeated keyword"))
            .connect(new KeywordEntity("keyword2"))
            .connect(new KeywordEntity("keyword3"));

        String testExpected = "(hello)|(keyword2)|(repeated keyword)|(keyword3)";

        Assertions.assertEquals("(?<Defendant>.+?)(?=("+testExpected+"))", entity1.getValue());
        Assertions.assertEquals(
            testExpected, 
            entity1.getInnerRegexMap().getAll(CommonToken.Word, ArbitrageToken.Keyword)
        );
        Assertions.assertEquals(entity1, testEntity);
        
        Iterator<Entity> testIterator = testEntity.getRelated().iterator();
        Iterator<Entity> entity1Iterator = entity1.getRelated().iterator();
        while(entity1Iterator.hasNext() && testIterator.hasNext()) {
            Assertions.assertEquals(
                entity1Iterator.next(), 
                testIterator.next()
            );
        }
    }

    @Test
    public void merge_OneHasOnlyWord() {
        Entity entity1 = new ThirdPartyEntity("");
        entity1
        .connect(new KeywordEntity("hello"))
        .connect(new WordEntity("repeated word"));
        
        Entity entity2 = new ThirdPartyEntity("");
        entity2
            .connect(new WordEntity("repeated word"))
            .connect(new WordEntity("word2"))
            .connect(new WordEntity("word3"));

        entity1.merge(entity2);

        Entity testEntity = new ThirdPartyEntity("");
        testEntity
            .connect(new KeywordEntity("hello"))
            .connect(new WordEntity("repeated word"))
            .connect(new WordEntity("repeated word"))
            .connect(new WordEntity("word2"))
            .connect(new WordEntity("word3"));

        String testExpected = "(word2)|(word3)|(repeated word)|(hello)";

        Assertions.assertEquals("(?<ThirdParty>.+?)(?=("+testExpected+"))", entity1.getValue());
        Assertions.assertEquals(
            testExpected, 
            entity1.getInnerRegexMap().getAll(CommonToken.Word, ArbitrageToken.Keyword)
        );
        Assertions.assertEquals(entity1, testEntity);
        
        Iterator<Entity> testIterator = testEntity.getRelated().iterator();
        Iterator<Entity> entity1Iterator = entity1.getRelated().iterator();
        while(entity1Iterator.hasNext() && testIterator.hasNext()) {
            Assertions.assertEquals(
                entity1Iterator.next(), 
                testIterator.next()
            );
        }
    }

    @Test
    public void merge_DifferentPerson_ThrowsException() {
        Entity entity1 = new ThirdPartyEntity("");
        entity1
            .connect(new KeywordEntity("hello"))
            .connect(new WordEntity("repeated word"));

        Entity entity2 = new DefendantEntity("");
        entity2
            .connect(new KeywordEntity("repeated keyword"))
            .connect(new KeywordEntity("keyword2"))
            .connect(new KeywordEntity("keyword3"));

        Assertions.assertThrows(
            IllegalArgumentException.class, 
            () -> entity1.merge(entity2)
        );
    }
    //#endregion

}
