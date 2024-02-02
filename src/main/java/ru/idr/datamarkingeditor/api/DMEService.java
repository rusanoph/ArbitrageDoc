package ru.idr.datamarkingeditor.api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.idr.datamarkingeditor.helper.MarkedDataParser;

@RestController
public class DMEService {
    @Autowired
    private MarkedDataParser parser;
    
    @PostMapping(value="/api/markdata/parse", produces="application/json")
    public String postMarkedTextToParse(@RequestBody String text, @RequestParam("saveType") String saveType) {
        JSONObject jsonObject = parser.parseAsJson();

        // ... 

        return jsonObject.toString();
    }

}
