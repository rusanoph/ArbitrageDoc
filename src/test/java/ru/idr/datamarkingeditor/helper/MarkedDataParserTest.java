package ru.idr.datamarkingeditor.helper;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.idr.datamarkingeditor.model.TokenType;

public class MarkedDataParserTest {
    
    private MarkedDataParser markedDataParser;

    @BeforeEach
    void setUp() {
        this.markedDataParser = new MarkedDataParser();
    }

    //#region 

    @Test
    void test_ChangingActualTokenInTokenSet() {
        // String text = """
                // """;

        Set<TokenType> tokens = new HashSet<>();

        TokenType token1 = new TokenType("value1", "Type");
        TokenType token2 = new TokenType("value2", "Number");

        token1.getAdjacentTokens().add(token2);

        tokens.add(token1);
        tokens.add(token2);

        for (TokenType token : tokens) {
            System.out.println(String.format("Token: (v: %s, t: %s, spec: %s, id: %s)", token.getValue(), token.getTokenType(), token.isSpecial(), token));

            for (TokenType adjToken : token.getAdjacentTokens()) {
                System.out.println(String.format("  AdjToken: (v: %s, t: %s, spec: %s, id: %s)", adjToken.getValue(), adjToken.getTokenType(), adjToken.isSpecial(), adjToken));
            }
        }

        token2.setValue("Absolute new value!!!");

        for (TokenType token : tokens) {
            System.out.println(String.format("Token: (v: %s, t: %s, spec: %s, id: %s)", token.getValue(), token.getTokenType(), token.isSpecial(), token));

            for (TokenType adjToken : token.getAdjacentTokens()) {
                System.out.println(String.format("  AdjToken: (v: %s, t: %s, spec: %s, id: %s)", adjToken.getValue(), adjToken.getTokenType(), adjToken.isSpecial(), adjToken));
            }
        }
        

        // Set<TokenType> parsedText = markedDataParser.parse(text);
    
        
    }

    //#endregion

    //#region processPerson Test
    @Test
    void processPerson_Condition() {
        // Может иметь или нет статическую часть в виде обычного регулярного выражения (обязательно в начале)
        // Может иметь или нет часть собирающую любые данные, оканчивающиеся на заданный regex
        // Должна быть проверка на окончание не только на слова, но и на другие виды токенов

        TokenType token = new TokenType("", "Complainant");
        TokenType tokenWord1 = new TokenType("A1", "Word");
        TokenType tokenWord2 = new TokenType("A2", "Word");
        TokenType tokenWord3 = new TokenType("A3", "Word");
        TokenType tokenWord4 = new TokenType("!!!", "Keyword");
        token.getAdjacentTokens().add(tokenWord1);
        token.getAdjacentTokens().add(tokenWord2);
        token.getAdjacentTokens().add(tokenWord3);
        token.getAdjacentTokens().add(tokenWord4);

        TokenType otherToken = new TokenType("", "Complainant");
        TokenType otherTokenWord1 = new TokenType("A1", "Word");
        TokenType otherTokenWord2 = new TokenType("B2", "Word");
        TokenType otherTokenWord3 = new TokenType("B3", "Word");
        TokenType otherTokenWord4 = new TokenType("???", "Defendant");
        otherToken.getAdjacentTokens().add(otherTokenWord1);
        otherToken.getAdjacentTokens().add(otherTokenWord2);
        otherToken.getAdjacentTokens().add(otherTokenWord3);
        otherToken.getAdjacentTokens().add(otherTokenWord4);
        
        String mergedTokenValue = markedDataParser.processPerson(token, otherToken);

        Assertions.assertEquals("(?<Complainant>.+?)(?=(A1|B2|B3|A2|A3))", mergedTokenValue);
    }

    //#endregion

    //#region processKeyword Test
    @Test
    void processKeyword_BothAlreadyHasBeenProcessed() {
        TokenType token1 = new TokenType("(?<Keyword>(AAA))", "Keyword");
        TokenType token2 = new TokenType("(?<Keyword>(BBB))", "Keyword");

        Assertions.assertEquals("(?<Keyword>(BBB)|(AAA))", markedDataParser.processKeyword(token1, token2));
    }

    @Test
    void processKeyword_OneAlreadyHasBeenProcessed() {
        TokenType token1 = new TokenType("(?<Keyword>(AAA))", "Keyword");
        TokenType token2 = new TokenType("BBB", "Keyword");

        Assertions.assertEquals("(?<Keyword>(BBB)|(AAA))", markedDataParser.processKeyword(token1, token2));
    }

    @Test
    void processKeyword_OneHasBeenNotProcessed() {
        TokenType token1 = new TokenType("AAA", "Keyword");
        TokenType token2 = new TokenType("BBB", "Keyword");

        Assertions.assertEquals("(?<Keyword>(BBB)|(AAA))", markedDataParser.processKeyword(token1, token2));
    }

    @Test
    void processKeyword_OneHasMultipleValues() {
        TokenType token1 = new TokenType("(?<Keyword>(AAA)|(CCC))", "Keyword");
        TokenType token2 = new TokenType("BBB", "Keyword");

        Assertions.assertEquals("(?<Keyword>(BBB)|(AAA)|(CCC))", markedDataParser.processKeyword(token1, token2));
    }
    //#endregion

}
