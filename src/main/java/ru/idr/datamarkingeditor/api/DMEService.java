package ru.idr.datamarkingeditor.api;

import java.io.IOException;
import java.util.Set;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.datamarkingeditor.helper.MarkedDataParser;


@RestController
public class DMEService {
    
    @Value("${dme.markeddata.path}")
    private String MARKED_DATA_URI;

    @Autowired
    private MarkedDataParser parser;

    
    @PostMapping(value="/api/markdata/parse", produces="application/json")
    public String postMarkedTextToParse(@RequestBody String text) {
        JSONArray jsonObject = parser.toJson(parser.parse(text));

        return jsonObject.toString();
    }

    @GetMapping(value="/api/markdata/combine/{localDirPath}", produces = "application/json")
    public String getCombineAllJson(@PathVariable String localDirPath) throws IOException {

        var combined = parser.combineAll(MARKED_DATA_URI, localDirPath);

        return parser.toJson(combined).toString();
    }

    @GetMapping(value="/api/markdata/list/dir")
    public String getMethodName() {
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
