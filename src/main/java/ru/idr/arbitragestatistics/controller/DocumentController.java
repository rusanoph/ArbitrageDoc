package ru.idr.arbitragestatistics.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentController {

    @GetMapping("/api/document/{document-name}")
    public String getDocumentText(@PathVariable("document-name") String documentName) {

        Path currentRelativePath = Paths.get("");
        String currentAbsolutePath = currentRelativePath.toAbsolutePath().toString() + "\\" + documentName;

        JSONObject returnJson = new JSONObject();
        returnJson.put("documentPath", currentAbsolutePath);

        return returnJson.toString();
    }

}
