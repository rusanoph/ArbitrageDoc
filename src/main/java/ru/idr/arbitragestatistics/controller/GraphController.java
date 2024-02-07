package ru.idr.arbitragestatistics.controller;

import java.io.IOException;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.arbitragestatistics.model.datastructure.StaticGraphs;
import ru.idr.arbitragestatistics.util.IJsonSerializable;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.datamarkingeditor.helper.MarkedDataParser;

@Controller
public class GraphController {

    @Autowired
    MarkedDataParser parser;

    @Value("${dme.markeddata.path}")
    private String MARKED_DATA_URI;


    @RequestMapping(value = {"/graph"})
    public String pageMethod(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        //#region Graph from marked json Example

        String rawJsonOne = ServerFile.fileText("one.json", MARKED_DATA_URI, "Example");
        String rawJsonTwo = ServerFile.fileText("two.json", MARKED_DATA_URI, "Example");

        JSONArray jsonOne = new JSONArray(rawJsonOne);
        JSONArray jsonTwo = new JSONArray(rawJsonTwo);

        var testMarkedGraphExample = MarkedDataParser.getGraphFromJson(
            parser.toJson(
                parser.combine(
                    MarkedDataParser.fromJSON(jsonOne), 
                    MarkedDataParser.fromJSON(jsonTwo))
            )
        );

        setGraphDataToModel(model, testMarkedGraphExample, "verticesMarkedExampleGraph", "edgesMarkedExampleGraph");

        //#endregion
        
        //#region Graph from marked json notUniqueTest
        System.out.println("\n\n--- Start graph form json ---");

        String rawJson = ServerFile.fileText("name.json", MARKED_DATA_URI, "notUniqueTest");
        JSONArray json = new JSONArray(rawJson);
        var testMarkedGraph = MarkedDataParser.getGraphFromJson(json);
        setGraphDataToModel(model, testMarkedGraph, "verticesMarkedGraph", "edgesMarkedGraph");

        System.out.println("--- End graph form json ---");
        //#endregion


        //#region cdpGraph
        // var cdpGraph = StaticGraphs.getCdpGraph();
        // setGraphDataToModel(model, cdpGraph, "verticesCdpGraph", "edgesCdpGraph");
        IJsonSerializable g = new Graph<String>();
        setGraphDataToModel(model, g, "verticesCdpGraph", "edgesCdpGraph");
        //#endregion

        return "arbitragestatistics/graph.html";
    }

    private <T> void setGraphDataToModel(Model model, IJsonSerializable tree, String vertexAttr, String edgeAttr) {
        JSONArray verticesTreeJSON = tree.verticesToJsonArray();
        JSONArray edgesTreeJSON = tree.edgesToJsonArray();
        
        model.addAttribute(vertexAttr, verticesTreeJSON.toString());        
        model.addAttribute(edgeAttr, edgesTreeJSON.toString());
    }
}
