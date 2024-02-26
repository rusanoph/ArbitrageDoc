package ru.idr.arbitragestatistics.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.model.datastructure.StaticGraphs;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.Vertex;
import ru.idr.datamarkingeditor.model.entity.EntityMap;
import ru.idr.datamarkingeditor.model.token.ArbitrageToken;

@Component
public class ArbitrageTemplateSeeker {
    // [Уу]\s*[Сс]\s*[Тт]\s*[Аа]\s*[Нн]\s*[Оо]\s*[Вв]\s*[Ии]\s*[Лл]\s*:?|[Оо]\s*[Пп]\s*[Рр]\s*[Ее]\s*[Дд]\s*[Ее]\s*[Лл]\s*[Ии]\s*[Лл]\s*:?|[Пп]\s*[Оо]\s*[Сс]\s*[Тт]\s*[Аа]\s*[Нн]\s*[Оо]\s*[Вв]\s*[Ии]\s*[Лл]\s*:?|[Рр]\s*[Ее]\s*[Шш]\s*[Ии]\s*[Лл]\s*:?

    private List<String> textPartsRegex = new ArrayList<>();
    private List<Pattern> patterns = new ArrayList<>();

    private String text = "";
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;

        initialize();
    }

    public ArbitrageTemplateSeeker() {
        initialize();
    }

    private void initialize() {
        textPartsRegex.clear();
        textPartsRegex.add(wordToUniversalRegex("установил")+":?");
        textPartsRegex.add(wordToUniversalRegex("определил")+":?");
        textPartsRegex.add(wordToUniversalRegex("постановил")+":?");
        textPartsRegex.add(wordToUniversalRegex("решил")+":?");
        
        patterns.clear();
        patterns.add(Pattern.compile(textPartsRegex.get(0)));
        patterns.add(Pattern.compile(textPartsRegex.get(1)));
        patterns.add(Pattern.compile(textPartsRegex.get(2)));
        patterns.add(Pattern.compile(textPartsRegex.get(3)));
    }
    
    //#region Text Structure Methods
    public String getHeaderPart(String text) {
        // String regexSolution = "";
        
        String regexFound = wordToUniversalRegex("установил")+"\\s*:?\\s*\\n?";
        String regexDetermined = wordToUniversalRegex("определил")+"\\s*:?\\s*\\n?";
        String regexDecided = wordToUniversalRegex("постановил")+"\\s*:?\\s*\\n?";
        String regexSolution = wordToUniversalRegex("решил")+"\\s*:?\\s*\\n?";

        Matcher matcherFound = Pattern.compile(regexFound).matcher(text);
        Matcher matcherDetermined = Pattern.compile(regexDetermined).matcher(text);
        Matcher matcherDecided = Pattern.compile(regexDecided).matcher(text);
        Matcher matcherSolution = Pattern.compile(regexSolution).matcher(text);


        if (matcherFound.find()) {
            String[] textSplit = text.split(regexFound);
            return textSplit[0];
        }

        if (matcherDetermined.find()) {
            String[] textSplit = text.split(regexDetermined);
            return textSplit[0];
        }

        if (matcherDecided.find()) {
            String[] textSplit = text.split(regexDecided);
            return textSplit[0];
        }

        if (matcherSolution.find()) {
            String[] textSplit = text.split(regexSolution);
            return textSplit[0];
        }

        return text;
    }

    public String getAfterFoundPart(String text) {
        return getTextPartByIndex(text, 0);
    }

    public String getAfterDeterminedPart(String text) {
        return getTextPartByIndex(text, 1);
    }

    public String getAfterDecidedPart(String text) {
        return getTextPartByIndex(text, 2);
    }

    public String getAfterSolutionPart(String text) {
        return getTextPartByIndex(text, 3);
    }
    //#endregion

    //#region Text Special Structure Parsing
    public String colorizeTokens(String text, Matcher m) {
        for (String token : m.namedGroups().keySet()) {
            text = String.format("<span class='token %s'>%s</span> ", token, m.group(token));
        }

        return text; 
    }

    public String getComplainantAndDefendantPartGraph(String text) {
        text = getHeaderPart(text);
        text = DocumentProcessor.removeLineSeparator(text);

        Graph<String> cdpGraph = StaticGraphs.getCdpGraph();

        String result = "";

        Vertex<String> currentVertex = cdpGraph.getVertexByDepthValue(0, "Initial");

        boolean hasComplainant = false;
        boolean hasDefendant = false;
        while (cdpGraph.hasChildren(currentVertex)) {
            List<Vertex<String>> adjacentVertices = cdpGraph.getAdjacentVertices(currentVertex);

            // Needs to create something like Current Token Class
            String findText = "";
            String tokenTags = "";
            int minMatchIndexStart = Integer.MAX_VALUE;
            int minMatchIndexEnd = Integer.MAX_VALUE;

            boolean continueSearch = false;
            
            System.out.println();
            for (Vertex<String> vertex : adjacentVertices) {
                
                String regex = vertex.getValue().length() > 2 ? vertex.getValue() : RegExRepository.wrapWordAsRegex(vertex.getValue());
                Matcher m = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE).matcher(text.toLowerCase());
                
                System.out.println("Try regex: '" + regex + "'");
                if (m.find()) {
                    System.out.println(
                        String.format("'%s' (i: %d-%d)", regex, m.start(), m.end())
                    );

                    System.out.println(text.substring(0, Math.min(100, text.length())));

                    // minHasNamedGroups = !minHasNamedGroups && m.namedGroups().isEmpty();
                    
                    if (minMatchIndexStart > m.start()) {
                        if (m.namedGroups().keySet().contains(ArbitrageToken.Complainant.getLabel()) && hasComplainant) {
                            System.out.println("Has comp");
                            continue;
                        }

                        if (m.namedGroups().keySet().contains(ArbitrageToken.Defendant.getLabel()) && hasDefendant) {
                            System.out.println("Has def");
                            continue;
                        }

                        if (!m.namedGroups().isEmpty() && (!hasComplainant || !hasDefendant)) {
                            for (String token : m.namedGroups().keySet()) {
                                ArbitrageToken t = ArbitrageToken.valueOf(token);
                                
                                if (t == ArbitrageToken.Complainant) {
                                    if (!hasComplainant) hasComplainant = true; 
                                } else if (t == ArbitrageToken.Defendant) {
                                    if (!hasDefendant) hasDefendant = true;
                                }
                            }
                        }

                        minMatchIndexStart = m.start();
                        minMatchIndexEnd = m.end();

                        currentVertex = vertex;

                        continueSearch = true;

                    } else continue;

                    findText = DocumentProcessor.removeLineSeparator(m.group());
                    if (m.groupCount() > 0) {
                        if (!m.namedGroups().isEmpty()) {
                            tokenTags = "Tags: " + String.join(", ", m.namedGroups().keySet());

                            findText = colorizeTokens(findText, m);
                        }
                    }

                }
            }
            
            if (!continueSearch) break;
            
            String startsWithSpecialOrSpace = "^[:\\s,;!.-]+";
            text = text
                .substring(
                    Math.min(minMatchIndexEnd, text.length())
                ).replaceFirst(startsWithSpecialOrSpace, "");

            result += String.format("%s (i: %d-%d; %s) ⟶ \n", findText, minMatchIndexStart, minMatchIndexEnd, tokenTags);
            // result += String.format("%s %s ⟶ \n", findText, tokenTags);
        }

        return "Result:\n " + result;
    }

    public JSONObject parseEntities(String text, String rawJson) {
        JSONObject json = new JSONObject(rawJson);
        
        return this.parseEntities(text, json);
    }

    public JSONObject parseEntities(String text, JSONObject json) {
        JSONObject foundEntities = new JSONObject();

        EntityMap entitiesModel = new EntityMap(json);

        


        return foundEntities;
    }

    //#endregion


    //#region Other
    private String getTextPartByIndex(String text, int index) {
        Matcher matcher = patterns.get(index).matcher(text);
        
        if (matcher.find()) {
            String[] textSplit = text.split(textPartsRegex.get(index));
            text = textSplit[textSplit.length - 1];

            for (int i = index + 1; i < patterns.size(); i++) {
                if (patterns.get(i).matcher(text).find()) {
                    textSplit = text.split(textPartsRegex.get(i));
                    text = textSplit[0];

                    break;
                }
            }

            return text;
        }

        return "";
    }

    // Convert 'word' to '[Ww]\\s*[Oo]\\s*[Rr]\\s*[Dd]\\s*'
    private String wordToUniversalRegex(String word) {
        String regex = "";

        for (char c : word.toCharArray()) {
            String cStr = c + "";
            regex += String.format("[%s%s]\\s*", cStr.toUpperCase(), cStr.toLowerCase());
        }

        return regex;
    }

    
    //#endregion
}
