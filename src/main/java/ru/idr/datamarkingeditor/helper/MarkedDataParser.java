package ru.idr.datamarkingeditor.helper;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.entity.EntityMap;
import ru.idr.datamarkingeditor.model.token.IToken;
import ru.idr.datamarkingeditor.model.token.UtilityToken;

public class MarkedDataParser {

    public EntityMap parse(String text) {
        EntityMap entities = new EntityMap();

        text = "<root>" + text + "</root>";
        Document document = Jsoup.parse(text, "", Parser.xmlParser());
        Element root = document.selectFirst("root");

        Entity current; 
        Entity next = null;
        for (int i = 0; i < root.childrenSize() - 1; i++) {
            Element currentToken = root.children().get(i);
            IToken currentType = IToken.fromString(currentToken.tagName());
            current = Entity.createInstance(currentToken.text(), currentType);

            // On first iteration
            if (i == 0) {
                Entity initial = Entity.createInstance(UtilityToken.Initial);
                initial.connect(current);
                entities.add(initial);
            }
            
            Element nextToken = root.children().get(i + 1);
            IToken nextType = IToken.fromString(nextToken.tagName());
            next = Entity.createInstance(nextToken.text(), nextType);

            current.connect(next);            
            entities.add(current);
        }

        if (next != null) {
            entities.add(next);
        }

        return entities;
    }

    public EntityMap combine(Set<String> files, String directoryPath, String... restDirectoryPath) throws IOException {
        EntityMap combinedEntities = new EntityMap();

        for (String file : files) {
            String rawJson = ServerFile.fileText(file, directoryPath, restDirectoryPath);
            EntityMap currentEntities = EntityMap.fromJsonObject(rawJson);
            
            for (Entity e : currentEntities) {
                combinedEntities.add(e);
            }
            combinedEntities.unvisitAll();
        }

        return combinedEntities;
    }

    @SafeVarargs
    public final EntityMap combineAll(String directoryPath, String... restDirectoryPath) throws IOException {

        Set<String> filesInDirectory = 
            ServerFile.listFilesServer(directoryPath, restDirectoryPath)
            .stream()
            .filter(file -> file.endsWith("json"))
            .collect(Collectors.toSet());

        return this.combine(filesInDirectory, directoryPath, restDirectoryPath);
    }

}
