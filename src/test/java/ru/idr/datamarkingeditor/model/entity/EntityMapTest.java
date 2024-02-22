package ru.idr.datamarkingeditor.model.entity;

import java.util.Iterator;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import ru.idr.datamarkingeditor.model.entity.arbitrage.person.ComplainantEntity;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.DefendantEntity;
import ru.idr.datamarkingeditor.model.entity.common.WordEntity;
import ru.idr.datamarkingeditor.model.token.CommonToken;

public class EntityMapTest {
    
    @Test
    public void toJsonArrayAsHashCodes() {
        EntityMap entities = (new EntityMap())
            .add(new WordEntity("hello"))
            .add(new WordEntity("hello"))
            .add(new WordEntity("world"));

        JSONArray entitiesHashCodeJson = entities.toJsonArrayAsHashCodes();

        JSONArray testExpected = (new JSONArray())
            .put(Objects.hash("(hello)", CommonToken.Word.getLabel()))
            .put(Objects.hash("(world)", CommonToken.Word.getLabel()));

        Assertions.assertTrue(entitiesHashCodeJson.similar(testExpected));
    }

    @Test
    public void toJsonObject() {
        EntityMap entities = (new EntityMap())
            .add(new WordEntity("hello"))
            .add(new WordEntity("hello"))
            .add(new WordEntity("world"));

        JSONObject entitiesJson = entities.toJsonObject();

        JSONObject testExpected = (new JSONObject())
            .put(
                "" + Objects.hash("(hello)", CommonToken.Word.getLabel()), 
                new WordEntity("hello").toJsonObject()
            )
            .put(
                "" + Objects.hash("(world)", CommonToken.Word.getLabel()), 
                new WordEntity("world").toJsonObject()
            );

        Assertions.assertTrue(entitiesJson.similar(testExpected));
    }

    @Test
    public void fromJsonArray() {
        String entityMapJsonString = """
            {
                "1945889885": {
                    "related": [
                        1801351948,
                        609635184
                    ],
                    "innerRegex": {"Word": ["(hello)"]},
                    "type": "Complainant",
                    "value": "(?<Complainant>.+?)(?=((hello)))"
                },
                "609635184": {
                    "related": [1945889885],
                    "innerRegex": {},
                    "type": "Defendant",
                    "value": "(?<Defendant>.+?)(?=())"
                },
                "1801351948": {
                    "related": [],
                    "innerRegex": {"Word": ["(hello)"]},
                    "type": "Word",
                    "value": "(hello)"
                }
            }
                """;
        EntityMap entityMap = EntityMap.fromJsonObject(entityMapJsonString);

        Entity testWord = new WordEntity("hello");
        Entity testComplainant = new ComplainantEntity("this value doesn't matter");
        Entity testDefendant = new DefendantEntity("this also doesn't matter");

        testComplainant.connect(testDefendant).connect(testWord);
        testDefendant.connect(testComplainant);

        EntityMap testExpected = new EntityMap()
            .add(testDefendant)
            .add(testComplainant)
            .add(testWord);

        
        for (Entity test : testExpected) {
            Assertions.assertTrue(entityMap.contains(test));
        }
    }   

    @Test 
    public void jsonSerialization_invariantTest() {
        Entity entity1 = new ComplainantEntity("");
        Entity entity2 = new DefendantEntity("");
        Entity entity3 = new WordEntity("hello");

        entity1
            .connect(entity2)
            .connect(entity3);
        entity2
            .connect(entity1);

        EntityMap testEntities = new EntityMap()
            .add(entity1)
            .add(entity2)
            .add(entity3);

        EntityMap entities = EntityMap.fromJsonObject(testEntities.toJsonObject());

        Iterator<Entity> testIterator = testEntities.iterator();
        Iterator<Entity> entitiesIterator = entities.iterator();
        while (entitiesIterator.hasNext() && entitiesIterator.hasNext()) {
            Entity entity = entitiesIterator.next();
            Entity testEntity = testIterator.next();

            Assertions.assertEquals(entity, testEntity);
        }
        
    } 
}
