package ru.idr.datamarkingeditor.model.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.idr.datamarkingeditor.model.token.IToken;

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

    public EntityMap addAll(Entity... entities) {
        for (Entity e : entities) this.add(e);
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
    
    public Set<Entity> getOfType(IToken token) {
        return this.stream()
            .filter(entity -> entity.ofType(token))
            .collect(Collectors.toSet());
    }
    //#endregion

    //#region Stream
    public Stream<Entity> stream() {
        return map.values().stream();
    }
    //#endregion

    //#region Iterable Implementation
    @Override
    public Iterator<Entity> iterator() { return new EntityMapIterator(this.map); }
    //#endregion

    //#region JSON Serializatoin

    public JSONArray toJSONArray() {
        
        JSONArray entitiesJson = new JSONArray();
        for (Entity entity : map.values()) {
            entitiesJson.put(entity.toJsonObject());
        }

        return entitiesJson;
    }

    public static EntityMap fromJsonArray(JSONArray json) {
        EntityMap entityMap = new EntityMap();

        for (Object o : json) {
            Entity entity = Entity.fromJsonObject((JSONObject) o);
            entityMap.add(entity);
        }   

        return entityMap;
    }

    //#endregion
}
