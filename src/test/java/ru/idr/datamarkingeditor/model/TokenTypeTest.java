package ru.idr.datamarkingeditor.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.idr.arbitragestatistics.model.arbitrage.ArbitrageToken;

public class TokenTypeTest {
    

    @Test
    void hasNamedRegexValue_ForKeyword() {

        TokenType token = new TokenType("(?<Keyword>(kw1)|(kw2)|(kw3))", "Keyword");
        boolean b = token.hasNamedRegexValue(ArbitrageToken.Keyword);

        Assertions.assertTrue(b);
    }

    @Test
    void hasNamedRegexValue_ForPersonsWithForwardLookup() {

        TokenType token = new TokenType("(?<Complainant>.+?)(?=())", "Complainant");
        boolean b = token.hasNamedRegexValue(ArbitrageToken.Complainant);

        Assertions.assertTrue(b);
    }

}
