package ru.idr.arbitragestatistics.util.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.idr.arbitragestatistics.util.IJsonSerializable;

public class Graph<T> implements IJsonSerializable {
    private Map<Vertex<T>, List<Vertex<T>>> adjacentVertices = new LinkedHashMap<>();
    // depth and vertex value map
    private Map<Integer, Map<T, Vertex<T>>> verticesIndex = new LinkedHashMap<>();
    private Integer currentDepth = 0;

    public Graph<T> nextDepthLevel() {
        this.currentDepth++;

        return this;
    }

    private void setVertexIndex(Integer depth, Vertex<T> vertex) {

        if (verticesIndex.containsKey(depth)) {
            verticesIndex.get(depth).put(vertex.getValue(), vertex);
        } else {
            Map<T, Vertex<T>> verticesValueMap = new LinkedHashMap<>();
            verticesValueMap.put(vertex.getValue(), vertex);

            verticesIndex.put(depth, verticesValueMap);
        }

    }

    public Graph<T> addVertex(Vertex<T> vertex) {
        vertex.setDepth(currentDepth);
        adjacentVertices.putIfAbsent(vertex, new ArrayList<>());

        setVertexIndex(currentDepth, vertex);

        return this;
    }

    public Graph<T> addOrientedEdge(T valueFrom, T valueTo) {
        return this.addOrientedEdge(currentDepth - 1, valueFrom, currentDepth, valueTo);
    }

    public Graph<T> addOrientedEdge(Integer depthFrom, T valueFrom, Integer depthTo, T valueTo) {
        Vertex<T> vertexFrom = this.getVertexByDepthValue(depthFrom, valueFrom);        
        Vertex<T> vertexTo = this.getVertexByDepthValue(depthTo, valueTo);

        return this.addOrientedEdge(vertexFrom, vertexTo);
    }

    public Graph<T> addOrientedEdge(Vertex<T> vertexFrom, Vertex<T> vertexTo) {
        if (vertexFrom == null || vertexTo == null) {
            String vertexFromErrorMessage = String.format("vertexFrom: (null: %b)", vertexFrom == null);
            String vertexToErrorMessage = String.format("vertexTo: (null: %b)", vertexTo == null);

            if (vertexFrom != null) {
                vertexFromErrorMessage += String.format(" | (d: %d, v: %s)", vertexFrom.getDepth(), vertexFrom.getValue());
            }

            if (vertexTo != null) {
                vertexToErrorMessage += String.format(" | (d: %d, v: %s)", vertexTo.getDepth(), vertexTo.getValue());
            }

            String errorMessage = String.format("current Depth: %d; %s; %s", currentDepth, vertexFromErrorMessage, vertexToErrorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        int vertexToDepth = currentDepth;
        vertexTo.setDepth(vertexToDepth);

        adjacentVertices.get(vertexFrom).add(vertexTo);

        if (verticesIndex.get(currentDepth).containsKey(vertexTo.getValue())) {
            verticesIndex.get(currentDepth).remove(vertexTo.getValue());
        }
        setVertexIndex(vertexToDepth, vertexTo);

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

    public Vertex<T> getVertexByDepthValue(Integer depth, T value) {
        return verticesIndex.get(depth).get(value);
    }

    public boolean hasChildren(Vertex<String> vertex) {
        return !this.adjacentVertices.get(vertex).isEmpty(); 
    }
    //#endregion
    
    //#region Json Serialization
    public JSONArray vertexIndexToJson() {
        JSONArray vertexIndexJson = new JSONArray();
        
        for (Integer depth : verticesIndex.keySet()) {
            JSONObject depthLevelJson = new JSONObject();

            depthLevelJson.put("depth", depth);
            
            JSONArray verticesAtDepthLevelJSON = new JSONArray();
            for (Vertex<T> vertex : verticesIndex.get(depth).values()) {
                verticesAtDepthLevelJSON.put(vertex.toJsonObject());
            }

            depthLevelJson.put("vertices", verticesAtDepthLevelJSON);

            vertexIndexJson.put(verticesAtDepthLevelJSON);
        }

        
        return vertexIndexJson;
    }

    @Override
    public JSONArray verticesToJsonArray() {
        JSONArray verticesJson = new JSONArray();

        for (Vertex<T> vertex : this.getVerticesSet()) {
            verticesJson.put(vertex.toJsonObject());
        }

        return verticesJson;
    }

    @Override
    public JSONArray edgesToJsonArray() {
        JSONArray edgesJson = new JSONArray();

        for (Vertex<T> vertexFrom : this.getVerticesSet()) {
            for (Vertex<T> vertexTo : this.getAdjacentVertices(vertexFrom)) {
                JSONObject edgeJson = new JSONObject();

                edgeJson.put("source", vertexFrom.hashCode())
                    .put("target", vertexTo.hashCode());

                edgesJson.put(edgeJson);
            }
        }

        return edgesJson;
    }
    //#endregion
}
