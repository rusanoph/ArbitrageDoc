package ru.idr.arbitragestatistics.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.model.datastructure.StaticTrees;
import ru.idr.arbitragestatistics.util.HTMLWrapper;
import ru.idr.arbitragestatistics.util.datastructure.Tree;

@Component
public class ArbitrageTemplateSeeker {
    private final Logger logger = LoggerFactory.getLogger(ArbitrageTemplateSeeker.class);
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
        
        String regexFound = wordToUniversalRegex("установил")+"\\s*:?\\s*\\n";
        String regexDetermined = wordToUniversalRegex("определил")+"\\s*:?\\s*\\n";
        String regexDecided = wordToUniversalRegex("постановил")+"\\s*:?\\s*\\n";
        String regexSolution = wordToUniversalRegex("решил")+"\\s*:?\\s*\\n";

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
        return "Not implemented algorithm";
    }

    // Tree's children number grows too fast because of path repeations
    public String getComplainantAndDefendantPartTree(String text) {
        // cdp -> Complainant Defendant Part
        Tree<Pattern> cdpTree = (new Tree<Pattern>(null))
        .appendChild(StaticTrees.getCdpTree1())     // ознакомившись
        .appendChild(StaticTrees.getCdpTree2())     // возобновлено 
        .appendChild(StaticTrees.getCdpTree3())     // рассмотрев | рассматривает | рассмотрел
        ;
        cdpTree.recomputeDepthDFS();

        List<String> textSplit = List.of(text.split("\\s+"));
        text = String.join(" " , textSplit).toLowerCase();

        String path = "";
        while (cdpTree.getChildren().size() != 0) {
            boolean continueSearch = false;

            for (var childCdpTree : cdpTree.getChildren()) {
                Matcher m = childCdpTree.getValue().matcher(text);

                if (m.find() && (cdpTree.getDepth() == 0 || m.start() == 0)) {
                    int sliceIndex = m.end();
                    text = text.substring(sliceIndex).trim();
                    path += "(" + childCdpTree.getValue().pattern() + "; depth=" + childCdpTree.getDepth() +") ⟶ ";

                    cdpTree = childCdpTree;

                    continueSearch = true;
                    break;
                }
            }

            if (!continueSearch) {
                break;
            }
        }

        if (cdpTree.hasAction()) {
            text = cdpTree.doAction(text);
        }

        var treeToPrint = (new Tree<Pattern>(Pattern.compile("start")))
        .appendChild(StaticTrees.getCdpTree1())
        .appendChild(StaticTrees.getCdpTree1())
        .appendChild(StaticTrees.getCdpTree1());
        treeToPrint.recomputeDepthDFS();

        return String.format("Tree path: %s\n%s\n\n\n%s", HTMLWrapper.tag("span", path, "sub-accent"), text, treeToPrint.toString());
    }


    // Incomplete search algorithm
    public String getComplainantAndDefendantPartRegex(String text) {
        String result = "Истец и ответчик не определены";

        Pattern pattern = Pattern.compile(RegExRepository.regexComplainantAndDefendat_v2, Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(text.toLowerCase());

        if (matcher.find()) {
            String complainant = matcher.group(1);
            String defendant = matcher.group(2);

            String complainantOgrn = "Не определен";
            String defendantOgrn = "Не определен";


            // logger.debug("\nmatch Истец и Ответчик: {}", matcher.group(0));

            Matcher complainantOgrnMatcher = Pattern.compile(RegExRepository.regexOgrn + "|" + RegExRepository.regexOgrnip).matcher(complainant);
            Matcher defendantOgrnMatcher = Pattern.compile(RegExRepository.regexOgrn + "|" + RegExRepository.regexOgrnip).matcher(defendant);


            if (complainantOgrnMatcher.find()) {
                complainantOgrn = complainantOgrnMatcher.group(0);
                // logger.debug("\n\nОГРН Истца: {}", complainantOgrn);
            }

            if (defendantOgrnMatcher.find()) {
                defendantOgrn = defendantOgrnMatcher.group(0);
                // logger.debug("\n\nОГРН ответчика: {}", defendantOgrn);
            }

            // result = result.replaceAll(, result)
            // result = result.replaceAll(RegExRepository.regexComplainanDefendatStart, "");
            // result = result.replaceAll(RegExRepository.regexComplainanDefendatEnd, "");

            // var resultSplit = result.split("");

            result = String.format("%s %s<br><br>%s %s<br><br>%s %s<br><br>%s %s<br><br>%s %s<br><br>",
                wrapAccent("Найдено:"), matcher.group(0),
                wrapAccent("Истец"), complainant,
                wrapAccent("ОГРН истца"), complainantOgrn,
                wrapAccent("Ответчик"), defendant,
                wrapAccent("ОГРН ответчика"), defendantOgrn
            );
        }

        return result;
    }

    private String wrapAccent(String str) {
        return String.format("<span class='accent'>%s</span>", str);
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
