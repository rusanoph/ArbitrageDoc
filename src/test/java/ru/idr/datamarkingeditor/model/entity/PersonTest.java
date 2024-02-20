package ru.idr.datamarkingeditor.model.entity;

import org.junit.jupiter.api.Test;

import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;

public class PersonTest {

    
    //#region Process Test
    @Test
    void processPerson_HasBeenNotProcessed() {
        // Может иметь или нет статическую часть в виде обычного регулярного выражения (обязательно в начале)
        // Может иметь или нет часть собирающую любые данные, оканчивающиеся на заданный regex
        // Должна быть проверка на окончание не только на слова, но и на другие виды токенов

        Entity entity = new PersonEntity("", ArbitrageToken.Complainant);
        Entity entityWord1 = new WordEntity("A1", CommonToken.Word);
        Entity entityWord2 = new WordEntity("A2", CommonToken.Word);
        Entity entityWord3 = new WordEntity("A3", CommonToken.Word);

        Entity entityKeyword1 = new KeywordEntity("keyword1", ArbitrageToken.Keyword);
        Entity entityKeyword2 = new KeywordEntity("keyword2", ArbitrageToken.Keyword);
        entityKeyword1.process();
        entityKeyword2.process();
        entityKeyword1.merge(entityKeyword2);


        entity.getRelated().add(entityWord1);
        entity.getRelated().add(entityWord2);
        entity.getRelated().add(entityWord3);

        entity.getRelated().add(entityKeyword1);
        entity.getRelated().add(entityKeyword2);

        entity.process();

        // Assertions.assertEquals("(?<Complainant>.+?)(?=(A1|A2|A3|(?<Keyword>(keyword2)|(keyword1))))", entity.getValue());
        // Assertions.assertEquals("A1|A2|A3|(?<Keyword>(keyword2)|(keyword1))", entity.getInnerRegex());
    }

    @Test 
    void processPerson_HasMultipleValues() {
        Entity entity = new PersonEntity("(?<Complainant>.+?)(?=(this|value|already|here|(?<Keyword>(kw3))))", ArbitrageToken.Complainant);

        Entity entityWord1 = new WordEntity("A1 (a11)", CommonToken.Word);

        Entity entityWord2 = new WordEntity("A2", CommonToken.Word);
        Entity entityWord3 = new WordEntity("A3", CommonToken.Word);
        Entity entityKeyword1 = new KeywordEntity("kw1", ArbitrageToken.Keyword);
        Entity entityKeyword2 = new KeywordEntity("kw2", ArbitrageToken.Keyword);
        entityKeyword1.process();
        entityKeyword2.process();
        entityKeyword1.merge(entityKeyword2);

        entity.getRelated().add(entityWord1);
        entity.getRelated().add(entityWord2);
        entity.getRelated().add(entityWord3);

        entity.getRelated().add(entityKeyword1);
        entity.getRelated().add(entityKeyword2);

        entity.process();

        // Assertions.assertEquals("(?<Complainant>.+?)(?=(here|A2|A3|already|this|value|a1 \\(a11\\)|(?<Keyword>(kw1)|(kw2))))", entity.getValue());
        // Assertions.assertEquals("here|A2|A3|already|this|value|a1 \\(a11\\)|(?<Keyword>(kw1)|(kw2))", entity.getInnerRegex());
    }

    @Test
    void processPerson_HasOnlyKeywordAndNotProcessed() {
        Entity entity = new PersonEntity("", ArbitrageToken.Complainant);

        Entity entityKeyword1 = new KeywordEntity("keyword1", ArbitrageToken.Keyword);
        Entity entityKeyword2 = new KeywordEntity("keyword2", ArbitrageToken.Keyword);

        entityKeyword1.process();
        entityKeyword2.process();
        entityKeyword1.merge(entityKeyword2);

        entity.getRelated().add(entityKeyword1);
        entity.getRelated().add(entityKeyword2);


        // Assertions.assertEquals("(?<Complainant>.+?)(?=((?<Keyword>(keyword2)|(keyword1))))", entity.getValue());
        // Assertions.assertEquals("(?<Keyword>(keyword2)|(keyword1))", entity.getInnerRegex());
    }
    //#endregion

    //#region Merge Test
    
    //#endregion


}
