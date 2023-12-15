package ru.idr.arbitragestatistics.util.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.idr.arbitragestatistics.util.IJsonSerializable;

public class Graph<T> implements IJsonSerializable {
    private Map<Vertex<T>, List<Vertex<T>>> adjacentVertices = new HashMap<>();

    public Graph<T> addVertex(Vertex<T> vertex) {
        adjacentVertices.putIfAbsent(vertex, new ArrayList<>());

        return this;
    }

    public Graph<T> addOrientedEdge(Vertex<T> vertexFrom, Vertex<T> vertexTo) {
        int vertexToDepth = vertexFrom.getDepth() + 1;
        vertexTo.setDepth(vertexToDepth);

        adjacentVertices.get(vertexFrom).add(vertexTo);

        return this;
    }

    // public Graph<T> addGraph(Graph<T> graph);

    //#region Getters
    public List<Vertex<T>> getVerticesSet() {
        return new ArrayList<>(adjacentVertices.keySet());
    }

    public List<Vertex<T>> getAdjacentVertices(Vertex<T> vertex) {
        return adjacentVertices.get(vertex);
    }
    //#endregion
    
    //#region toJson
    @Override
    public JSONArray verticesToJsonArray() {
        JSONArray verticesJson = new JSONArray();

        throw new UnsupportedOperationException();

        // return verticesJson;
    }

    @Override
    public JSONArray edgesToJsonArray() {
        JSONArray edgesJson = new JSONArray();

        throw new UnsupportedOperationException();


        // return edgesJson;
    }
    //#endregion
}