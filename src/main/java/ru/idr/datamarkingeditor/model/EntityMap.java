package ru.idr.datamarkingeditor.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class EntityMap implements Iterable<Entity> {
    
    private Map<Integer, Entity> map = new HashMap<>();

    //#region Check Properties 
    public boolean contains(Entity entity) {
        return map.containsValue(entity);
    }
    //#endregion

    //#region Add/Get
    public EntityMap add(Entity entity) {

        int hash = entity.hashCode();

        if (this.map.values().contains(entity)) {
            Entity current = this.map.get(hash);
            this.map.put(hash, current.merge(entity));
        } else {
            this.map.put(hash, entity);
        }
        
        return this;
    }

    public EntityMap addAll(EntityMap entityMap, EntityMap... entityMaps) {
        
        // Logic to correctly merge all Entities

        for (Entity e : entityMap) this.add(e);

        for (EntityMap em : entityMaps) {
            for (Entity e : em) this.add(e);
        }
        
        return this;
    }

    public Entity get(Entity patternToken) {
        return this.map.get(patternToken.hashCode());
    }
    //#endregion

    //#region Stream
    public Stream<Entity> stream() {
        return map.values().stream();
    }
    //#endregion

    //#region Iterable Implementation
    @Override
    public Iterator<Entity> iterator() {
        return new EntityMapIterator(this.map);
    }
    //#endregion
}
