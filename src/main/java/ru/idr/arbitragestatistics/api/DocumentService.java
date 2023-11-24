package ru.idr.arbitragestatistics.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.idr.arbitragestatistics.helper.DocumentProcessor;
import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.arbitragestatistics.model.TitleData;

@RestController
public class DocumentService {

    //#region Document Object
    @GetMapping(value="/api/document/text", produces="application/json")    
    public String getDocumentText(@RequestParam("documentPath") String documentPath, 
        @RequestParam("documentFileName") String documentFileName,
        @RequestParam("formated") Boolean formated,
        @RequestParam("lemma") Boolean lemma) {

        JSONObject documentJson = new JSONObject();
        documentJson.put("filename", documentFileName);
        

        if (documentPath.startsWith(".") || documentPath.contains(":")) {
            documentJson.put("error", "Документ не найден.");
            return documentJson.toString();
        }

        Path documentURI = Paths.get("", "txtFiles", documentPath, documentFileName).toAbsolutePath();

        try {
            String targetFileText = new String(Files.readAllBytes(documentURI), StandardCharsets.UTF_8);

            String arbitrageTitle = DocumentProcessor.getArbitrageTextTitle(targetFileText, " ");

            if (formated) {
                // targetFileText = DocumentStatistic.removeLineSeparator(targetFileText);
                targetFileText = DocumentProcessor.removeSpecialCharacters(targetFileText);
                targetFileText = DocumentProcessor.removeSpaceBetweenWords(targetFileText);
            }

            if (lemma) {
                targetFileText = DocumentProcessor.lemmatizeText(targetFileText, " ");
            }

            documentJson.put("title", arbitrageTitle);
            documentJson.put("text", targetFileText);

        } catch (IOException ioEx) {
            documentJson.put("error", "Документ не найден.");
            ioEx.printStackTrace();
        }

        return documentJson.toString();
    }
    
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

    //#region Files
    @GetMapping(value="/api/document/list/title", produces="application/json")
    public String getTitleData(@RequestParam("directoryPath") String directoryPath) {

        JSONArray documentTitleJson = new JSONArray();

        try {
            Map<String, TitleData> textTitles = DocumentProcessor.getTitleMap(directoryPath);
            
            for (String title : textTitles.keySet()) {
                
                TitleData tmp = textTitles.get(title);
                JSONObject jsonTmp = new JSONObject();

                jsonTmp.put("title", title);                
                jsonTmp.put("count", tmp.getCount());
                jsonTmp.put("files", String.join(",<br><br>", tmp.getFiles()));

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
    //#endregion

}
