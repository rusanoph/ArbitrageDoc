package ru.idr.datamarkingeditor.model;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class TokenType {
    String value;
    String tokenType;
    Set<TokenType> adjacentTokens;

    public TokenType(String value, String tokeyType) {
        this.value = value.trim().toLowerCase();
        this.tokenType = tokeyType;
        this.adjacentTokens = new HashSet<>();
    }

    public static TokenType fromJsonObject(JSONObject json) {
        
        TokenType token = new TokenType(json.getString("value"), json.getString("type"));

        for (var adjToken : json.getJSONArray("adjacent")) {
            JSONObject adjTokenJson = (JSONObject) adjToken;
            token.getAdjacentTokens()
            .add(new TokenType(adjTokenJson.getString("value"), adjTokenJson.getString("type")));
        }

        return token;
    }

    public JSONObject toJsonObject() {
        JSONObject tokenJson = new JSONObject();

        tokenJson.put("value", this.value)
        .put("type", this.tokenType)
        .put("adjacent", new JSONArray());

        for (TokenType token : adjacentTokens) {
            token = new TokenType(token.value, token.tokenType);
            tokenJson.getJSONArray("adjacent").put(token.toJsonObject());
        }

        return tokenJson;
    }

    public String getValue() {
        return value;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Set<TokenType> getAdjacentTokens() {
        return adjacentTokens;
    }

    
}
