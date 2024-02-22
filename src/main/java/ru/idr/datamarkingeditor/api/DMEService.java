package ru.idr.datamarkingeditor.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;
import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.datamarkingeditor.helper.MarkedDataParser;


@RestController
public class DMEService {
    
    @Value("${dme.markeddata.path}")
    private String MARKED_DATA_URI;

    @Autowired
    private MarkedDataParser parser;

    
    @SuppressWarnings("null")
    @PostMapping(value="/api/markdata/parse", produces="application/json")
    public ResponseEntity<Map<String, Object>> postMarkedTextToParse(@RequestBody String text) {
        JSONObject jsonObject = parser.parse(text).toJsonObject();
        
        return ResponseEntity
            .ok()
            .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
            .body(jsonObject.toMap());
    }


    @SuppressWarnings("null")
    @GetMapping(value="/api/markdata/combine", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getCombineAllJson(@PathParam("path") String path) throws IOException {

        var combined = parser.combineAll(MARKED_DATA_URI, path);

        return ResponseEntity
            .ok()
            .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
            .body(combined.toJsonObject().toMap());
    }

    
    @GetMapping(value="/api/markdata/list/dir")
    public String getMarkedDataDirs() {
        Set<String> listOfDirectories = ServerFile.listDirectoryServer("markedDataJson");

        JSONArray returnJson = new JSONArray();
        if (listOfDirectories != null) {
            for (String dirName : listOfDirectories) {
                returnJson.put(dirName);
            }
        }

        return returnJson.toString();
    }
    
}
