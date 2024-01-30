package ru.idr.arbitragestatistics.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.model.datastructure.StaticGraphs;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.Vertex;

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
    public String getComplainantAndDefendantPartGraph(String text) {
        text = getHeaderPart(text);
        text = DocumentProcessor.removeLineSeparator(text);

        Graph<String> cdpGraph = StaticGraphs.getCdpGraph();

        String result = "";

        Vertex<String> currentVertex = cdpGraph.getVertexByDepthValue(0, "Initial");


        while (cdpGraph.hasChildren(currentVertex)) {
            List<Vertex<String>> adjacentVertices = cdpGraph.getAdjacentVertices(currentVertex);

            int minMatchIndex = Integer.MAX_VALUE;

            boolean continueSearch = false;
            for (Vertex<String> vertex : adjacentVertices) {
                
                System.out.println(vertex.getValue());

                String regex = vertex.getValue().length() > 1 ? vertex.getValue() : RegExRepository.wrapWordAsRegex(vertex.getValue());
                Matcher m = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE).matcher(text.toLowerCase());

                if (m.find()) {
                    if (m.start() > 2 && vertex.getDepth() > 1) continue;

                    if (minMatchIndex > m.start()) {
                        minMatchIndex = m.start();

                        currentVertex = vertex;
                        continueSearch = true;

                    } else continue;

                    text = text.substring(m.end()).trim();

                    String tokenTags = "";
                    String findText = DocumentProcessor.removeLineSeparator(m.group());
                    if (m.groupCount() > 0) {
                        if (!m.namedGroups().isEmpty()) {
                            tokenTags = "Tags: " + String.join(", ", m.namedGroups().keySet());

                            for (String token : m.namedGroups().keySet()) {
                                String tokenType = "";
                                findText = findText.replace(m.group(token) , "<span class='token "+tokenType+"'>" + m.group(token) + "</span> ");
                            }
                        }
                    }

                    result += String.format("%s (i: %d-%d; %s) ⟶ \n", findText, m.start(), m.end(), tokenTags);
                    // result += String.format("%s %s ⟶ \n", findText, tokenTags);
                    
                    
                }

            }

            if (!continueSearch) break;
        }

        return "Result:\n " + result;
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
