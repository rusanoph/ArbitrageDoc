package ru.idr.datamarkingeditor.helper;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Component;

import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.entity.EntityMap;
import ru.idr.datamarkingeditor.model.token.IToken;

@Component
public class MarkedDataParser {

    EntityMap entities;

    public MarkedDataParser() { this.entities = new EntityMap(); }
    public MarkedDataParser(EntityMap entities) { this.entities = entities; }

    public EntityMap parse(String text) {
        text = "<root>" + text + "</root>";
        Document document = Jsoup.parse(text, "", Parser.xmlParser());
        Element root = document.selectFirst("root");

        Entity current; 
        Entity next = null;
        for (int i = 0; i < root.childrenSize() - 1; i++) {
            Element currentToken = root.children().get(i);
            IToken currentType = IToken.fromString(currentToken.tagName());
            current = Entity.createInstance(currentToken.text(), currentType);
            
            Element nextToken = root.children().get(i + 1);
            IToken nextType = IToken.fromString(nextToken.tagName());
            next = Entity.createInstance(nextToken.text(), nextType);

            current.connect(next);            
            this.entities.add(current);
        }

        if (next != null) {
            this.entities.add(next);
        }

        return this.entities;
    }

    @SafeVarargs
    public final EntityMap combineAll(String directoryPath, String... restDirectoryPath) throws IOException {

        EntityMap combinedEntities = new EntityMap();
        Set<String> filesInDirectory = 
            ServerFile.listFilesServer(directoryPath, restDirectoryPath)
            .stream()
            .filter(file -> file.endsWith("json"))
            .collect(Collectors.toSet());

        for (String filename : filesInDirectory) {
            String rawJson = ServerFile.fileText(filename, directoryPath, restDirectoryPath);
            EntityMap currentEntities = EntityMap.fromJsonObject(rawJson);
            
            combinedEntities.addAll(currentEntities);
        }

        return combinedEntities;
    }

    //#region JSON Serialization
    public String toJsonString() {
        return this.entities.toJsonString();
    }

    public JSONObject toJsonObject() {
        return this.entities.toJsonObject();
    }
    //#endregion
}
