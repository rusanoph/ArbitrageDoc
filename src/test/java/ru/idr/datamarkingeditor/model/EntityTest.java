package ru.idr.datamarkingeditor.model;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.idr.arbitragestatistics.model.arbitrage.ArbitrageToken;

public class EntityTest {
    

    //#region Property Checking Test

    //#region Has Named Regex Value Test
    @Test
    void hasNamedRegexValue_ForKeyword() {

        Entity token = new Entity("(?<Keyword>(kw1)|(kw2)|(kw3))", "Keyword");
        boolean b = token.hasNamedRegexValue(ArbitrageToken.Keyword);

        Assertions.assertTrue(b);
    }

    @Test
    void hasNamedRegexValue_ForPersonsWithForwardLookup() {

        Entity token = new Entity("(?<Complainant>.+?)(?=())", "Complainant");
        boolean b = token.hasNamedRegexValue(ArbitrageToken.Complainant);

        Assertions.assertTrue(b);
    }
    //#endregion

    //#endregion

    
    //#region Entity Merge

    //#region mergePerson Test
    //...
    //#endregion

    //#region mergeKeyword Test
    @Test
    void mergeKeyword_BothNotProcessedBefore() {
        Entity entityKeyword1 = new Entity("keyword1", "Keyword");
        Entity entityKeyword2 = new Entity("keyword2", "Keyword");

        entityKeyword1.mergeKeyword(entityKeyword2);

        Entity testEntity = new Entity("(?<Keyword>(keyword1)|(keyword2))", "Keyword");
        Assertions.assertEquals(testEntity, entityKeyword1);
        Assertions.assertEquals("(keyword2)|(keyword1)", entityKeyword1.getInnerRegex());
    }

    @Test
    void mergeKeyword_OneAlreadyProcessedBefore() {
        Entity entityKeyword1 = new Entity("(?<Keyword>(keyword1))", "Keyword");
        entityKeyword1.setProcessed();

        Entity entityKeyword2 = new Entity("keyword2", "Keyword");

        entityKeyword1.mergeKeyword(entityKeyword2);

        Entity testEntity = new Entity("(?<Keyword>(keyword1)|(keyword2))", "Keyword");
        Assertions.assertEquals(testEntity, entityKeyword1);
        Assertions.assertEquals("(keyword2)|(keyword1)", entityKeyword1.getInnerRegex());
    }
    
    @Test
    void mergeKeyword_BothAlreadyProcessedBefore() {
        Entity entityKeyword1 = new Entity("(?<Keyword>(keyword1))", "Keyword");
        entityKeyword1.setProcessed();

        Entity entityKeyword2 = new Entity("(?<Keyword>(keyword2))", "Keyword");
        entityKeyword2.setProcessed();

        entityKeyword1.mergeKeyword(entityKeyword2);

        Entity testEntity = new Entity("(?<Keyword>(keyword1)|(keyword2))", "Keyword");
        Assertions.assertEquals(testEntity, entityKeyword1);
        Assertions.assertEquals("(keyword2)|(keyword1)", entityKeyword1.getInnerRegex());
    }
    //#endregion

    //#region mergeWord Test
    @Test
    void mergeWord_TryMergeDifferentWords_ThrowsException() {
        Entity entity1 = new Entity("word1", "Word");
        Entity entity2 = new Entity("word2", "Word");

        Assertions.assertThrows(
            IllegalArgumentException.class, 
            () -> entity1.mergeWord(entity2));
    }

    @Test
    void mergeWord_BothNotProcessedBefore() {
        Entity entity = new Entity("word1", "Word");
        Entity otherEntity = new Entity("word1", "Word");

        Entity entity1 = new Entity("A", "Word");
        Entity otherEntity1 = new Entity("oA", "Word");
        Entity otherEntity2 = new Entity("oB", "Word");

        entity.getRelatedEntities().add(entity1);

        otherEntity.getRelatedEntities().add(otherEntity1);
        otherEntity.getRelatedEntities().add(otherEntity2);

        entity = entity.mergeWord(otherEntity);

        Set<Entity> testRelated = Set.of(
            new Entity("A", "Word"),
            new Entity("oA", "Word"),
            new Entity("oB", "Word")
        );
        
        for (Entity testEntity : testRelated) {
            Assertions.assertTrue(entity.getRelatedEntities().contains(testEntity));
        }
    }

    @Test
    void mergeWord_OneAlreadyProcessedBefore() {
        Entity entity = new Entity("WORD1", "Word");
        Entity otherEntity = new Entity("Word1", "Word");

        Entity entity1 = new Entity("A", "Word");
        Entity otherEntity1 = new Entity("oA", "Word");
        Entity otherEntity2 = new Entity("oB", "Word");

        entity.getRelatedEntities().add(entity1);

        otherEntity.getRelatedEntities().add(otherEntity1);
        otherEntity.getRelatedEntities().add(otherEntity2);
    
        entity.processWord();
        entity.mergeWord(otherEntity);

        Set<Entity> testRelated = Set.of(
            new Entity("A", "Word"),
            new Entity("oA", "Word"),
            new Entity("oB", "Word")
        );
        
        for (Entity testEntity : testRelated) {
            Assertions.assertTrue(entity.getRelatedEntities().contains(testEntity));
        }
    }

    @Test
    void mergeWord_BothAlreadyProcessedBefore() {
        Entity entity = new Entity("WORD1", "Word");
        Entity otherEntity = new Entity("Word1", "Word");

        Entity entity1 = new Entity("A", "Word");
        Entity otherEntity1 = new Entity("oA", "Word");
        Entity otherEntity2 = new Entity("oB", "Word");

        entity.getRelatedEntities().add(entity1);

        otherEntity.getRelatedEntities().add(otherEntity1);
        otherEntity.getRelatedEntities().add(otherEntity2);
    
        entity.processWord();
        otherEntity.processWord();
        entity.mergeWord(otherEntity);

        Set<Entity> testRelated = Set.of(
            new Entity("A", "Word"),
            new Entity("oA", "Word"),
            new Entity("oB", "Word")
        );
        
        for (Entity testEntity : testRelated) {
            Assertions.assertTrue(entity.getRelatedEntities().contains(testEntity));
        }
    }
    //#endregion

    //#endregion 

    //#region Entity Processing

    //#region processPerson Test

    @Test
    void processPerson_AlreadyHasBeenProcessed() {
        Entity entity = new Entity("(?<Complainant>.+?)(?=(word))", "Complainant");
        entity.setProcessed();

        Entity entityWord1 = new Entity("word", "Word");
        Entity entityWord2 = new Entity("otherWord", "Word");

        entity.getRelatedEntities().add(entityWord1);
        entity.getRelatedEntities().add(entityWord2);

        entity.processPerson();

        Assertions.assertEquals("(?<Complainant>.+?)(?=(word|otherWord))", entity.getValue());
        Assertions.assertEquals("word|otherWord", entity.getInnerRegex());
    }

    @Test
    void processPerson_HasBeenNotProcessed() {
        // Может иметь или нет статическую часть в виде обычного регулярного выражения (обязательно в начале)
        // Может иметь или нет часть собирающую любые данные, оканчивающиеся на заданный regex
        // Должна быть проверка на окончание не только на слова, но и на другие виды токенов

        Entity entity = new Entity("", "Complainant");
        Entity entityWord1 = new Entity("A1", "Word");
        Entity entityWord2 = new Entity("A2", "Word");
        Entity entityWord3 = new Entity("A3", "Word");

        Entity entityKeyword1 = new Entity("keyword1", "Keyword");
        Entity entityKeyword2 = new Entity("keyword2", "Keyword");
        entityKeyword1.processKeyword();
        entityKeyword2.processKeyword();
        entityKeyword1.mergeKeyword(entityKeyword2);


        entity.getRelatedEntities().add(entityWord1);
        entity.getRelatedEntities().add(entityWord2);
        entity.getRelatedEntities().add(entityWord3);

        entity.getRelatedEntities().add(entityKeyword1);
        entity.getRelatedEntities().add(entityKeyword2);

        entity.processPerson();

        Assertions.assertEquals("(?<Complainant>.+?)(?=(A1|A2|A3|(?<Keyword>(keyword1)|(keyword2))))", entity.getValue());
        Assertions.assertEquals("A1|A2|A3|(?<Keyword>(keyword1)|(keyword2))", entity.getInnerRegex());
    }

    @Test 
    void processPerson_HasMultipleValues() {
        Entity entity = new Entity("(?<Complainant>.+?)(?=(this|value|already|here|(?<Keyword>(kw1))))", "Complainant");
        entity.setProcessed();

        Entity entityWord1 = new Entity("A1 (a11)", "Word");
        entityWord1.processWord();

        Entity entityWord2 = new Entity("A2", "Word");
        Entity entityWord3 = new Entity("A3", "Word");
        Entity entityKeyword1 = new Entity("kw1", "Keyword");
        Entity entityKeyword2 = new Entity("kw2", "Keyword");
        entityKeyword1.processKeyword();
        entityKeyword2.processKeyword();
        entityKeyword1.mergeKeyword(entityKeyword2);

        entity.getRelatedEntities().add(entityWord1);
        entity.getRelatedEntities().add(entityWord2);
        entity.getRelatedEntities().add(entityWord3);

        entity.getRelatedEntities().add(entityKeyword1);
        entity.getRelatedEntities().add(entityKeyword2);

        entity.processPerson();

        Assertions.assertEquals("(?<Complainant>.+?)(?=(here|A2|A3|already|this|value|a1 \\(a11\\)|(?<Keyword>(kw1)|(kw2))))", entity.getValue());
        Assertions.assertEquals("here|A2|A3|already|this|value|a1 \\(a11\\)|(?<Keyword>(kw1)|(kw2))", entity.getInnerRegex());
    }

    //#endregion

    //#region processKeyword Test
    @Test
    void processKeyword_AlreadyHasBeenProcessed() {
        Entity entity = new Entity("(?<Keyword>(AAA))", "Keyword");
        entity.setProcessed();

        Assertions.assertEquals("(?<Keyword>(AAA))", entity.processKeyword());
    }

    @Test
    void processKeyword_HasBeenNotProcessed() {
        Entity entity = new Entity("AAA", "Keyword");

        Assertions.assertEquals("(?<Keyword>(AAA))", entity.processKeyword());
    }

    @Test
    void processKeyword_HasMultipleValues() {
        Entity entity = new Entity("(?<Keyword>(AAA)|(CCC))", "Keyword");
        entity.setProcessed();

        Assertions.assertEquals("(?<Keyword>(AAA)|(CCC))", entity.processKeyword());
    }
    //#endregion

    //#region processWord Test 

    @Test
    void processWord_CorrectBracesReplacing() {
        Entity entity = new Entity("word (braces)", "Word");

        Assertions.assertEquals("word \\(braces\\)", entity.processWord());
        Assertions.assertEquals("word \\(braces\\)", entity.processWord());
    }

    @Test
    void processWord_ShortWordWrapping() {
        Entity entityShort1 = new Entity("s", "Word");
        Assertions.assertEquals("(^|\\s*)s($|\\s+)", entityShort1.processWord());
        Assertions.assertEquals("s", entityShort1.getInnerRegex());

        Entity entityShort2 = new Entity("sr", "Word");
        Assertions.assertEquals("(^|\\s*)sr($|\\s+)", entityShort2.processWord());
        Assertions.assertEquals("sr", entityShort2.getInnerRegex());
        
        Entity entityShort3 = new Entity("srt", "Word");
        Assertions.assertEquals("(^|\\s*)srt($|\\s+)", entityShort3.processWord());
        Assertions.assertEquals("srt", entityShort3.getInnerRegex());

        Entity entityLong = new Entity("loooooong", "Word");
        Assertions.assertEquals("loooooong", entityLong.processWord());
        Assertions.assertEquals("loooooong", entityLong.getInnerRegex());
    }

    //#endregion

    //#endregion

}
