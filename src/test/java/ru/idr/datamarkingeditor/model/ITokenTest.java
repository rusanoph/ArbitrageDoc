package ru.idr.datamarkingeditor.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;

public class ITokenTest {

    //#region isPerson Test
    @Test
    public void isPerson_Complainant() {
        IToken person = ArbitrageToken.Complainant;
        Assertions.assertTrue(person.isPerson());
    }

    @Test
    public void isPerson_Defendant() {
        IToken person = ArbitrageToken.Defendant;
        Assertions.assertTrue(person.isPerson());
    }

    @Test
    public void isPerson_ThirdParty() {
        IToken person = ArbitrageToken.ThirdParty;
        Assertions.assertTrue(person.isPerson());
    }

    @Test
    public void isPerson_CompetitionManager() {
        IToken person = ArbitrageToken.CompetitionManager;
        Assertions.assertTrue(person.isPerson());
    }

    @Test
    public void isPerson_FinancialManager() {
        IToken person = ArbitrageToken.FinancialManager;
        Assertions.assertTrue(person.isPerson());
    }

    @Test
    public void isPerson_CourtCaseSum_NotPerson() {
        IToken notPerson = ArbitrageToken.CourtCaseSum;
        Assertions.assertFalse(notPerson.isPerson());
    }

    @Test
    public void isPerson_Keyword_NotPerson() {
        IToken notPerson = ArbitrageToken.Keyword;
        Assertions.assertFalse(notPerson.isPerson());
    }

    @Test
    public void isPerson_Word_NotPerson() {
        IToken notPerson = CommonToken.Word;
        Assertions.assertFalse(notPerson.isPerson());
    }
    //#endregion
    
    //#region fromString Test

    @Test
    public void fromString_ValidStringValue_ReturnsCommonToken() throws Exception {
        IToken token = IToken.fromString("Word");
        IToken testToken = CommonToken.Word;


        Assertions.assertEquals(testToken, token);
    }

    @Test
    public void fromString_ValidStringValue_ReturnsArbitrageToken() throws Exception {
        IToken token = IToken.fromString("Keyword");
        IToken testToken = ArbitrageToken.Keyword;

        Assertions.assertEquals(testToken, token);
    }

    @Test
    public void fromString_InvalidStringValue_ThrowsIllegalArgumentException() {
        
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> IToken.fromString("ThisTokenTypeWillNeverUse")
        );
    }

    //#endregion
}
