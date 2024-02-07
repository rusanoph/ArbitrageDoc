package ru.idr.datamarkingeditor.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.arbitragestatistics.helper.regex.RegExBuilder;
import ru.idr.arbitragestatistics.model.arbitrage.ArbitrageToken;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.Vertex;
import ru.idr.datamarkingeditor.model.TokenType;

@Component
public class MarkedDataParser {

    private RegExBuilder rb;

    public MarkedDataParser() {
        this.rb = new RegExBuilder();
    }

    //#region From/To Json
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

    public static Set<TokenType> fromJSON(JSONArray json) {
        Set<TokenType> parsedText = new HashSet<>();

        for (Object token : json) {
            JSONObject tokenJson = (JSONObject) token;
            parsedText.add(TokenType.fromJsonObject(tokenJson));
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
    public static Graph<String> getGraphFromJson(JSONArray parsedTokensJson) {
        Set<TokenType> parsedTokens = MarkedDataParser.fromJSON(parsedTokensJson);

        return MarkedDataParser.getGraphFromTokenSet(parsedTokens);
    }

    public static Graph<String> getGraphFromTokenSet(Set<TokenType> parsedTokens) {
        Graph<String> graph = new Graph<>();

        for (TokenType token : parsedTokens) {

            Vertex<String> v;
            if (graph.getVertexByDepthValue(0, token.getValue()) == null) {
                v = new Vertex<String>(token.getValue(), token.getTokenType());
            } else {
                v = graph.getVertexByDepthValue(0, token.getValue());
            }
            graph.addVertex(v);

            for (TokenType adjToken : token.getAdjacentTokens()) {
                Vertex<String> u;
                if (graph.getVertexByDepthValue(0, adjToken.getValue()) == null) {
                    u = new Vertex<String>(adjToken.getValue(), token.getTokenType());
                } else {
                    u = graph.getVertexByDepthValue(0, adjToken.getValue());
                }

                graph.addOrEdge(v, u);
            }

            System.out.println(String.format("Token: (v: %s, t: %s, spec: %s)", token.getValue(), token.getTokenType(), token.isSpecial()));
            System.out.println(String.format("Vertex: (v: %s, t: %s, spec: %s)", v.getValue(), v.getType(), v.isSpecial()));
        }

        return graph;
    }

    //#endregion

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


            // Parse here special tags
            parsedText.add(current);
        }

        if (next != null) {
            parsedText.add(next);
        }

        return parsedText;
    } 

    public Set<TokenType> combine(Set<TokenType> tokens, Set<TokenType> otherTokens) {
        
        for (TokenType otherToken : otherTokens) {

            // Extract to a separate method
            // Make uniqueness and token preprocessing bulletproof through all the parser
            // Even file, json, combination or any other structure or operation 
            // is on incorrect data parser must return correct object.
            if (!tokens.contains(otherToken)) {
                tokens.add(otherToken);
            } else {
                for (TokenType token : tokens) {
                    if (token.equals(otherToken)) {

                        if (otherToken.getTokenType().equals(ArbitrageToken.Keyword.getLabel()) && 
                            token.getTokenType().equals(otherToken.getTokenType())) {

                            token.setValue(processKeyword(token, otherToken));
                        }

                        token.getAdjacentTokens()
                        .addAll(otherToken.getAdjacentTokens());
                        break;
                    }
                }
            }
        }

        return tokens;
    }

    @SafeVarargs
    public final Set<TokenType> combineAll(String directoryPath, String... restDirectoryPath) throws IOException {

        Set<TokenType> resultTokens = new HashSet<>();
        Set<String> filesInDirectory = ServerFile.listFilesServer(directoryPath, restDirectoryPath);

        for (String filename : filesInDirectory) {
            String rawJson = ServerFile.fileText(filename, directoryPath, restDirectoryPath);
            JSONArray json = new JSONArray(rawJson);

            Set<TokenType> currentSet = MarkedDataParser.fromJSON(json);
            resultTokens = this.combine(resultTokens, currentSet);
        }

        return resultTokens;
    }


    // ??? to Set
    private Set<TokenType> processSpecialArbitrageTokens(Set<TokenType> parsedToken) {

        for (TokenType token : parsedToken) {
            
            String type = token.getTokenType();
            if (isSpecial(type)) {
                Set<TokenType> adjTokens = token.getAdjacentTokens();

                if (type == "Word") {
                    token.setValue(processWord(token, adjTokens));
                }


            }
        }

        return parsedToken;
    }

    private String processPerson() {
        return "";
    }

    private String processComplainant() { return processPerson(); }
    private String processDefendant() { return processPerson(); }
    private String processThirdParty() { return processPerson(); }
    private String processCompetitionManager() { return processPerson(); }
    private String processFinancialManager() { return processPerson(); }

    private String processKeyword(TokenType token, TokenType otherToken) {
        String t = "(" + token.getValue() + ")";
        String ot = "(" + token.getValue() + ")";

        if (t.startsWith("(?<Keyword>") && t.endsWith(")") &&
            ot.startsWith("(?<Keyword>") && ot.endsWith(")")) {
            
            // Length of string
            // "(?<Keyword>".length() -> 11
            t = ot.substring(11, ot.length() - 1);
            ot = t.substring(11, t.length() - 1);

        }
        return "(?<Keyword>" + String.join("|", ot, t) + ")";
    }

    private String processWord(TokenType vertex, Set<TokenType> adjTokens) {
        return "";
    }

    private boolean isSpecial(String type) {
        return TokenType.SPECIAL_TOKEN_TYPE.contains(type);
    }
}
