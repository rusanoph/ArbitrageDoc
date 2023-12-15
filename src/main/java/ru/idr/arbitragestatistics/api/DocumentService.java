package ru.idr.arbitragestatistics.api;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.idr.arbitragestatistics.helper.ArbitrageTemplateSeeker;
import ru.idr.arbitragestatistics.helper.DocumentProcessor;
import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.arbitragestatistics.model.TitleData;
import ru.idr.arbitragestatistics.util.HTMLWrapper;

@RestController
public class DocumentService {

    @Autowired
    ArbitrageTemplateSeeker ats;

    //#region Document Object
    @GetMapping(value="/api/document/text", produces="application/json")    
    public String getDocumentText(@RequestParam("documentPath") String documentPath, 
        @RequestParam("documentFileName") String documentFileName,
        @RequestParam("formated") Boolean formated,
        @RequestParam("lemma") Boolean lemma) {

        JSONObject documentJson = new JSONObject();
        documentJson.put("filename", documentFileName);

        try {
            String targetFileText = DocumentProcessor.getText(documentPath, documentFileName);
            String arbitrageTitle = DocumentProcessor.getArbitrageTextTitle(targetFileText, " ");

            if (formated) {
                // targetFileText = DocumentStatistic.removeLineSeparator(targetFileText);
                targetFileText = DocumentProcessor.removeSpecialCharacters(targetFileText);
                targetFileText = DocumentProcessor.removeSpaceBetweenWords(targetFileText);
            }

            if (lemma) {
                targetFileText = DocumentProcessor.lemmatizeText(targetFileText, " ");
            }

            targetFileText = targetFileText.replaceAll("\t", "&#8195;");
            targetFileText = targetFileText.replaceAll("\\*\\*(.*?)\\*\\*", HTMLWrapper.tag("span", "$1", "sub-accent"));            
            targetFileText = targetFileText.replaceAll("(\\*)", HTMLWrapper.tag("span", "$1", "sub-accent"));


            documentJson.put("title", arbitrageTitle);
            documentJson.put("text", targetFileText);

        } catch (IOException ioEx) {
            documentJson.put("error", "Документ не найден.");
            ioEx.printStackTrace();
        }

        return documentJson.toString();
    }
    
    @GetMapping(value="/api/document/text/part", produces="application/json")    
    public String getDocumentStructureParts(@RequestParam("documentPath") String documentPath, @RequestParam("documentFileName") String documentFileName) {

        JSONObject documentPartsJson = new JSONObject();

        try {
            String targetFileText = DocumentProcessor.getText(documentPath, documentFileName);

            documentPartsJson.put("header", ats.getHeaderPart(targetFileText));
            documentPartsJson.put("found", ats.getAfterFoundPart(targetFileText));
            documentPartsJson.put("determined", ats.getAfterDeterminedPart(targetFileText));
            documentPartsJson.put("decided", ats.getAfterDecidedPart(targetFileText));
            documentPartsJson.put("solution", ats.getAfterSolutionPart(targetFileText));

            //#region Complainant And Defendant Part
            String headerRegex = HTMLWrapper.tag("h2", "Алгоритм поиска 'истец и ответчик' version 1 (RegEx)") + "<br>";
            String headerTree = HTMLWrapper.tag("h2", "Алгоритм поиска 'истец и ответчик' version 2 (Tree)") + "<br>";
            String headerGraph = HTMLWrapper.tag("h2", "Алгоритм поиска 'истец и ответчик' version 3 (Graph)") + "<br>";


            documentPartsJson.put("complainantAndDefendantRegex", 
                String.format("%s<br>%s", 
                    headerRegex, 
                    ats.getComplainantAndDefendantPartRegex(targetFileText))
            );

            documentPartsJson.put("complainantAndDefendantTree", 
                String.format("%s<br>%s", 
                    headerTree, 
                    ats.getComplainantAndDefendantPartTree(targetFileText))
            );

            documentPartsJson.put("complainantAndDefendantGraph", 
                String.format("%s<br>%s", 
                    headerGraph, 
                    ats.getComplainantAndDefendantPartGraph(targetFileText))
            );
            //#endregion

        } catch (IOException ioEx) {
            documentPartsJson.put("error", "Документ не найден.");
            ioEx.printStackTrace();
        }

        return documentPartsJson.toString();
    }

    @GetMapping(value="/api/document/court")
    public String getDocumentCourt(@RequestParam("documentPath") String documentPath, @RequestParam("documentFileName") String documentFileName) {

        try {
            String text = DocumentProcessor.getText(documentPath, documentFileName);
            String court = DocumentProcessor.getCourt(text);
            
            return court;
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            
            return ioEx.getMessage();
        }

    }

     @PostMapping(value="/api/document/text/data/moneysum", produces="application/json")
    public String postDocumentMoneySum(@RequestBody String text) {

        JSONArray moneySumJson = new JSONArray();

        Iterable<String> moneySums = DocumentProcessor.getMoneySum(text);

        for (String moneySum : moneySums) {
            moneySumJson.put(moneySum);
        }

        return moneySumJson.toString();
    }

    @PostMapping(value="/api/document/text/data/moneysum/hyphen", produces="application/json")
    public String postDocumentMoneySumHyphen(@RequestBody String text) {

        JSONArray moneySumJson = new JSONArray();
        Iterable<String> moneySums = DocumentProcessor.getMoneySumHyphen(text);

        for (String moneySum : moneySums) {
            moneySumJson.put(moneySum);
        }

        return moneySumJson.toString();
    }

    //#region Document Statistic
    @PostMapping(value="/api/document/text/statistic", produces="applicatoin/json")
    public String postDocumentWordStatistic(@RequestBody String text) {

        JSONObject wordStatisticJson = new JSONObject();
        
        Map<String, Integer> wordStatistic = DocumentProcessor.getWordStatistic(text, " ");
        for (String word : wordStatistic.keySet()) {
            wordStatisticJson.put(word, wordStatistic.get(word));
        }

        return wordStatisticJson.toString();
    }
    
    @PostMapping(value="/api/document/text/lemma/valid", produces="application/json")
    public String postDocumentTextLemmaValid(@RequestBody String text) {

        JSONObject textLemmasJson = new JSONObject();

        Iterable<String> textLemmas = DocumentProcessor.getLemmasFromText(text, " ", true);
        String simplifiedText = String.join(" ", textLemmas);

        Map<String, Integer> wordStatistic = DocumentProcessor.getWordStatistic(simplifiedText, " ");
        for (String word : wordStatistic.keySet()) {
            textLemmasJson.put(word, wordStatistic.get(word));
        }

        return textLemmasJson.toString();

    }

    @PostMapping(value="/api/document/text/lemma/invalid", produces="application/json")
    public String postDocumentTextLemmaInvalid(@RequestBody String text) {

        JSONObject textLemmasJson = new JSONObject();

        Iterable<String> textLemmas = DocumentProcessor.getLemmasFromText(text, " ", false);
        String simplifiedText = String.join(" ", textLemmas);

        Map<String, Integer> wordStatistic = DocumentProcessor.getWordStatistic(simplifiedText, " ");
        for (String word : wordStatistic.keySet()) {
            textLemmasJson.put(word, wordStatistic.get(word));
        }

        return textLemmasJson.toString();

    }
    //#endregion

   //#endregion

    //#region Files
    @GetMapping(value="/api/document/list/title", produces="application/json")
    public String getTitleMapData(@RequestParam("directoryPath") String directoryPath) {

        JSONArray documentTitleJson = new JSONArray();

        try {
            Map<String, TitleData> textTitles = DocumentProcessor.getTitleMap(directoryPath);
            
            for (String title : textTitles.keySet()) {
                
                TitleData tmp = textTitles.get(title);
                JSONObject jsonTmp = new JSONObject();

                jsonTmp.put("title", title);
                jsonTmp.put("count", tmp.getCount());
                jsonTmp.put("files", String.join("<br><br>", tmp.getFiles()));

                documentTitleJson.put(jsonTmp);
            }

        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

        return documentTitleJson.toString();
    }

    @GetMapping(value="/api/document/list/doc", produces="application/json")
    public String getListOfFiles(@RequestParam("directoryPath") String directoryPath) {

        Set<String> listOfFiles = ServerFile.listFilesServer(directoryPath);

        JSONArray returnJson = new JSONArray();
        
        if (listOfFiles != null) {
            for (String fileName : listOfFiles) {
                returnJson.put(fileName);
            }
        }

        return returnJson.toString();
    }
    //#endregion

    //#region Directories
    @GetMapping(value="/api/document/list/dir", produces="application/json")
    public String getListOfDirectories(@RequestParam("directoryPath") String directoryPath) {

        Set<String> listOfDirectories = ServerFile.listDirectoryServer(directoryPath);

        JSONArray returnJson = new JSONArray();
        
        if (listOfDirectories != null) {
            for (String dirName : listOfDirectories) {
                returnJson.put(dirName);
            }
        }

        return returnJson.toString();

    }
    
    @GetMapping(value="/api/document/list/dir/deep", produces="application/json")
    public String getDeepListOfDirectories(@RequestParam("directoryPath") String directoryPath) {

        JSONArray returnJson = new JSONArray();

        Set<String> listOfDirectories = ServerFile.deepListDirectoryServer(directoryPath);

        if (listOfDirectories != null) {
            for (String dirName : listOfDirectories) {
                returnJson.put(dirName);
            }
        }

        return returnJson.toString();
    }
    //#endregion


    //#region Method For Test

    @GetMapping(value="/api/test")
    public String  getResultOfTestFunction(@RequestBody String text) {

        ArbitrageTemplateSeeker ats = new ArbitrageTemplateSeeker();

        text = DocumentProcessor.removePageNumbersAndDocNumbers(text);
        text = DocumentProcessor.removeLineSeparator(text);

        return ats.getAfterDecidedPart(text);
    }

    //#endregion
}
