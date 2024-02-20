package ru.idr.datamarkingeditor.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ru.idr.datamarkingeditor.model.entity.Entity;

public class EntityMapIterator implements Iterator<Entity> {
    private int cursor;
    private int lastReturned = -1;
    private Map<Integer, Entity> map;
    private List<Entity> elements;

    public EntityMapIterator(Map<Integer, Entity> map) {
        this.map = map;
        this.elements = new ArrayList<>(this.map.values());
    }

    @Override
    public boolean hasNext() {
        return this.cursor != this.map.size();
    }

    @Override
    public Entity next() {
        return getNextElement();
    }

    private Entity getNextElement() {
        int current = this.cursor;
        
        this.cursor += 1;
        this.lastReturned = current;

        return elements.get(this.lastReturned);
    }
    
}
