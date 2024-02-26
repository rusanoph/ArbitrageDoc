package ru.idr.datamarkingeditor.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.idr.datamarkingeditor.model.token.IToken;

public class EntityMap implements Iterable<Entity> {
    
    private Map<Integer, Entity> map = new HashMap<>();

    public EntityMap() {}
    public EntityMap(JSONObject configJson) {
        EntityMap configEntityMap = EntityMap.fromJsonObject(configJson);
        this.map = configEntityMap.map;
    } 

    //#region Check Properties 
    public boolean contains(Entity entity) { return map.containsValue(entity); }
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
        for (Entity e : entityMap) this.add(e);

        for (EntityMap em : entityMaps) {
            for (Entity e : em) this.add(e);
        }
        
        return this;
    }

    public Entity get(Integer hash) { return this.map.get(hash); }
    public Entity get(Entity patternToken) { return this.map.get(patternToken.hashCode()); }
    
    public Set<Entity> getOfType(IToken token) {
        return this.stream()
            .filter(entity -> entity.ofType(token))
            .collect(Collectors.toSet());
    }
    //#endregion

    //#region Stream
    public Stream<Entity> stream() { return map.values().stream(); }
    //#endregion

    //#region Iterable Implementation
    @Override
    public Iterator<Entity> iterator() { return new EntityMapIterator(this.map); }
    //#endregion

    //#region JSON Serializatoin
    public String toJsonStringAsHashCodes() { return this.toJsonStringAsHashCodes().toString(); }
    public String toJsonString() { return this.toJsonObject().toString(); }

    public JSONArray toJsonArrayAsHashCodes() {
        JSONArray entitiesHashCodeJson = new JSONArray();
        for (Entity entity : map.values()) entitiesHashCodeJson.put(entity.hashCode());

        return entitiesHashCodeJson;
    }

    public JSONObject toJsonObject() {
        
        JSONObject entitiesJson = new JSONObject();

        for (Entity entity : map.values()) {
            entitiesJson.put(
                "" + entity.hashCode(), 
                entity.toJsonObject()
            );
        }

        return entitiesJson;
    }

    public static List<Integer> fromJSONArrayToHashList(JSONArray hashArray) {
        List<Integer> hashList = new ArrayList<>();   
        for (int i = 0; i < hashArray.length(); i++) hashList.add(hashArray.getInt(i));

        return hashList;
    }

    public static EntityMap fromJsonObject(String rawJson) {
        JSONObject json = new JSONObject(rawJson);
        return EntityMap.fromJsonObject(json);
    }

    public static EntityMap fromJsonObject(JSONObject json) {
        
        EntityMap entityMap = new EntityMap();

        // Add Entities
        for (String hash : json.keySet()) {
            entityMap.add(Entity.fromJsonObject(json.getJSONObject(hash)));
        }

        // Setting relations between entities
        for (String hash : json.keySet()) {
            
            Entity current = entityMap.get(Integer.parseInt(hash));
            List<Integer> relatedHashes = EntityMap.fromJSONArrayToHashList(json.getJSONObject(hash).getJSONArray("related"));
            
            for (Integer relatedHash : relatedHashes) {
                current.connect(entityMap.get(relatedHash));
            }
        }

        return entityMap;
    }

    //#endregion
}
