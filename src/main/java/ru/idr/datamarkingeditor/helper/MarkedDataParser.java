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
import ru.idr.arbitragestatistics.model.arbitrage.ArbitrageToken;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.Vertex;
import ru.idr.datamarkingeditor.model.CommonToken;
import ru.idr.datamarkingeditor.model.TokenType;

@Component
public class MarkedDataParser {
    // * Нужно отдельно предобрабатывать каждый тип токена
    // * Отдельно склеивать два токена одинакового типа
    // * Нужно провести рефакторинг и очистить код от лишней логики
    // * Требуется покрыть тестами методы


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

    public Set<TokenType> fromJSON(JSONArray json) {
        Set<TokenType> parsedText = new HashSet<>();

        for (Object o : json) {
            JSONObject tokenJson = (JSONObject) o;
            TokenType token = TokenType.fromJsonObject(tokenJson);

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
        Set<TokenType> parsedTokens = this.fromJSON(parsedTokensJson);

        return MarkedDataParser.getGraphFromTokenSet(parsedTokens);
    }
    //#endregion

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
            
            System.out.println();
            System.out.println(String.format("Token: (v: %s, t: %s, spec: %s, id: %s)", token.getValue(), token.getTokenType(), token.isSpecial(), token));
            for (TokenType adjToken : token.getAdjacentTokens()) {
                System.out.println(String.format("\tAdjToken: (v: %s, t: %s, spec: %s, id: %s)", adjToken.getValue(), adjToken.getTokenType(), adjToken.isSpecial(), adjToken));

                Vertex<String> u;
                if (graph.getVertexByDepthValue(0, adjToken.getValue()) == null) {
                    u = new Vertex<String>(adjToken.getValue(), adjToken.getTokenType());
                } else {
                    u = graph.getVertexByDepthValue(0, adjToken.getValue());
                }
                
                graph.addOrEdge(v, u);
            }
            System.out.println(String.format("Vertex: (v: %s, t: %s, spec: %s)", v.getValue(), v.getType(), v.isSpecial()));
            
        }
        
        return graph;
    }

    private Set<TokenType> addTokenTypeToSet(Set<TokenType> tokens, TokenType otherToken) {

        if (tokens.contains(otherToken)) {
            for (TokenType token : tokens) {
                if (token.equals(otherToken)) {

                    // === Arbitrage === 
                    if (token.bothOfType(otherToken, ArbitrageToken.Complainant)) {
                        token.setValue(processComplainant(token, otherToken));
                    }

                    if (token.bothOfType(otherToken, ArbitrageToken.Defendant)) {
                        token.setValue(processDefendant(token, otherToken));                                                    
                    }

                    if (token.bothOfType(otherToken, ArbitrageToken.ThirdParty)) {
                        token.setValue(processThirdParty(token, otherToken));                                                
                    }

                    if (token.bothOfType(otherToken, ArbitrageToken.CompetitionManager)) {
                        token.setValue(processCompetitionManager(token, otherToken));                                                
                    }

                    if (token.bothOfType(otherToken, ArbitrageToken.FinancialManager)) {
                        token.setValue(processFinancialManager(token, otherToken));                                                
                    }


                    if (token.bothOfType(otherToken, ArbitrageToken.Keyword)) {
                        token.setValue(processKeyword(token, otherToken));
                    }

                    // === Common ===
                    if (token.bothOfType(otherToken, CommonToken.Word)) {
                        token.setValue(processWord(token));
                    }

                    // Merge adjacent tokens
                    token.getAdjacentTokens()
                    .addAll(otherToken.getAdjacentTokens());
                    break;
                }
            }
        } else {
            tokens.add(otherToken);
        }

        return tokens;
    }

    private TokenType getTokenInstanceFromSet(Set<TokenType> container, TokenType patternToken) {
        if (container.contains(patternToken)) {
            for (TokenType token : container) {
                if (token.equals(patternToken)) return token;
            }
        }
        return patternToken; 
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
            if (parsedText.contains(current)) current = getTokenInstanceFromSet(parsedText, current);

            next = new TokenType(nextToken.text(), nextToken.tagName());
            if (parsedText.contains(next)) next = getTokenInstanceFromSet(parsedText, next);

            current.getAdjacentTokens().add(next);


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

    public Set<TokenType> processTokenValues(Set<TokenType> parsedText) {
        Set<TokenType> preprocessedParsedText = new HashSet<>(parsedText);
        for (TokenType token : preprocessedParsedText) {
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

    public Set<TokenType> combine(Set<TokenType> tokens, Set<TokenType> otherTokens) {
        
        for (TokenType otherToken : otherTokens) {
            tokens = addTokenTypeToSet(tokens, otherToken);
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

            Set<TokenType> currentSet = this.fromJSON(json);
            resultTokens = this.combine(resultTokens, currentSet);
        }

        return resultTokens;
    }


    //#region Token Value processing 

    public String processTokenValue(TokenType token) {
        // Method, that recognizes type of token and process it with corresponding method

        return "";
    }

    public String processTokenValue(TokenType token, TokenType otherToken) {
        return "";
    }

    public String processPerson(TokenType token) {
        String tokenValue = token.getValue()
            .replaceAll("\\(", "\\\\(")
            .replaceAll("\\)", "\\\\)");

        //(?<Complainant>.+?)(?=( #regex# ))
        String patternStart = "(?<"+token.getTokenType()+">.+?)(?=(";
        String patternEnd = "))";

        boolean tokenHasPersonNamedRegexValue = token.hasNamedRegexValue(ArbitrageToken.Complainant) ||
            token.hasNamedRegexValue(ArbitrageToken.Defendant) ||
            token.hasNamedRegexValue(ArbitrageToken.ThirdParty) ||
            token.hasNamedRegexValue(ArbitrageToken.FinancialManager) ||
            token.hasNamedRegexValue(ArbitrageToken.CompetitionManager);

        Set<String> tokenAdjWord;
        if (tokenHasPersonNamedRegexValue) {
            String tokenRegex = tokenValue.substring(patternStart.length(), tokenValue.length() - patternEnd.length());
            tokenAdjWord = Set.of(tokenRegex.split("|"));

        } else {
            tokenAdjWord = token.getAdjacentTokens().stream()
                .filter(t -> t.ofType(CommonToken.Word))
                .map(TokenType::getValue)
                .collect(Collectors.toSet());
        }

        return patternStart + String.join("|", tokenAdjWord) + patternEnd;
    }

    public String mergePerson(TokenType token, TokenType otherToken) {
        return "";
    }

    public String processPerson(TokenType token, TokenType otherToken) {

        String tokenValue = token.getValue()
            .replaceAll("\\(", "\\\\(")
            .replaceAll("\\)", "\\\\)");
        String otherTokenValue = otherToken.getValue()
            .replaceAll("\\(", "\\\\(")
            .replaceAll("\\)", "\\\\)");

        //(?<Complainant>.+?)(?=( #regex# ))
        String patternStart = "(?<"+token.getTokenType()+">.+?)(?=(";
        String patternEnd = "))";

        boolean tokenHasPersonNamedRegexValue = token.hasNamedRegexValue(ArbitrageToken.Complainant) ||
            token.hasNamedRegexValue(ArbitrageToken.Defendant) ||
            token.hasNamedRegexValue(ArbitrageToken.ThirdParty) ||
            token.hasNamedRegexValue(ArbitrageToken.FinancialManager) ||
            token.hasNamedRegexValue(ArbitrageToken.CompetitionManager);

        boolean otherTokenHasPersonNamedRegexValue = otherToken.hasNamedRegexValue(ArbitrageToken.Complainant) ||
            otherToken.hasNamedRegexValue(ArbitrageToken.Defendant) ||
            otherToken.hasNamedRegexValue(ArbitrageToken.ThirdParty) ||
            otherToken.hasNamedRegexValue(ArbitrageToken.FinancialManager) ||
            otherToken.hasNamedRegexValue(ArbitrageToken.CompetitionManager);

        Set<String> tokenAdjWord;
        if (tokenHasPersonNamedRegexValue) {
            String tokenRegex = tokenValue.substring(patternStart.length(), tokenValue.length() - patternEnd.length());
            tokenAdjWord = Set.of(tokenRegex.split("|"));

        } else {
            tokenAdjWord = token.getAdjacentTokens().stream()
                .filter(t -> t.ofType(CommonToken.Word))
                .map(TokenType::getValue)
                .collect(Collectors.toSet());
        }

        Set<String> otherTokenAdjWord;
        if (otherTokenHasPersonNamedRegexValue) {
            String otherTokenRegex = otherTokenValue.substring(patternStart.length(), otherTokenValue.length() - patternEnd.length());
            otherTokenAdjWord = Set.of(otherTokenRegex.split("|"));
        } else {
            otherTokenAdjWord = otherToken.getAdjacentTokens().stream()
                .filter(t -> t.ofType(CommonToken.Word))
                .map(TokenType::getValue)
                .collect(Collectors.toSet());
        }

        otherTokenAdjWord.addAll(tokenAdjWord);

        return patternStart + String.join("|", otherTokenAdjWord) + patternEnd;
    }

    private String processComplainant(TokenType token, TokenType otherToken) { return processPerson(token, otherToken); }
    private String processDefendant(TokenType token, TokenType otherToken) { return processPerson(token, otherToken); }
    private String processThirdParty(TokenType token, TokenType otherToken) { return processPerson(token, otherToken); }
    private String processCompetitionManager(TokenType token, TokenType otherToken) { return processPerson(token, otherToken); }
    private String processFinancialManager(TokenType token, TokenType otherToken) { return processPerson(token, otherToken); }

    public String processKeyword(TokenType token, TokenType otherToken) {
        String tokenValue = token.getValue()
            .replaceAll("\\(", "\\\\(")
            .replaceAll("\\)", "\\\\)");
        String otherTokenValue = otherToken.getValue()
            .replaceAll("\\(", "\\\\(")
            .replaceAll("\\)", "\\\\)");

        // Length of string
        // "(?<Keyword>".length() -> 11
        if (token.hasNamedRegexValue(ArbitrageToken.Keyword)) {
            tokenValue = tokenValue.substring(11, tokenValue.length() - 1);
        } else {
            tokenValue = "(" + tokenValue + ")";
        }

        if (otherToken.hasNamedRegexValue(ArbitrageToken.Keyword)) {
            otherTokenValue = otherTokenValue.substring(11, otherTokenValue.length() - 1);
        } else {
            otherTokenValue = "(" + otherTokenValue + ")";
        }

        return "(?<Keyword>" + String.join("|", otherTokenValue, tokenValue) + ")";
    }

    private String processWord(TokenType token) {

        String tokenValue = token.getValue().toLowerCase()
            .replaceAll("\\(", "\\\\(")
            .replaceAll("\\)", "\\\\)");
        
        // Words shorter or equal than 3 letters 
        if (tokenValue.length() <= 3) {
            tokenValue = "(^|\\s*)" + tokenValue + "($|\\s+)";
        }

        return tokenValue;
    }


    //#endregion
}
