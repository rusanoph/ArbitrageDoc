package ru.idr.arbitragestatistics.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

    //#region Data

    public static Map<String, TitleData> getTitleMap(String currentDir) throws IOException {

        Map<String, TitleData> titleMap = new HashMap<>();

        Set<String> dirs = ServerFile.listDirectoryServer(currentDir);
        if (dirs != null) {
            for (String dir : dirs) {
                // Merge two maps: recursive map and titleMap
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

        Set<String> files = ServerFile.listFilesServer(currentDir);
        if (files != null) {
                for (String file : files) {

                String title = getArbitrageTextTitle(ServerFile.fileText(currentDir, file), " ");

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

        List<String> possibleTitles = new ArrayList<>(List.of(
            "резолютивный",
            "определение",
            "постановление",
            "протокольный",
            "решение"
        ));

        text = DocumentProcessor.removeLineSeparator(text);
        text = DocumentProcessor.removeSpecialCharacters(text);
        text = DocumentProcessor.removeSpaceBetweenWords(text);
        text = text.toLowerCase();

        String lemmatizeText = DocumentProcessor.lemmatizeText(text, " ");

        // Find index of first word in title
        Integer titleIndex = 0;
        for (String title : possibleTitles) {
            if (lemmatizeText.contains(title)) {
                
                Integer candidateTitleIndex = lemmatizeText.indexOf(title);

                if (titleIndex == 0) {
                    titleIndex = candidateTitleIndex;
                } else {
                    titleIndex = titleIndex < candidateTitleIndex ? titleIndex : candidateTitleIndex; 
                }
            }
        }

        // Cut off all words before first word in title
        String candidateTitle = text.substring(titleIndex);

        // Get substring by regexp
        List<String> patterns = new ArrayList<>(List.of(
            "(.*?дело)",
            "(.*?г\\.)",
            "(.*?Г\\.)"
        ));
        
        // Find regexp result with minimal length

        for (String patternRaw : patterns) {
            Pattern pattern = Pattern.compile(patternRaw);
            Matcher matcher = pattern.matcher(candidateTitle);

            if (matcher.find()) {
                String title = matcher.group();

                if (title.length() < candidateTitle.length()) {
                    candidateTitle = title;
                }
            }
        }

        arbitrageTitle = candidateTitle;
        
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
