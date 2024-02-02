package ru.idr.arbitragestatistics.controller;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.idr.arbitragestatistics.model.datastructure.StaticGraphs;
import ru.idr.arbitragestatistics.util.IJsonSerializable;

@Controller
public class GraphController {
    @RequestMapping(value = {"/graph"})
    public String pageMethod(Model model, HttpServletRequest request, HttpServletResponse response) {

        //#region cdpGraph
        var cdpGraph = StaticGraphs.getCdpGraph();

        setGraphDataToModel(model, cdpGraph, "verticesCdpGraph", "edgesCdpGraph");

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
