package ru.idr.datamarkingeditor.helper;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Server;
import org.json.JSONObject;
import org.junit.Test;

import ru.idr.arbitragestatistics.helper.DocumentProcessor;
import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.entity.EntityMap;
import ru.idr.datamarkingeditor.model.entity.arbitrage.KeywordEntity;
import ru.idr.datamarkingeditor.model.entity.common.WordEntity;
import ru.idr.datamarkingeditor.model.entity.utility.InitialEntity;


public class MarkedDataParserTest {

    @Test
    public void parse_HasSeveralKeywords() {
        MarkedDataParser parser = new MarkedDataParser();

        String testText = """
            ОПРЕДЕЛЕНИЕ 
            о <Keyword>замене </Keyword>кредитора в <Word>реестре </Word><Word>требований </Word><Keyword>кредиторов</Keyword> должника 
            г.Москва 
            30 декабря 2021 года                                                                       Дело №А41-61904/18 
                """;

        EntityMap entities = parser.parse(testText);

        Entity initialEntity = new InitialEntity();

        Entity entityKeyword1 = new KeywordEntity("замене");
        Entity entityKeyword2 = new KeywordEntity("кредиторов");
        
        Entity entityWord1 = new WordEntity("реестре");
        Entity entityWord2 = new WordEntity("требований");

        initialEntity.connect(entityKeyword1);

        entityKeyword1.merge(entityKeyword2).connect(entityWord1);
        entityWord1.connect(entityWord2);
        entityWord2.connect(entityKeyword1);

        EntityMap testExpected = new EntityMap()
            .add(initialEntity)
            .add(entityKeyword1)
            .add(entityKeyword2)
            .add(entityWord1)
            .add(entityWord2);

        Assertions.assertTrue(entities.toJsonObject().similar(testExpected.toJsonObject()));
    }

    
    @Test
    public void combineAll() throws IOException {
        MarkedDataParser parser = new MarkedDataParser();

        EntityMap entities = parser.combineAll("markedDataJson", "JsonTest", "CombineAllTest");

        Entity initialEntity = new InitialEntity();

        Entity entityKeyword1 = new KeywordEntity("кредиторов");
        Entity entityKeyword2 = new KeywordEntity("краснодарского");
        
        Entity entityWord1 = new WordEntity("составе");
        Entity entityWord2 = new WordEntity("края");
        Entity entityWord3 = new WordEntity("требований");
        Entity entityWord4 = new WordEntity("реестре");
        
        initialEntity.connect(entityKeyword1).connect(entityWord4);
        entityKeyword1.merge(entityKeyword2).connect(entityWord2);
        entityWord2.connect(entityWord1);
        entityWord3.connect(entityKeyword1);
        entityWord4.connect(entityWord3);

        EntityMap testExpected = new EntityMap()
            .add(initialEntity)
            .add(entityKeyword1)
            .add(entityKeyword2)
            .add(entityWord1)
            .add(entityWord2)
            .add(entityWord3)
            .add(entityWord4);

        Assertions.assertTrue(entities.toJsonObject().similar(testExpected.toJsonObject()));
    }


    @Test
    public void combine() throws IOException {
        MarkedDataParser parser = new MarkedDataParser();
        EntityMap entities = parser.combineAll("markedDataJson", "JsonTest", "ModelOn20Files");
        System.out.println(entities.toJsonObject().toString(4));
    }

    @Test
    public void parseMarkedToJson()  {
        String jsonDir = "markedDataJson";
        String directoryPath = "JsonTest/ModelOn20Files";

        MarkedDataParser parser = new MarkedDataParser();
        Set<JSONObject> parsedJson = 
            ServerFile.listFilesServer(jsonDir, directoryPath)
            .stream()
            .filter(file -> file.endsWith("marked"))
            .map(file -> {
                try {
                    return ServerFile.fileText(file, jsonDir, directoryPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "";
                }
            })
            .map(text -> parser.parse(text))
            .map(entityMap -> entityMap.toJsonObject())
            .collect(Collectors.toSet());

        int i = 0;
        for (JSONObject json : parsedJson) {
            ServerFile.saveString(json.toString(4), jsonDir + "/" + directoryPath + "/json", ++i + ".json");
        }
    }
}
