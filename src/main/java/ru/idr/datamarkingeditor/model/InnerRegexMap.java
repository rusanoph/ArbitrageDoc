package ru.idr.datamarkingeditor.model;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    public boolean isEmpty() {
        for (IToken key : this.keySet())
            if (!this.innerRegexMap.get(key).isEmpty()) return false;

        return true;
    }

    public Set<IToken> keySet() {
        return this.innerRegexMap.keySet();
    }

    public Set<String> values(IToken token) {
        return this.innerRegexMap.get(token);
    }

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

    public String get(IToken token) {
        return String.join("|", this.innerRegexMap.get(token));
    }

    //#region JSON Serialization
    public static InnerRegexMap fromJsonObject(String rawJson) {
        JSONObject json = new JSONObject(rawJson);
        return InnerRegexMap.fromJsonObject(json);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static InnerRegexMap fromJsonObject(JSONObject json) {
        InnerRegexMap innerRegexMap = new InnerRegexMap();
        
        Map<String, Object> rawInnerRegexMap = json.toMap();

        for (String rawToken : rawInnerRegexMap.keySet()) {
            IToken token = IToken.fromString(rawToken);
            Set<String> set = new HashSet<String>((List) rawInnerRegexMap.get(rawToken));
            
            innerRegexMap.putAll(token, set);
        }

        return innerRegexMap;
    }

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject(this.innerRegexMap);

        return json;
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
