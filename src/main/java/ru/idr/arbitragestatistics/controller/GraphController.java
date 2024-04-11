package ru.idr.arbitragestatistics.controller;

import java.io.IOException;
import java.util.Set;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import ru.idr.arbitragestatistics.util.IJsonSerializable;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.datamarkingeditor.helper.MarkedDataParser;
import ru.idr.datamarkingeditor.model.entity.EntityMap;

import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class GraphController {

    @Value("${dme.markeddata.path}")
    private String MARKED_DATA_URI;

    @RequestMapping(value="/graph/input", method=RequestMethod.GET)
    public String graphInputPage(HttpServletRequest request, Model model) {    
        
        model.addAttribute("activePage", "graph");
        
        return "arbitragestatistics/graph-input.html";
    }

    @RequestMapping(value = {"/graph/file"}, method=RequestMethod.GET)
    public String pageMethodWithSegments(Model model, @PathParam("pathDirectories") String[] pathDirectories, @PathParam("fileName") String[] fileName) throws IOException {


        MarkedDataParser parser = new MarkedDataParser();
        EntityMap em = parser.combine(Set.of(fileName), MARKED_DATA_URI, pathDirectories);

        setGraphDataToModel(model, em.toGraph(), "verticesGraph", "edgesGraph");
        model.addAttribute("activePage", "graph");

        return "arbitragestatistics/graph.html";
    }

    @RequestMapping(value = {"/graph"})
    public String pageMethod(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        setGraphDataToModel(model, new Graph<String>(), "verticesGraph", "edgesGraph");

        model.addAttribute("activePage", "graph");

        return "arbitragestatistics/graph.html";
    }

    private <T> void setGraphDataToModel(Model model, IJsonSerializable tree, String vertexAttr, String edgeAttr) {
        JSONArray verticesTreeJSON = tree.verticesToJsonArray();
        JSONArray edgesTreeJSON = tree.edgesToJsonArray();
        
        model.addAttribute(vertexAttr, verticesTreeJSON.toString());        
        model.addAttribute(edgeAttr, edgesTreeJSON.toString());
    }
}
