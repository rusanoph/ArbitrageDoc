package ru.idr.arbitragestatistics.api;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.idr.arbitragestatistics.helper.ArbitrageTemplateSeeker;
import ru.idr.arbitragestatistics.helper.DocumentProcessor;
import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.arbitragestatistics.model.TitleData;
import ru.idr.datamarkingeditor.helper.TextParser;

@RestController
public class DocumentService {

    @Value("${dme.markeddata.path}")
    private String MARKED_DATA_URI;

    @Autowired
    ArbitrageTemplateSeeker ats;

    //#region Parsing
    @GetMapping(value = "/api/document/parsing", produces="application/json")
    public String getMethodName(@RequestParam("documentPath") String documentPath, @RequestParam("documentFileName") String documentFileName) throws IOException {

        String modelRawJson = ServerFile.fileText("model.json", MARKED_DATA_URI, "JsonTest", "TextParserTest");
        TextParser parser = new TextParser(modelRawJson);

        // ... not implemented ...

        return new String();
    }
    
    //#endregion

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

    @GetMapping(value="/api/document/sentencies", produces="application/json")    
    public String getDocumentSentencies(@RequestParam("documentPath") String documentPath, @RequestParam("documentFileName") String documentFileName) {
        JSONObject sentenciesJSON = new JSONObject();
        sentenciesJSON.put("sentencies", new JSONArray());


        try {
            String targetFileText = DocumentProcessor.getText(documentPath, documentFileName);

            List<String> header = DocumentProcessor.extractSentences(ats.getHeaderPart(targetFileText));
            List<String> afterFound = DocumentProcessor.extractSentences(ats.getAfterFoundPart(targetFileText));
            List<String> afterDetermined = DocumentProcessor.extractSentences(ats.getAfterDeterminedPart(targetFileText));
            List<String> afterDecided = DocumentProcessor.extractSentences(ats.getAfterDecidedPart(targetFileText));
            List<String> afterSolution = DocumentProcessor.extractSentences(ats.getAfterSolutionPart(targetFileText));

            List<String> sentencies = Stream.of(header, afterFound, afterDetermined, afterDecided, afterSolution).flatMap(Collection::stream).toList();

            for (String sentence : sentencies) {
                sentenciesJSON.getJSONArray("sentencies").put(sentence);
            }
        } catch (IOException ioEx) {
            sentenciesJSON.put("error", "Документ не найден.");
            ioEx.printStackTrace();
        }

        return sentenciesJSON.toString();
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

            documentPartsJson.put("complainantAndDefendantGraph", ats.getComplainantAndDefendantPartGraph(targetFileText));
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

    @PostMapping(value="/api/document/text/data/moneysum/hyphen", produces="application/json")
    public String postDocumentMoneySumHyphen(@RequestBody String text) {

        JSONArray moneySumJson = new JSONArray();
        Iterable<String> moneySums = DocumentProcessor.getMoneySumHyphen(text);

        for (String moneySum : moneySums) {
            moneySumJson.put(moneySum);
        }

        return moneySumJson.toString();
    }

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

        Set<String> listOfDirectories = ServerFile.listDirectoryServer("txtFiles", directoryPath);

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
}
