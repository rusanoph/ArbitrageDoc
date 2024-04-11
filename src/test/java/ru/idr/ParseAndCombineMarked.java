package ru.idr;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.junit.Test;

import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.datamarkingeditor.helper.MarkedDataParser;
import ru.idr.datamarkingeditor.model.entity.EntityMap;

public class ParseAndCombineMarked {
    String jsonDir = "markedDataJson";
    String directoryPath = "JsonTest/ModelOn20Files";

    @Test
    public void parseMarkedToJson()  {
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

    @Test
    public void combine() throws IOException {
        MarkedDataParser parser = new MarkedDataParser();
        EntityMap entities = parser.combineAll("markedDataJson", "JsonTest", "ModelOn20Files", "json");

        ServerFile.saveString(entities.toJsonString(), jsonDir + "/" + directoryPath + "/combined", "combined.json");
    }
}
