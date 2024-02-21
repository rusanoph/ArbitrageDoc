package ru.idr.datamarkingeditor.model.entity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ru.idr.datamarkingeditor.model.entity.arbitrage.person.ComplainantEntity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;

public class EntityTest {
    
    @Test
    public void toJsonObject() {
        Entity entity = new ComplainantEntity("entity test", ArbitrageToken.Complainant);

        JSONObject entityJson = entity.toJsonObject();
        JSONObject testJson = (new JSONObject())
            .put("value", ArbitrageToken.Complainant.getRegex())
            .put("type", "Complainant")
            .put("innerRegex", entity.getInnerRegexMap().toJsonObject())
            .put("related", new JSONArray());

        Assertions.assertTrue(entityJson.similar(testJson));
    }

    @Test
    public void toJsonString() {
        Entity entity = new ComplainantEntity("entity test", ArbitrageToken.Complainant);
        JSONObject entityJson = entity.toJsonObject();

        String testExpected = "{"+
            "\"related\":[],"+
            "\"innerRegex\":"+
                "{\"MoneySum\":[],"+
                "\"FinancialManager\":[],"+
                "\"Word\":[],"+
                "\"Keyword\":[],"+
                "\"Complainant\":[],"+
                "\"CompetitionManager\":[],"+
                "\"Defendant\":[],"+
                "\"ThirdParty\":[]},"+
            "\"type\":\"Complainant\","+
            "\"value\":\"(?<Complainant>.+?)(?=())\""+
        "}";

        System.out.println(entityJson);

        Assertions.assertEquals(
            testExpected, 
            entityJson.toString()
        );
    }

    @Test
    public void connect() {
        // test if entities merging
    }
}
