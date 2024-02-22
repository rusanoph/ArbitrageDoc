package ru.idr.datamarkingeditor.model.entity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ru.idr.datamarkingeditor.model.entity.arbitrage.person.ComplainantEntity;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.DefendantEntity;
import ru.idr.datamarkingeditor.model.entity.common.WordEntity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;

public class EntityTest {
    
    @Test
    public void toJsonObject_WithoutRelated() {
        Entity entity = new ComplainantEntity("entity test", ArbitrageToken.Complainant);

        JSONObject entityJson = entity.toJsonObject();
        JSONObject testJson = (new JSONObject())
            .put("value", ArbitrageToken.Complainant.getRegex())
            .put("type", "Complainant")
            .put("innerRegex", new JSONObject())
            .put("related", new JSONArray());

        Assertions.assertTrue(entityJson.similar(testJson));
    }

    @Test
    public void toJsonObject_WithRelatedAndCyclic() {
        Entity entity1 = new ComplainantEntity("");
        Entity entity2 = new DefendantEntity("");

        entity1
            .connect(new WordEntity("hello"))
            .connect(entity2);
        entity2
            .connect(entity1);

        JSONObject entity1Json = entity1.toJsonObject();
        JSONObject entity2Json = entity2.toJsonObject();

        JSONObject entity1TestExpected = (new JSONObject())
            .put("related", new JSONArray("[1801351948,609635184]"))
            .put("innerRegex", new JSONObject("{\"Word\": [\"(hello)\"]}"))
            .put("type", ArbitrageToken.Complainant.getLabel())
            .put("value", "(?<Complainant>.+?)(?=((hello)))")
        ;
        JSONObject entity2TestExpected = (new JSONObject())
            .put("related", new JSONArray("[1945889885]"))
            .put("innerRegex", new JSONObject())
            .put("type", ArbitrageToken.Defendant.getLabel())
            .put("value", "(?<Defendant>.+?)(?=())")
        ;

        // EntityMap entities = new EntityMap();
        // entities
        //     .add(new WordEntity("hello"))
        //     .add(entity1)
        //     .add(entity2);

        // System.out.println("=== Entity .toJsonObject() ===");
        // System.out.println(entity1.hashCode() + " -> " + entity1.toJsonString());
        // System.out.println(entity2.hashCode() + " -> " + entity2.toJsonString());
    
        // System.out.println("=== EntityMap .toJsonObject() ===");
        // System.out.println(entities.toJsonObject().toString(4));

        Assertions.assertTrue(entity1Json.similar(entity1TestExpected));
        Assertions.assertTrue(entity2Json.similar(entity2TestExpected));
    }

    @Test
    public void toJsonString_WithoutRelated() {
        Entity entity = new ComplainantEntity("entity test", ArbitrageToken.Complainant);
        JSONObject entityJson = entity.toJsonObject();

        String testExpected = "{"+
            "\"related\":[],"+
            "\"innerRegex\":{},"+
            "\"type\":\"Complainant\","+
            "\"value\":\"(?<Complainant>.+?)(?=())\""+
        "}";

        // System.out.println(entityJson);

        Assertions.assertEquals(
            testExpected, 
            entityJson.toString()
        );
    }

}
