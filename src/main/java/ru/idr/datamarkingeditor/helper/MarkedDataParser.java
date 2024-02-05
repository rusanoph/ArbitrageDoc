package ru.idr.datamarkingeditor.helper;


import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import ru.idr.datamarkingeditor.model.TokenType;

@Component
public class MarkedDataParser {

    // Token structure:
    // {
    //     value: text, // Maybe with other tags
    //     tokenType: <Complainant>,
    //     adjacent: [val1, val2, ...],
    // }
    public JSONArray parseAsJson(String text) {
        JSONArray tokenJsonArray = new JSONArray();

        for (TokenType token : this.parse(text)) {
            tokenJsonArray.put(token.toJsonObject());
        }

        return tokenJsonArray;
    }
 
    private Set<TokenType> parse(String text) {
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
}
