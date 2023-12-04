package ru.idr.arbitragestatistics.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ArbitrageTemplateSeeker {
    // [Уу]\s*[Сс]\s*[Тт]\s*[Аа]\s*[Нн]\s*[Оо]\s*[Вв]\s*[Ии]\s*[Лл]\s*:?|[Оо]\s*[Пп]\s*[Рр]\s*[Ее]\s*[Дд]\s*[Ее]\s*[Лл]\s*[Ии]\s*[Лл]\s*:?|[Пп]\s*[Оо]\s*[Сс]\s*[Тт]\s*[Аа]\s*[Нн]\s*[Оо]\s*[Вв]\s*[Ии]\s*[Лл]\s*:?|[Рр]\s*[Ее]\s*[Шш]\s*[Ии]\s*[Лл]\s*:?

    private List<String> textPartsRegex = new ArrayList<>();
    private List<Matcher> matchers = new ArrayList<>();

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
        
        matchers.clear();
        matchers.add(Pattern.compile(textPartsRegex.get(0)).matcher(text));
        matchers.add(Pattern.compile(textPartsRegex.get(1)).matcher(text));
        matchers.add(Pattern.compile(textPartsRegex.get(2)).matcher(text));
        matchers.add(Pattern.compile(textPartsRegex.get(3)).matcher(text));
    }
    
    //#region Text Structure Methods
    public String getHeaderPart(String text) {
        // String regexSolution = "";
        
        String regexFound = wordToUniversalRegex("установил")+":?";
        String regexDetermined = wordToUniversalRegex("определил")+":?";
        String regexDecided = wordToUniversalRegex("постановил")+":?";
        String regexSolution = wordToUniversalRegex("решил")+":?";

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

    //#region Text Special Structure 
    public String getComplainantAndDefendantPart(String text) {
        String result = "";

        Pattern pattern = Pattern.compile(RegExRepository.regexComplainantAndDefendat);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            result = matcher.group();
        }

        return result;
    }

    //#endregion


    //#region Other
    private String getTextPartByIndex(String text, int index) {
        Matcher matcherFound = matchers.get(index);
        
        if (matcherFound.find()) {
            String[] textSplit = text.split(textPartsRegex.get(index));
            text = textSplit[textSplit.length - 1];

            for (int i = index + 1; i < matchers.size(); i++) {
                if (matchers.get(i).find()) {
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
