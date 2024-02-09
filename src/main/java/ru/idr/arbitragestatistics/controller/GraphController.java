package ru.idr.arbitragestatistics.controller;

import java.io.IOException;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.idr.arbitragestatistics.helper.ServerFile;
import ru.idr.arbitragestatistics.util.IJsonSerializable;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.datamarkingeditor.helper.MarkedDataParser;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GraphController {

    @Autowired
    MarkedDataParser parser;

    @Value("${dme.markeddata.path}")
    private String MARKED_DATA_URI;

    @RequestMapping(value = {"/graph/{pathDirectories}/{fileName}"}, method=RequestMethod.GET)
    public String pageMethodWithSegments(Model model, @PathVariable("pathDirectories") String[] pathDirectories, @PathVariable("fileName") String fileName) throws IOException {
        
        String rawJsonGraph = ServerFile.fileText(fileName, "markedDataJson", pathDirectories);
        JSONArray jsonGraph = new JSONArray(rawJsonGraph);

        var graph = parser.getGraphFromJson(jsonGraph);
        setGraphDataToModel(model, graph, "verticesGraph", "edgesGraph");

        return "arbitragestatistics/graph.html";
    }
    


    @RequestMapping(value = {"/graph"})
    public String pageMethod(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        //#region Graph from marked json Example

        String rawJsonOne = ServerFile.fileText("one.json", MARKED_DATA_URI, "Example");
        String rawJsonTwo = ServerFile.fileText("two.json", MARKED_DATA_URI, "Example");

        JSONArray jsonOne = new JSONArray(rawJsonOne);
        JSONArray jsonTwo = new JSONArray(rawJsonTwo);

        var testMarkedGraphExample = parser.getGraphFromJson(
            parser.toJson(
                parser.combine(
                    parser.fromJSON(jsonOne), 
                    parser.fromJSON(jsonTwo))
            )
        );

        setGraphDataToModel(model, testMarkedGraphExample, "verticesMarkedExampleGraph", "edgesMarkedExampleGraph");

        //#endregion
        
        //#region Graph from marked json notUniqueTest
        System.out.println("\n\n--- Start graph form json ---");

        String rawJson = ServerFile.fileText("name.json", MARKED_DATA_URI, "notUniqueTest");
        JSONArray json = new JSONArray(rawJson);
        var testMarkedGraph = parser.getGraphFromJson(json);
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
