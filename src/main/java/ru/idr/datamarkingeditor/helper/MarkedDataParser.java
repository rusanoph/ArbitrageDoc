package ru.idr.datamarkingeditor.helper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.OperationNotSupportedException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.arbitragestatistics.helper.regex.RegExBuilder;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.Vertex;
import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;
import ru.idr.datamarkingeditor.model.token.CommonToken;

@Component
public class MarkedDataParser {
    // * Нужно отдельно предобрабатывать каждый тип токена
    // * Отдельно склеивать два токена одинакового типа
    // * Нужно провести рефакторинг и очистить код от лишней логики
    // * Требуется покрыть тестами методы
    // * Разработать токен для даты
    // * Разработать токен для адреса





    private RegExBuilder rb;

    public MarkedDataParser() {
        this.rb = new RegExBuilder();
    }

    public static Graph<String> getGraphFromTokenSet(Set<Entity> parsedTokens) {
        Graph<String> graph = new Graph<>();

        for (Entity token : parsedTokens) {

            Vertex<String> v;
            if (graph.getVertexByDepthValue(0, token.getValue()) == null) {
                v = new Vertex<String>(token.getValue(), token.getType());
            } else {
                v = graph.getVertexByDepthValue(0, token.getValue());
            }
            graph.addVertex(v);
            
            System.out.println();
            System.out.println(String.format("Token: (v: %s, t: %s, spec: %s, id: %s)", token.getValue(), token.getType(), token.isSpecial(), token));
            // for (Entity adjToken : token.getAdjacentTokens()) {
            //     System.out.println(String.format("\tAdjToken: (v: %s, t: %s, spec: %s, id: %s)", adjToken.getValue(), adjToken.getType(), adjToken.isSpecial(), adjToken));

            //     Vertex<String> u;
            //     if (graph.getVertexByDepthValue(0, adjToken.getValue()) == null) {
            //         u = new Vertex<String>(adjToken.getValue(), adjToken.getType());
            //     } else {
            //         u = graph.getVertexByDepthValue(0, adjToken.getValue());
            //     }
                
            //     graph.addOrEdge(v, u);
            // }
            System.out.println(String.format("Vertex: (v: %s, t: %s, spec: %s)", v.getValue(), v.getType(), v.isSpecial()));
            
        }
        
        return graph;
    }

    private Set<Entity> addTokenTypeToSet(Set<Entity> tokens, Entity otherToken) {

        if (tokens.contains(otherToken)) {
            for (Entity token : tokens) {
                if (token.equals(otherToken)) {

                }
            }
        } else {
            tokens.add(otherToken);
        }

        return tokens;
    }

    private Entity getTokenInstanceFromSet(Set<Entity> container, Entity patternToken) {
        if (container.contains(patternToken)) {
            for (Entity token : container) {
                if (token.equals(patternToken)) return token;
            }
        }
        return patternToken; 
    }

    public Set<Entity> parse(String text) {
        Set<Entity> parsedText = new HashSet<>();
        
        text = "<root>" + text + "</root>";
        Document document = Jsoup.parse(text, "", org.jsoup.parser.Parser.xmlParser());
        Element root = document.selectFirst("root");

        Entity current = null; 
        Entity next = null;
        for (int i = 0; i < root.childrenSize() - 1; i++) {
            Element currentToken = root.children().get(i);
            Element nextToken = root.children().get(i + 1);

            current = new Entity(currentToken.text(), currentToken.tagName());
            if (parsedText.contains(current)) current = getTokenInstanceFromSet(parsedText, current);

            next = new Entity(nextToken.text(), nextToken.tagName());
            if (parsedText.contains(next)) next = getTokenInstanceFromSet(parsedText, next);

            // current.getAdjacentTokens().add(next);


            // Parse here special tags
            // parsedText = addTokenTypeToSet(parsedText, current);
            parsedText.add(current);
        }

        if (next != null) {
            // parsedText = addTokenTypeToSet(parsedText, next);
            parsedText.add(next);
        }

        // return parsedText;
        return processTokenValues(parsedText);
    } 

    public Set<Entity> processTokenValues(Set<Entity> parsedText) {
        Set<Entity> preprocessedParsedText = new HashSet<>(parsedText);
        for (Entity token : preprocessedParsedText) {
            preprocessedParsedText = addTokenTypeToSet(preprocessedParsedText, token);
        }

        //#region Debug output
        // for (TokenType token : preprocessedParsedText) {
        //     System.out.println(String.format("\n\nToken: (v: %s, t: %s, spec: %s, id: %s)\n", token.getValue(), token.getTokenType(), token.isSpecial(), token));

        //     for (TokenType adjToken : token.getAdjacentTokens()) {
        //         System.out.println(String.format("  AdjToken: (v: %s, t: %s, spec: %s, id: %s)", adjToken.getValue(), adjToken.getTokenType(), adjToken.isSpecial(), adjToken));
        //     }
        // }
        //#endregion

        return preprocessedParsedText;
    }


    @SafeVarargs
    public final Set<Entity> combineAll(String directoryPath, String... restDirectoryPath) throws IOException {

        Set<Entity> resultTokens = new HashSet<>();
        Set<String> filesInDirectory = ServerFile.listFilesServer(directoryPath, restDirectoryPath);

        for (String filename : filesInDirectory) {
            String rawJson = ServerFile.fileText(filename, directoryPath, restDirectoryPath);
            JSONArray json = new JSONArray(rawJson);

            Set<Entity> currentSet = this.fromJSON(json);
            resultTokens = this.combine(resultTokens, currentSet);
        }

        return resultTokens;
    }

    //#region From/To Json
    // Token structure:
    // {
    //     value: text, // Maybe with other tags
    //     tokenType: <Complainant>,
    //     adjacent: [val1, val2, ...],
    // }
    public JSONArray toJson(Set<Entity> parsedText) {
        JSONArray tokenJsonArray = new JSONArray();

        for (Entity token : parsedText) {
            tokenJsonArray.put(token.toJsonObject());
        }

        return tokenJsonArray;
    }

    public Set<Entity> fromJSON(JSONArray json) {
        Set<Entity> parsedText = new HashSet<>();

        for (Object o : json) {
            JSONObject tokenJson = (JSONObject) o;
            Entity token = Entity.fromJsonObject(tokenJson);

            parsedText = addTokenTypeToSet(parsedText, token);
        }

        return parsedText;
    }

    // Json structure:
    // [
    //     {
    //         "value": value1,
    //         "type": type1,
    //         "adjacent": [{...}, ..., {...}]
    //     },
    //     ...
    //     {
    //         "value": valueN,
    //         "type": typeN,
    //         "adjacent": [{...}, ..., {...}]
    //     }
    // ]
    public Graph<String> getGraphFromJson(JSONArray parsedTokensJson) {
        Set<Entity> parsedTokens = this.fromJSON(parsedTokensJson);

        return MarkedDataParser.getGraphFromTokenSet(parsedTokens);
    }
    //#endregion

}
