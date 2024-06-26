package ru.idr.datamarkingeditor.model.entity.common;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;

import ru.idr.datamarkingeditor.model.InnerRegexMap;
import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.entity.arbitrage.KeywordEntity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;

public class WordEntityTest {

     //#region Process Test 

     @Test
     public void processWord_CorrectBracesReplacing() {
        Entity entity = new WordEntity("word (braces)", CommonToken.Word);

        String textExpected = "(word \\(braces\\))";
        Assertions.assertEquals(textExpected, entity.getValue());
        Assertions.assertEquals(textExpected, entity.getValue());
     }
 
     @Test
     public void processWord_ShortWordWrapping() {
        // Word length = 1
        Entity entityShort1 = new WordEntity("s", CommonToken.Word);

        String testExpected1 = "((^|\\s*)s($|\\s+))";
        InnerRegexMap testInnerRegexMap1 = (new InnerRegexMap()).put(CommonToken.Word, testExpected1);
        Assertions.assertEquals(testExpected1, entityShort1.getValue());
        Assertions.assertTrue(entityShort1.getInnerRegexMap().equals(testInnerRegexMap1)); 

        // Word length = 2
        Entity entityShort2 = new WordEntity("sr", CommonToken.Word);
        
        String testExpected2 = "((^|\\s*)sr($|\\s+))";
        InnerRegexMap testInnerRegexMap2 = (new InnerRegexMap()).put(CommonToken.Word, testExpected2);
        Assertions.assertEquals(testExpected2, entityShort2.getValue());
        Assertions.assertTrue(entityShort2.getInnerRegexMap().equals(testInnerRegexMap2));   

        // Word length = 3
        Entity entityShort3 = new WordEntity("srt", CommonToken.Word);

        String testExpected3 = "((^|\\s*)srt($|\\s+))";
        InnerRegexMap testInnerRegexMap3 = (new InnerRegexMap()).put(CommonToken.Word, testExpected3);
        Assertions.assertEquals(testExpected3, entityShort3.getValue());
        Assertions.assertTrue(entityShort3.getInnerRegexMap().equals(testInnerRegexMap3));

        // Word length > 3
        Entity entityLong = new WordEntity("loooooong", CommonToken.Word);

        String testExpectedLong = "(loooooong)";
        InnerRegexMap testInnerRegexMapLong = (new InnerRegexMap()).put(CommonToken.Word, testExpectedLong);
        Assertions.assertEquals(testExpectedLong, entityLong.getValue());
        Assertions.assertTrue(entityLong.getInnerRegexMap().equals(testInnerRegexMapLong));
     }
 
     //#endregion

    //#region Merge Test
    @Test
    public void mergeWord_TryMergeDifferentWords_ThrowsException() {
        Entity entity1 = new WordEntity("word1", CommonToken.Word);
        Entity entity2 = new WordEntity("word2", CommonToken.Word);

        Assertions.assertThrows(
            IllegalArgumentException.class, 
            () -> entity1.merge(entity2));
    }

    @Test
    public void mergeWord_otherIsNotTypeOfWord_ThrowsException() {
        Entity entity1 = new WordEntity("word1", CommonToken.Word);
        Entity entity2 = new KeywordEntity("word1", ArbitrageToken.Keyword);

        Assertions.assertThrows(
            IllegalArgumentException.class, 
            () -> entity1.merge(entity2));
    }

    @Test
    public void mergeWord_BothNotProcessedBefore() {
        Entity entity = new WordEntity("word1", CommonToken.Word);
        entity.connect(new WordEntity("A"));

        Entity otherEntity = new WordEntity("word1", CommonToken.Word);
        otherEntity
            .connect(new WordEntity("oA"))
            .connect(new WordEntity("oB"));

        entity.merge(otherEntity);

        Set<Entity> testRelated = Set.of(
            new WordEntity("A"),
            new WordEntity("oA"),
            new WordEntity("oB")
        );
        
        for (Entity testEntity : testRelated) {
            Assertions.assertTrue(entity.getRelated().contains(testEntity));
        }
    }
    //#endregion
    
    
}
