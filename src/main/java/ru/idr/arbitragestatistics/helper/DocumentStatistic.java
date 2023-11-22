package ru.idr.arbitragestatistics.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import com.github.demidko.aot.WordformMeaning;

public class DocumentStatistic {

    private static String regexCapital = "[А-Я]";
    private static String regexSpecialCharacters = "[^а-яА-Я0-9. ]";

    
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

        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
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
