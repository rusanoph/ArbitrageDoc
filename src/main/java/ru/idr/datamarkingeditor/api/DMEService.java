package ru.idr.datamarkingeditor.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;
import ru.idr.datamarkingeditor.helper.MarkedDataParser;


@RestController
public class DMEService {
    
    public static final MediaType jsonMediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
    @Value("${dme.markeddata.path}")
    private String MARKED_DATA_URI;


    @SuppressWarnings("null")
    @PostMapping(value="/api/markdata/parse", produces="application/json")
    public ResponseEntity<Map<String, Object>> postMarkedTextToParse(@RequestBody String text) {
        MarkedDataParser parser = new MarkedDataParser();
        JSONObject jsonObject = parser.parse(text).toJsonObject();
        
        return ResponseEntity
            .ok()
            .contentType(jsonMediaType)
            .body(jsonObject.toMap());
    }


    @SuppressWarnings("null")
    @GetMapping(value="/api/markdata/combine/all", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getCombineAllJson(@PathParam("path") String path) throws IOException {

        try {
            MarkedDataParser parser = new MarkedDataParser();
            var combined = parser.combineAll(MARKED_DATA_URI, path);

            return ResponseEntity
                .ok()
                .contentType(jsonMediaType)
                .body(combined.toJsonObject().toMap());

        } catch (Exception ex) {
            JSONObject response = new JSONObject()
                .put("error", ex.getClass().getName())
                .put("message", ex.getMessage());

            return ResponseEntity
                .badRequest()
                .contentType(jsonMediaType)
                .body(response.toMap());
        }
    }
}
