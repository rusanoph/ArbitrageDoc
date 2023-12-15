package ru.idr.arbitragestatistics.controller;

import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.idr.arbitragestatistics.model.arbitrage.StaticGraphs;
import ru.idr.arbitragestatistics.model.arbitrage.StaticTrees;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.Tree;

@Controller
public class GraphController {
    @RequestMapping(value = {"/graph"})
    public String pageMethod(Model model, HttpServletRequest request, HttpServletResponse response) {

        //#region cdpTree
        Tree<Pattern> cdpTree = (new Tree<Pattern>(null))
        .appendChild(StaticTrees.cdpTree_1)
        .appendChild(StaticTrees.cdpTree_2)
        .appendChild(StaticTrees.cdpTree_3);

        setTreeDataToModel(model, cdpTree, "verticesCdpTree", "edgesCdpTree");
        //#endregion


        //#region testTree1
        var testTree = StaticTrees.testTree1;

        setTreeDataToModel(model, testTree, "verticesTestTree", "edgesTestTree");
        //#region

        // model.addAttribute("cdpGraph", cdpGraphJSON);


        return "graph.html";
    }

    private <T> void setTreeDataToModel(Model model, Tree<T> tree, String vertexAttr, String edgeAttr) {
        JSONArray verticesCdpTreeJSON = tree.verticesToJsonArray();
        JSONArray edgesCdpTreeJSON = tree.edgesToJsonArray();
        
        model.addAttribute(vertexAttr, verticesCdpTreeJSON.toString());        
        model.addAttribute(edgeAttr, edgesCdpTreeJSON.toString());
    }
}
