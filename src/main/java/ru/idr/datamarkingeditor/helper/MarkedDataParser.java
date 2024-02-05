package ru.idr.datamarkingeditor.helper;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import ru.idr.datamarkingeditor.model.TokenType;
import ru.idr.arbitragestatistics.helper.ServerFile;

@Component
public class MarkedDataParser {

    // Token structure:
    // {
    //     value: text, // Maybe with other tags
    //     tokenType: <Complainant>,
    //     adjacent: [val1, val2, ...],
    // }
    public JSONArray toJson(Set<TokenType> parsedText) {
        JSONArray tokenJsonArray = new JSONArray();

        for (TokenType token : parsedText) {
            tokenJsonArray.put(token.toJsonObject());
        }

        return tokenJsonArray;
    }

    public Set<TokenType> fromJSON(JSONArray json) {
        Set<TokenType> parsedText = new HashSet<>();

        for (Object token : json) {
            JSONObject tokenJson = (JSONObject) token;
            parsedText.add(TokenType.fromJsonObject(tokenJson));
        }

        return parsedText;
    }

    public Set<TokenType> combine(Set<TokenType> parserResult1, Set<TokenType> parserResult2) {
        parserResult1.addAll(parserResult2);

        return parserResult1;
    }
 
    public Set<TokenType> parse(String text) {
        Set<TokenType> parsedText = new HashSet<>();
        
        text = "<root>" + text + "</root>";
        Document document = Jsoup.parse(text, "", org.jsoup.parser.Parser.xmlParser());
        Element root = document.selectFirst("root");

        TokenType current = null; 
        TokenType next = null;
        for (int i = 0; i < root.childrenSize() - 1; i++) {
            Element currentToken = root.children().get(i);
            Element nextToken = root.children().get(i + 1);

            current = new TokenType(currentToken.text(), currentToken.tagName());
            next = new TokenType(nextToken.text(), nextToken.tagName());
            current.getAdjacentTokens().add(next);

            parsedText.add(current);
        }

        if (next != null) {
            parsedText.add(next);
        }

        return parsedText;
    } 


    // Bug: recognizes Tokens as different only if they has different referencies.
    // Don't combines adjacent tokens.
    @SafeVarargs
    public final Set<TokenType> combineAll(String directoryPath, String... restDirectoryPath) throws IOException {

        Set<TokenType> resultTokens = new HashSet<>();
        Set<String> filesInDirectory = ServerFile.listFilesServer(directoryPath, restDirectoryPath);

        for (String filename : filesInDirectory) {
            String rawJson = ServerFile.fileText(filename, directoryPath, restDirectoryPath);
            JSONArray json = new JSONArray(rawJson);

            resultTokens.addAll(this.fromJSON(json));
        }

        return resultTokens;
    }
}
