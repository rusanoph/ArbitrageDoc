package ru.idr.arbitragestatistics.util.datastructure;

import java.util.ArrayList;
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

    private void setVertexIndex(Integer depth, Vertex<T> vertex) {


        if (verticesIndex.containsKey(depth)) {
            verticesIndex.get(depth).put(vertex.getValue(), vertex);
        } else {
            Map<T, Vertex<T>> verticesValueMap = new LinkedHashMap<>();
            verticesValueMap.put(vertex.getValue(), vertex);

            verticesIndex.put(depth, verticesValueMap);
        }

    }
    
    public Graph<T> nextDepthLevel() {
        this.currentDepth++;

        return this;
    }

    public Graph<T> addVertex(Vertex<T> vertex) {
        vertex.setDepth(currentDepth);
        adjacentVertices.putIfAbsent(vertex, new ArrayList<>());

        setVertexIndex(currentDepth, vertex);

        return this;
    }

    @SafeVarargs
    public final Graph<T> addOrEdge(T valueFrom, T... valuesTo) {
        for (T valueTo : valuesTo) 
            this.addOrEdge(currentDepth - 1, valueFrom, currentDepth, valueTo);
        
        return this;
    }

    @SafeVarargs
    public final Graph<T> addOrEdge(Vertex<T> vertexFrom, Vertex<T>... verteciesTo) {
        boolean verteciesToContainsNull = false;
        int nullVertexIndex = -1;
        for (int i = 0; i < verteciesTo.length; i++) {
            if (verteciesTo[i] == null) {
                verteciesToContainsNull = true;
                nullVertexIndex = i;
                break;
            }
        } 

        if (vertexFrom == null || verteciesToContainsNull) {
            String vertexFromErrorMessage = String.format("vertexFrom: (null: %b)", vertexFrom == null);
            String vertexToErrorMessage = String.format("vertexTo: (null: %b)", verteciesTo[nullVertexIndex] == null);

            if (vertexFrom != null) {
                vertexFromErrorMessage += String.format(" | (d: %d, v: %s)", vertexFrom.getDepth(), vertexFrom.getValue());
            }

            if (verteciesTo[0] != null) {
                vertexToErrorMessage += String.format(" | (d: %d, v: %s)", verteciesTo[0].getDepth(), verteciesTo[0].getValue());
            }

            String errorMessage = String.format("current Depth: %d; %s; %s", currentDepth, vertexFromErrorMessage, vertexToErrorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        int vertexToDepth = currentDepth;

        for (var vertexTo : verteciesTo) {
            vertexTo.setDepth(vertexToDepth);
            adjacentVertices.get(vertexFrom).add(vertexTo);
            
            if (verticesIndex.get(currentDepth).containsKey(vertexTo.getValue())) {
                verticesIndex.get(currentDepth).remove(vertexTo.getValue());
            }
            setVertexIndex(vertexToDepth, vertexTo);
        }

        return this;
    }

    public Graph<T> addOrEdge(Integer depthFrom, T valueFrom, Integer depthTo, T valueTo) {
        Vertex<T> vertexFrom = this.getVertexByDepthValue(depthFrom, valueFrom);        
        Vertex<T> vertexTo = this.getVertexByDepthValue(depthTo, valueTo);

        return this.addOrEdge(vertexFrom, vertexTo);
    }

    //#region Getters
    public List<Vertex<T>> getVerticesSet() {
        return new ArrayList<>(adjacentVertices.keySet());
    }

    public List<Vertex<T>> getAdjacentVertices(Vertex<T> vertex) {
        return adjacentVertices.get(vertex);
    }

    public Vertex<T> getVertexByDepthValue(Integer depth, T value) {
        
        if (verticesIndex.containsKey(depth) && verticesIndex.get(depth).containsKey(value))
            return verticesIndex.get(depth).get(value);
        else 
            return null;

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

                // System.out.println(vertexFrom.hashCode());
                edgeJson.put("source", vertexFrom.hashCode())
                    .put("target", vertexTo.hashCode());

                edgesJson.put(edgeJson);
            }
        }

        return edgesJson;
    }
    //#endregion
}
