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

import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.Vertex;
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

    public boolean isEmpty() { return this.map.isEmpty(); }
    public boolean notEmpty() { return !this.map.isEmpty(); }

    public boolean hasUnvisited() {
        for (Entity e : this) if (!e.isVisited()) return true;
        return false;
    }
    public void unvisitAll() {
        for (Entity e : this) e.setVisit(false);
    }
    //#endregion

    //#region Add/Get
    public EntityMap add(Entity entity) {

        int hash = entity.hashCode();

        if (this.map.containsValue(entity)) {
            Entity current = this.map.get(hash);

            // If not visited block current entity with visited property and
            // after merging set unvisited
            if (!current.isVisited()) {
                current.setVisit(true);
                this.map.put(hash, current.merge(entity));
                current.setVisit(false);
            }
        } else {
            this.map.put(hash, entity);
        }
        
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

    //#region To Graph<> & Vertex
    public Graph<String> toGraph() {
        Graph<String> graph = new Graph<>();

        for (Entity e : this) {
            graph.addVertex(
                new Vertex<String>(e.getValue(), e.getType().getLabel())
            );
        }

        for (Entity e : this) {
            Vertex<String> v = graph.getVertexByDepthValue(0, e.getValue());

            for (Entity oe : e.getRelated()) {
                Vertex<String> u = graph.getVertexByDepthValue(0, oe.getValue());

                graph.addOrEdge(v, u);
            }
        }

        return graph;
    }
    //#endregion

}
