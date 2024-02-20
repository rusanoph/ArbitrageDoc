package ru.idr.datamarkingeditor.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.idr.datamarkingeditor.model.InnerRegexMap;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;

public class KeywordTest {

    //#region Process Test
    @Test
    void processKeyword_HasMultipleValues() {
        Entity entity = new KeywordEntity("keyword1", ArbitrageToken.Keyword);
        entity.process();

        Assertions.assertEquals("(?<Keyword>(keyword1))", entity.getValue());
    }
    //#endregion

    //#region Merge Test
    @Test
    void mergeKeyword_BothNotProcessedBefore() {
        Entity entityKeyword1 = new KeywordEntity("keyword1", ArbitrageToken.Keyword);
        Entity entityKeyword2 = new KeywordEntity("keyword2 with appendix", ArbitrageToken.Keyword);
        entityKeyword1.merge(entityKeyword2);
        
        InnerRegexMap testInnerRegexMap = (new InnerRegexMap())
        .putAll(ArbitrageToken.Keyword, "(keyword2 with appendix)", "(keyword1)");
        Assertions.assertTrue(entityKeyword1.getInnerRegexMap().equals(testInnerRegexMap));
        Assertions.assertEquals("(?<Keyword>(keyword1)|(keyword2 with appendix))", entityKeyword1.getValue());
    }

    @Test
    void mergeKeyword_otherIsNotTypeOfKeyword_ThrowsException() {
        Entity entityKeyword = new KeywordEntity("keyword", ArbitrageToken.Keyword);
        Entity entityNotKeyword = new WordEntity("not keyword", CommonToken.Word);

        Assertions.assertThrows(
            IllegalArgumentException.class, 
            () -> entityKeyword.merge(entityNotKeyword));
    }
    //#endregion
}
