package ru.idr.arbitragestatistics.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.demidko.aot.WordformMeaning;

import ru.idr.arbitragestatistics.model.TitleData;

public class DocumentProcessor {

    private static String regexCapital = "[А-Я]";
    private static String regexSpecialCharacters = "[^а-яА-Я0-9. ]";

    private static String regexMoneySumFull = "(\\s*\\d+)+\\s*(р\\.|руб\\.|рублей)\\s*((\\s*\\d+)+\\s*(к\\.|коп\\.|копеек))?";    
    private static String regexMoneySumComma = "(\\s*\\d+)+,\\s*(\\s*\\d+)+\\s*(р\\.|руб\\.|рублей)";
    private static String regexMoneySumComment = "(\\s*\\d+)+\\s*(\\([А-Яа-я ]*\\))?\\s*(р\\.|руб\\.|рублей)\\s*((\\s*\\d+)+\\s*(к\\.|коп\\.|копеек))?";

    private static String regexSpecial = "(\\s*\\d+)+\\s*(р\\.|руб\\.|рублей)\\s*((\\s*\\d+)+\\s*(к\\.|коп\\.|копеек))?|(\\s*\\d+)+,\\s*(\\s*\\d+)+\\s*(р\\.|руб\\.|рублей)|(\\s*\\d+)+\\s*(\\([А-Яа-я ]*\\))?\\s*(р\\.|руб\\.|рублей)\\s*((\\s*\\d+)+\\s*(к\\.|коп\\.|копеек))?";


    //#region Data

    public static Iterable<String> getMoneySum(String text) {

        List<String> moneySum = new ArrayList<>();

        final String regexString = regexMoneySumFull + "|" + regexMoneySumComma + "|" + regexMoneySumComment;

        Matcher matcher = Pattern.compile(regexString, Pattern.MULTILINE).matcher(text);

        while (matcher.find()) {
            moneySum.add(matcher.group().replaceAll("\\s+", " "));
        }

        return moneySum;
    }

    public static Map<String, TitleData> getTitleMap(String currentDir) throws IOException {

        Map<String, TitleData> titleMap = new HashMap<>();

        // Check all directories for existing files
        Set<String> dirs = ServerFile.listDirectoryServer(currentDir);
        if (dirs != null) {
            for (String dir : dirs) {
                // If there dir, then recursively search files inside of it
                // And merge two maps: result map and result of recursive call
                getTitleMap(dir).forEach((key, value) -> 
                    titleMap.merge(key, value, (v1, v2) -> {
                        v1.getFiles().addAll(v2.getFiles());
                        return new TitleData(
                            v1.getCount() + v2.getCount(), 
                            v1.getFiles()
                        );
                    }));
            }
        }

        // Add all file's titles to titleMap
        Set<String> files = ServerFile.listFilesServer(currentDir);
        if (files != null) {
                for (String file : files) {

                String title = getArbitrageTextTitle(ServerFile.fileText(currentDir, file), " ");

                // Add or Increment title data
                TitleData tmp;
                if (titleMap.containsKey(title)) {

                    tmp = titleMap.get(title); 
                    tmp.addFile(file);
                    tmp.setCount(tmp.getCount() + 1);

                } else {

                    tmp = new TitleData();
                    tmp.setCount(1);
                    tmp.addFile(file);
                    titleMap.put(title, tmp);
                
                }
            }
        }

        return titleMap;
    }
    
    public static String getArbitrageTextTitle(String text, String splitSymbol) {
        String arbitrageTitle = "Неизвестный заголовок";

        text = DocumentProcessor.removeLineSeparator(text);
        text = DocumentProcessor.removeSpecialCharacters(text);
        text = DocumentProcessor.removeSpaceBetweenWords(text);
        text = text.toLowerCase();

        String lemmatizeText = DocumentProcessor.lemmatizeText(text, splitSymbol);

        String[] textSplit = text.split(splitSymbol);
        String[] lemmatizeTextSplit = lemmatizeText.split(splitSymbol);

        int textLength = lemmatizeTextSplit.length;

        List<String> possibleTitlesStart = new ArrayList<>(List.of(
            "резолютивный",
            "определение",
            "постановление",
            "протокольный",
            "решение"
            // "судебный"
        ));

        // Find index of first word in title
        Integer titleIndexStart = -1;
        for (String titleStart : possibleTitlesStart) {
            for (int i = 0; i < textLength; i++) {
                if (lemmatizeTextSplit[i].trim().toLowerCase().equals(titleStart)) {
                    if (titleIndexStart == -1 || titleIndexStart > i) {
                        titleIndexStart = i;
                    }

                    break;
                }
            }
        }

        // If didn't find anything -> return
        if (titleIndexStart == -1) {
            return arbitrageTitle;
        }


        //Regexp end rules
        String[] regexpEndRules = {
            "дело",
            "деть",
            "г\\..*",
            "Г\\.(.*)?",
            "город",
            "а\\d+",
            "имя",
            "\\d{1,2}"
        };
        
        String patternTitleEndString = String.join("?|", regexpEndRules) + "?";
        Pattern patternTitleEnd = Pattern.compile(patternTitleEndString, Pattern.CASE_INSENSITIVE);

        String[] regexpInnerBeforeEndRules = {
            "\\s?по\\s"
        };

        String patternTitleInnerBeforeEndString = String.join("?|", regexpInnerBeforeEndRules) + "?";
        Pattern patternTitleInnerBeforeEnd = Pattern.compile(patternTitleInnerBeforeEndString, Pattern.CASE_INSENSITIVE);

        String[] regexpInnerAfterEndRules = {
            "\\s?к\\s|\\sк\\s?",
            "ст\\."
        };

        String patternTitleInnerAfterEndString = String.join("?|", regexpInnerAfterEndRules) + "?";
        Pattern patternTitleInnerAfterEnd = Pattern.compile(patternTitleInnerAfterEndString, Pattern.CASE_INSENSITIVE);

        // Find index of last word in title
        Integer titleIndexEnd = -1;
        for (int i = titleIndexStart + 1; i < textLength - 1; i++) {
            if (lemmatizeTextSplit[i].isBlank()) {
                continue;
            }

            Matcher currentWord = patternTitleEnd.matcher(lemmatizeTextSplit[i].trim());

            if (currentWord.find()) {
                Matcher wordBefore = patternTitleInnerBeforeEnd.matcher(lemmatizeTextSplit[i - 1].trim());
                if (wordBefore.find()) {
                    continue;
                }
                
                Matcher wordAfter = patternTitleInnerAfterEnd.matcher(lemmatizeTextSplit[i + 1].trim());
                if (wordAfter.find()) {
                    continue;
                }

                if (titleIndexEnd == -1 || titleIndexEnd > i) {
                    titleIndexEnd = i;
                }
            }
        }
        
        if (titleIndexStart != -1 && titleIndexEnd != -1) {
            String[] arbitrageTitleSplit = Arrays.copyOfRange(textSplit, titleIndexStart, titleIndexEnd);

            arbitrageTitle = "";
            for (String titleWord : arbitrageTitleSplit) {
                if (!titleWord.isBlank()) {
                    arbitrageTitle += titleWord.trim() + " ";
                }
            }
        } 
        
        return arbitrageTitle;
    }

    //#endregion

    //#region Statistic methods

    private static Iterable<WordformMeaning> getValidWordformMeaningFromText(String text, String splitSymbol) {

        var wordformMeaning = new ArrayList<WordformMeaning>();
        
        String[] textSplit = text.split(splitSymbol);
        for (var word : textSplit) {
            var meanings = WordformMeaning.lookupForMeanings(word);

            if (meanings.size() < 1) {
                continue;
            }

            var meaning = meanings.get(0);
            wordformMeaning.add(meaning);
        }

        return wordformMeaning; 
    }

    private static Iterable<String> getInvalidWordformMeaningFromText(String text, String splitSymbol) {
        var words = new ArrayList<String>();
        
        String[] textSplit = text.split(splitSymbol);
        for (var word : textSplit) {
            var meanings = WordformMeaning.lookupForMeanings(word);

            if (meanings.size() < 1) {
                words.add(word);
            }
        }

        return words;
    }

    public static Iterable<String> getLemmasFromText(String text, String splitSymbol, boolean valid) {
        var lemmas = new ArrayList<String>();

        if (valid) {
            var lemmasList = getValidWordformMeaningFromText(text, splitSymbol);

            for (var wordformMeaning : lemmasList) {
                lemmas.add(wordformMeaning.getLemma().toString());
            }
        } else {
            var lemmasList = getInvalidWordformMeaningFromText(text, splitSymbol);

            for (var wordformMeaning : lemmasList) {
                lemmas.add(wordformMeaning);
            }
        }

        return lemmas;
    }

    public static Map<String, Integer> getWordStatistic(String text, String splitSymbol) {
        Map<String, Integer> wordStatistic = new HashMap<>();

        String[] textSplit = text.split(splitSymbol);
        for (String word : textSplit) {
            Integer currentWordValue = (Integer) Objects.requireNonNullElse(wordStatistic.get(word), 0);
            wordStatistic.put(word, currentWordValue + 1);
        }
        
        return wordStatistic;
    }

    public static Integer getWordCount(String text, String splitSymbol) {
        String[] textSplit = text.split(splitSymbol);
        return textSplit.length;
    }
    
    //#endregion

    //#region Preprocessing

    public static String lemmatizeText(String text, String splitSymbol) {

        var lemmaList = new ArrayList<String>();
        
        String[] textSplit = text.split(splitSymbol);
        for (var word : textSplit) {
            var meanings = WordformMeaning.lookupForMeanings(word);

            if (meanings.size() < 1) {
                lemmaList.add(word);
                continue;
            }

            var meaning = meanings.get(0);
            lemmaList.add(meaning.getLemma().toString());
        }

        return String.join(splitSymbol, lemmaList);
    }

    public static String removeSpecialCharacters(String text) {
        return text.replaceAll(regexSpecialCharacters, "");
    }

    public static String removeLineSeparator(String text) {
        String separator = "\n|\r";
        return text.replaceAll(separator, " ");
    }

    // Work Incorrect
    public static String removeSpaceBetweenWords(String text) {

        text = text.replaceAll("\n|\r", " ");
        text = toCapitalText(text);

        String[] textSplit = text.split("\\s(?="+regexCapital+")");

        return String.join(" ", textSplit);
    }

    public static String toCapitalWord(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static String toCapitalText(String text) {
        String[] textSplit = text.split(" ");

        String newText = "";

        String previousWord = "";
        for (String currentWord : textSplit) {
            // If previous word ends with capital char, then no space, 
            // else has space after current word
            boolean hasNoSpace = previousWord.matches("\\.*"+regexCapital+"$|\\.*"+regexCapital+" ") && previousWord.length() <= 1 && currentWord.length() <= 1;
            String separator = hasNoSpace ? "" : " ";

            newText += toCapitalWord(previousWord) + separator;

            previousWord = currentWord;
        }

        return newText + previousWord;
    }

    //#endregion
}
