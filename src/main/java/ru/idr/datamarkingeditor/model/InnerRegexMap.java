package ru.idr.datamarkingeditor.model;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.idr.datamarkingeditor.model.entity.Entity;
import ru.idr.datamarkingeditor.model.token.IToken;

public class InnerRegexMap {
    //#region Static Fields
    public static final List<IToken> allowedTokens = Entity.ALLOWED_TOKENS;
    //#endregion

    Map<IToken, Set<String>> innerRegexMap;
    
    public InnerRegexMap() {
        this.innerRegexMap = new LinkedHashMap<>();

        for (IToken token : allowedTokens) {
            innerRegexMap.put(token, new HashSet<String>());
        }
    }

    //#region Is Empty
    public boolean isEmpty(IToken token) {
        return this.innerRegexMap.get(token).isEmpty();
    }

    public boolean isEmptyAll() {
        for (IToken key : this.keySet()) {
            if (!this.innerRegexMap.get(key).isEmpty()) {
                return false;
            }
        }

        return true;
    }
    //#endregion

    //#region Get Keys/Values
    public Set<IToken> keySet() {
        return this.innerRegexMap.keySet();
    }

    public Set<String> values(IToken token) {
        return this.innerRegexMap.get(token);
    }
    //#endregion

    //#region Put/PutAll
    public InnerRegexMap put(IToken token, String value) {
        this.innerRegexMap.get(token).add(value);
        return this;
    }

    public InnerRegexMap putAll(IToken token, Iterable<String> values) {
        for (String value : values) this.put(token, value);
        return this;
    }

    public InnerRegexMap putAll(IToken token, String... values) {
        for (String value : values) this.put(token, value);
        return this;
    }
    //#endregion

    //#region Get/GetAll
    public String get(IToken token) {
        return String.join("|", this.innerRegexMap.get(token));
    }

    public String getAll(IToken... tokens) {
        return String.join(
            "|", 
            Stream.of(tokens)
                .filter(token -> !this.isEmpty(token))
                .map(token -> this.get(token))
                .collect(Collectors.toList())
        );
    }
    //#endregion

    //#region JSON Serialization
    public static InnerRegexMap fromJsonObject(String rawJson) {
        JSONObject json = new JSONObject(rawJson);
        return InnerRegexMap.fromJsonObject(json);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static InnerRegexMap fromJsonObject(JSONObject json) {
        InnerRegexMap innerRegexMap = new InnerRegexMap();

        for (IToken token : allowedTokens) {
            List<Object> tokenArray = 
                Objects.requireNonNullElse(
                    json.optJSONArray(token.getLabel()), 
                    new JSONArray()
                )
                .toList();
            
            innerRegexMap.putAll(token, new HashSet<String>((List) tokenArray));
        }

        return innerRegexMap;
    }

    public JSONObject toJsonObject() {
        JSONObject innerRegexMapJson = new JSONObject();

        for (IToken token : allowedTokens) {
            if (!this.innerRegexMap.get(token).isEmpty()) {
                innerRegexMapJson.put(token.getLabel(), this.innerRegexMap.get(token));
            }
        }

        return innerRegexMapJson;
    }
    //#endregion

    //#region Object Overrides
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        InnerRegexMap other = (InnerRegexMap) obj;
        
        return this.innerRegexMap.equals(other.innerRegexMap);
    }

    @Override
    public int hashCode() {
        int hash = 0;

        for (IToken token : allowedTokens) {
            hash = Objects.hash(hash, token);
            for (String value : this.values(token)) {
                hash = Objects.hash(hash, value);
            }
        }

        return hash;
    }
    //#endregion
    
}    
