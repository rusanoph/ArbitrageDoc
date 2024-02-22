package ru.idr.datamarkingeditor.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;
import ru.idr.datamarkingeditor.model.token.IToken;

public class InnerRegexMapTest {
    
    @Test
    public void constructor_RightKeyTokenOrder() {
        InnerRegexMap irm = new InnerRegexMap();

        var testRightKeyTokenOrder = InnerRegexMap.allowedTokens;

        var irmIterator = irm.keySet().iterator();
        var testIterator = testRightKeyTokenOrder.iterator();

        while (irmIterator.hasNext() && testIterator.hasNext()) {
            IToken irmToken = irmIterator.next();
            IToken testToken = testIterator.next();

            Assertions.assertEquals(testToken, irmToken);
        }

    }

    @Test
    public void isEmptyAll() {
        InnerRegexMap innerRegexMap = new InnerRegexMap();

        Assertions.assertTrue(innerRegexMap.isEmptyAll());

        innerRegexMap.put(ArbitrageToken.Keyword, "keyword");

        Assertions.assertFalse(innerRegexMap.isEmptyAll());
    }

    @Test
    public void fromJsonObject_Serialization() {
        JSONObject irmJson = (new JSONObject())
            .put("Word", new JSONArray(List.of("word1", "word2")))
            .put("FinancialManager", new JSONArray(List.of("value1", "value2", "value1")))
            .put("Keyword", new JSONArray(List.of("keyword1", "keyword2")))
            .put("Complainant", new JSONArray(List.of("complainant1", "complainant2")))
            .put("CompetitionManager", new JSONArray(List.of("competitionManager1", "competitionManager2")))
            .put("MoneySum", new JSONArray(List.of("courtCaseSum1", "courtCaseSum2")))
            .put("Defendant", new JSONArray(List.of("defendant1", "defendant2")))
            .put("ThirdParty", new JSONArray(List.of("thirdParty1", "thirdParty2")));

        InnerRegexMap irm = InnerRegexMap.fromJsonObject(irmJson);

        //#region Test InnerRegexMap
        InnerRegexMap testIrm = new InnerRegexMap();
        testIrm.putAll(CommonToken.Word, "word1", "word2")
            .putAll(CommonToken.MoneySum, "courtCaseSum1", "courtCaseSum2")
            .putAll(ArbitrageToken.FinancialManager, "value1", "value2", "value1")
            .putAll(ArbitrageToken.Keyword, "keyword1", "keyword2")
            .putAll(ArbitrageToken.Complainant, "complainant1", "complainant2")
            .putAll(ArbitrageToken.CompetitionManager, "competitionManager1", "competitionManager2")
            .putAll(ArbitrageToken.Defendant, "defendant1", "defendant2")
            .putAll(ArbitrageToken.ThirdParty, "thirdParty1", "thirdParty2")
        ;
        //#endregion

        Assertions.assertTrue(irm.equals(testIrm));
    }

    @Test
    public void toJsonObject_Serialization() {
        InnerRegexMap irm = new InnerRegexMap();

        JSONObject irmJson = irm.toJsonObject();
        JSONObject testJson = new JSONObject();

        Assertions.assertEquals(testJson.toString(), irmJson.toString());
    }

    @Test
    public void toJsonObject_NotEmptySerialization() {
        InnerRegexMap irm = new InnerRegexMap();
        irm
            .put(CommonToken.MoneySum, "sum")
            .put(ArbitrageToken.Defendant, "sum")
            .put(ArbitrageToken.ThirdParty, "sum");

        JSONObject irmJson = irm.toJsonObject();
        JSONObject testJson = (new JSONObject())
            .put("MoneySum", new JSONArray("[\"sum\"]"))
            .put("Defendant", new JSONArray("[\"sum\"]"))
            .put("ThirdParty", new JSONArray("[\"sum\"]"));

        Assertions.assertEquals(testJson.toString(), irmJson.toString());
    }
}
