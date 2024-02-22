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
import ru.idr.arbitragestatistics.util.IJsonSerializable;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.datamarkingeditor.helper.MarkedDataParser;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class GraphController {

    @Autowired
    MarkedDataParser parser;

    @Value("${dme.markeddata.path}")
    private String MARKED_DATA_URI;

    @RequestMapping(value = {"/graph/{pathDirectories}/{fileName}"}, method=RequestMethod.GET)
    public String pageMethodWithSegments(Model model, @PathVariable("pathDirectories") String[] pathDirectories, @PathVariable("fileName") String fileName) throws IOException {

        return "arbitragestatistics/graph.html";
    }
    


    @RequestMapping(value = {"/graph"})
    public String pageMethod(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        
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
