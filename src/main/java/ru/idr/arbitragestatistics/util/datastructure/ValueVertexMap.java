package ru.idr.arbitragestatistics.util.datastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

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

    public Vertex<T> get(T value) {
        if (!this.valueMap.containsKey(value)) {
            String errorMessage = "There is no element '" + value + "' in ValueVertexMap";
            throw new NoSuchElementException(errorMessage);
        }

        return this.valueMap.get(value);
    }

    public Set<T> keySet() {
        return valueMap.keySet();
    }
    
}
