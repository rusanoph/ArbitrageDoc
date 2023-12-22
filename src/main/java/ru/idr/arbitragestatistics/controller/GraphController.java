package ru.idr.arbitragestatistics.controller;

import java.util.regex.Pattern;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.idr.arbitragestatistics.model.datastructure.StaticGraphs;
import ru.idr.arbitragestatistics.model.datastructure.StaticTrees;
import ru.idr.arbitragestatistics.util.IJsonSerializable;
import ru.idr.arbitragestatistics.util.datastructure.Tree;

@Controller
public class GraphController {
    @RequestMapping(value = {"/graph"})
    public String pageMethod(Model model, HttpServletRequest request, HttpServletResponse response) {

        //#region cdpTree
        Tree<Pattern> cdpTree = (new Tree<Pattern>(Pattern.compile("Initial")))
        .appendChild(StaticTrees.getCdpTree1())
        .appendChild(StaticTrees.getCdpTree2())
        .appendChild(StaticTrees.getCdpTree3());
        cdpTree.recomputeDepthDFS();

        setGraphDataToModel(model, cdpTree, "verticesCdpTree", "edgesCdpTree");
        //#endregion


        //#region testTree1
        var testTree = StaticTrees.getTestTree1();

        setGraphDataToModel(model, testTree, "verticesTestTree", "edgesTestTree");
        //#endregion

        //#region testGraph1
        var testGraph1 = StaticGraphs.getTestGraph1();

        setGraphDataToModel(model, testGraph1, "verticesTestGraph_1", "edgesTestGraph_1");
        //#endregion

        //#region testGraph2
        var testGraph2 = StaticGraphs.getTestGraph2();
        setGraphDataToModel(model, testGraph2, "verticesTestGraph_2", "edgesTestGraph_2");
        //#endregion

        return "graph.html";
    }

    private <T> void setGraphDataToModel(Model model, IJsonSerializable tree, String vertexAttr, String edgeAttr) {
        JSONArray verticesTreeJSON = tree.verticesToJsonArray();
        JSONArray edgesTreeJSON = tree.edgesToJsonArray();
        
        model.addAttribute(vertexAttr, verticesTreeJSON.toString());        
        model.addAttribute(edgeAttr, edgesTreeJSON.toString());
    }
}
