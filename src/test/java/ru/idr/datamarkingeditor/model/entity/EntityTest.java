package ru.idr.datamarkingeditor.model.entity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ru.idr.datamarkingeditor.model.token.ArbitrageToken;

public class EntityTest {
    
    @Test
    public void toJsonObject() {
        Entity entity = new PersonEntity("entity test", ArbitrageToken.Complainant);

        JSONObject entityJson = entity.toJsonObject();
        JSONObject testJson = (new JSONObject())
            .put("value", "entity test")
            .put("type", "Complainant")
            .put("innerRegex", entity.getInnerRegexMap().toJsonObject())
            .put("related", new JSONArray());

        Assertions.assertEquals(testJson.toString(), entityJson.toString());
    }

    @Test
    public void toJsonString() {
        Entity entity = new PersonEntity("entity test", ArbitrageToken.Complainant);
        JSONObject entityJson = entity.toJsonObject();

        Assertions.assertEquals("{\"related\":[],\"innerRegex\":{\"FinancialManager\":[],\"Word\":[],\"Keyword\":[],\"Complainant\":[],\"CompetitionManager\":[],\"CourtCaseSum\":[],\"Defendant\":[],\"ThirdParty\":[]},\"type\":\"Complainant\",\"value\":\"entity test\"}", entityJson.toString());
    }

    
}
