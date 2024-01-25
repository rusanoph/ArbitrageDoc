package ru.idr.arbitragestatistics.helper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.model.TitleData;
import ru.idr.arbitragestatistics.model.arbitrage.Person;

public class DocumentProcessor {

    //#region Document

    public static String getText(String documentPath, String documentFileName) throws IOException {

        documentPath = documentPath.trim();
        documentFileName = documentFileName.trim();

        if (documentPath.startsWith(".") || documentPath.contains(":")) {
            throw new IOException("Документ не найден");
        }
        
        try {
            Path documentURI = Paths.get("", "txtFiles", documentPath, documentFileName).toAbsolutePath();
            String targetFileText = new String(Files.readAllBytes(documentURI), StandardCharsets.UTF_8);

            return targetFileText;
        } catch (IOException ioEx) {
            throw new IOException("Документ не найден");
        }
    }

    //#endregion

    //#region Complainant & Defendant

    public static Person getComplainant(String text) {

        Person person = new Person();

        return person;
    }

    public static Person getDefendant(String text) {

        Person person = new Person();

        return person;
    }

    //#endregion

    //#region Court Case Solution

    // . . . 

    //#endregion

    //#region Data

    public static Iterable<String> getMoneySum(String text) {

        List<String> moneySum = new ArrayList<>();

        final String regexString = RegExRepository.regexMoneySumFull + "|" + RegExRepository.regexMoneySumComma + "|" + RegExRepository.regexMoneySumComment;

        Matcher matcher = Pattern.compile(regexString, Pattern.MULTILINE).matcher(text);

        while (matcher.find()) {
            moneySum.add(matcher.group().replaceAll("\\s+", " "));
        }

        return moneySum;
    }

    public static Iterable<String> getMoneySumHyphen(String text) {
        List<String> moneySum = new ArrayList<>();

        final String regexString = String.format("(%s|%s|%s)%s", RegExRepository.regexMoneySumFull, RegExRepository.regexMoneySumComma, RegExRepository.regexMoneySumComment, RegExRepository.regexAfterHyphenText);

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
                String filapath = Paths.get(currentDir, file).toString();

                // Add or Increment title data
                TitleData tmp;
                if (titleMap.containsKey(title)) {
                    tmp = titleMap.get(title); 
                    tmp.addFile(filapath);
                    tmp.setCount(tmp.getCount() + 1);
                } else {
                    tmp = new TitleData();
                    tmp.setCount(1);
                    tmp.addFile(filapath);
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
            "решение",
            "протокол",
            "судебный"
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
            "г\\.",
            "Г\\.",
            "город",
            "а\\d+",
            "имя",
            "\\d{1,2}",
            // "приказ"
        };
        
        String patternTitleEndString = String.join("|", regexpEndRules) + "";
        Pattern patternTitleEnd = Pattern.compile(patternTitleEndString, Pattern.CASE_INSENSITIVE);

        // String[] regexpInnerBeforeEndRules = {
        //     "\\s?по\\s"
        // };

        // String patternTitleInnerBeforeEndString = String.join("?|", regexpInnerBeforeEndRules) + "?";
        // Pattern patternTitleInnerBeforeEnd = Pattern.compile(patternTitleInnerBeforeEndString, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        // String[] regexpInnerAfterEndRules = {
        //     "\\s?к\\s|\\sк\\s?",
        //     "ст\\.",
        //     // ""
        // };

        // String patternTitleInnerAfterEndString = String.join("?|", regexpInnerAfterEndRules) + "?";
        // Pattern patternTitleInnerAfterEnd = Pattern.compile(patternTitleInnerAfterEndString, Pattern.CASE_INSENSITIVE);

        // Find index of last word in title
        Integer titleIndexEnd = -1;
        for (int i = titleIndexStart + 1; i < textLength - 1; i++) {
            if (lemmatizeTextSplit[i].isBlank()) {
                continue;
            }

            Matcher currentWord = patternTitleEnd.matcher(lemmatizeTextSplit[i].trim().toLowerCase());

            if (currentWord.find()) {
                // Matcher wordBefore = patternTitleInnerBeforeEnd.matcher(lemmatizeTextSplit[i - 1].trim());
                // if (wordBefore.find()) {
                //     continue;
                // }
                
                // Matcher wordAfter = patternTitleEnd.matcher(lemmatizeTextSplit[i + 1].trim());
                // if (wordAfter.find()) {
                //     titleIndexEnd = i - 1;
                //     break;
                // }

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

    public static String getCourt(String text) {

        String court = "Суд не определен";
        // List<String> courtList = new ArrayList<String>();
        // courtList.add("Суд не определен");

        String regexCourt = "(арбитражный(.*?)(округа|москвы|республики|области|края))";
        Matcher matcher = Pattern.compile(regexCourt, Pattern.DOTALL | Pattern.CASE_INSENSITIVE).matcher(text.toLowerCase());
        
        if (matcher.find()) {
            court = matcher.group();
        }

        // List<String> matchCourt = new ArrayList<>();
        // while (matcher.find()) {
        //     matchCourt.add(matcher.group());
        // }

        // if (matchCourt.size() > 0) {
        //     courtList = matchCourt;
        // }

        return court;
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

    public static List<String> extractSentences(String text) {
        // === unneded dots === Not Implemented yet
        // address: проспект Академика Сахарова, д. 18, г. Москва
        // url: http://asmo.arbitr.ru/
        // date: 30.12.2021
        // name: Бекетовой Е.А.
        // abbreviation: Можайского г.о.; городского округа, т.е. по состоянию
        // legal article: статьи 6.11; Согласно ч. 1 ст. 1.6 КоАП РФ

        // sentence dot by word split => word.len > 2 & '.' at end & only one '.' 

        List<String> sentencies = new ArrayList<>();

        Matcher m = Pattern.compile("((\\s+([а-яА-Я]{2,}))|[)])\\.").matcher(text);

        int previous = 0;
        while (m.find()) {
            if (m.group().matches("\\s*(оф|им|ст|ул|кв|инк|мкр|пос|рег|обл|комн|тел|руб|коп|час|мин|каб|абз|наб|пер|стр|[Рр][Фф])\\.\\s*")) continue;

            String sentence = text.substring(previous, m.end());
            previous = m.end();

            sentencies.add(removeLineSeparator(sentence));
        }

        if (previous != text.length() - 1) sentencies.add(removeLineSeparator(text.substring(previous)));

        return sentencies;
    }

    public static String removePageNumbersAndDocNumbers(String text) {
        return text.replaceAll("\\b\\d+_\\d+\\b|\\d+(\r\n|[\r\n])", "");
    }

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
        return text.replaceAll(RegExRepository.regexSpecialCharacters, "");
    }

    public static String removeLineSeparator(String text) {
        String separator = "\n|\r";
        return text.replaceAll(separator, " ").trim();
    }

    public static String removeSpaceBetweenWords(String text) {

        text = text.replaceAll("\n|\r", " ");
        text = toCapitalText(text);

        String[] textSplit = text.split("\\s(?="+RegExRepository.regexCapital+")");

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
            boolean hasNoSpace = previousWord.matches("\\.*"+RegExRepository.regexCapital+"$|\\.*"+RegExRepository.regexCapital+" ") && previousWord.length() <= 1 && currentWord.length() <= 1;
            String separator = hasNoSpace ? "" : " ";

            newText += toCapitalWord(previousWord) + separator;

            previousWord = currentWord;
        }

        return newText + previousWord;
    }

    //#endregion
}
