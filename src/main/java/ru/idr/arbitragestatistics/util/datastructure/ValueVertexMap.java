package ru.idr.arbitragestatistics.util.datastructure;

import java.util.HashMap;
import java.util.Map;

public class ValueVertexMap<T> {

    private Map<T, Vertex<T>> valueMap;

    public ValueVertexMap() {
        this.valueMap = new HashMap<>(); 
    }

    public ValueVertexMap<T> put(T value) {
        this.valueMap.put(value, new Vertex<T>(value));
        return this;
    }

    public ValueVertexMap<T> put(T value, Vertex<T> vertex) {
        this.valueMap.put(value, vertex);
        return this;
    }

    public Map<T, Vertex<T>> getMap() {
        return valueMap;
    }
    
}
