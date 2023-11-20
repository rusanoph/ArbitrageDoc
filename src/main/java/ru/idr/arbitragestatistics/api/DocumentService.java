package ru.idr.arbitragestatistics.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.idr.arbitragestatistics.helper.DocumentStatistic;
import ru.idr.arbitragestatistics.helper.ServerFile;

@RestController
public class DocumentService {

    //#region Document Object
    @GetMapping(value="/api/document/text", produces="application/json")    
    public String getDocumentText(@RequestParam("documentPath") String documentPath, 
        @RequestParam("documentFileName") String documentFileName,
        @RequestParam("formated") Boolean formated) {

        JSONObject documentJson = new JSONObject();
        documentJson.put("filename", documentFileName);
        

        if (documentPath.startsWith(".") || documentPath.contains(":")) {
            documentJson.put("error", "Документ не найден.");
            return documentJson.toString();
        }

        Path documentURI = Paths.get("", "txtFiles", documentPath, documentFileName).toAbsolutePath();

        try {
            String targetFileText = new String(Files.readAllBytes(documentURI), StandardCharsets.UTF_8);

            if (formated) {
                targetFileText = DocumentStatistic.removeLineSeparator(targetFileText);
                targetFileText = DocumentStatistic.removeSpecialCharacters(targetFileText);
                targetFileText = DocumentStatistic.removeSpaceBetweenWords(targetFileText);
            }

            documentJson.put("text", targetFileText);
        } catch (IOException ioEx) {
            documentJson.put("error", "Документ не найден.");
            ioEx.printStackTrace();
        }

        return documentJson.toString();
    }
    
    @PostMapping(value="/api/document/text/statistic", produces="applicatoin/json")
    public String getDocumentWordStatistic(@RequestBody String text) {

        JSONObject wordStatisticJson = new JSONObject();
        
        Map<String, Integer> wordStatistic = DocumentStatistic.getWordStatistic(text, " ");
        for (String word : wordStatistic.keySet()) {
            wordStatisticJson.put(word, wordStatistic.get(word));
        }

        return wordStatisticJson.toString();
    }
    //#endregion

    //#region Files
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
