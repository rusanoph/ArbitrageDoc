package ru.idr.datamarkingeditor.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.idr.arbitragestatistics.model.arbitrage.ArbitrageToken;

public class TokenType {
    public static final Set<String> SPECIAL_TOKEN_TYPE = 
        Set.of(ArbitrageToken.values())
        .stream()
        .map(ArbitrageToken::getLabel)
        .map(t -> t.toLowerCase())
        .collect(Collectors.toSet());

    String value;
    String tokenType;
    Set<TokenType> adjacentTokens;

    public TokenType(String value, String tokenType) {
        this.value = value;
        this.tokenType = tokenType;
        this.adjacentTokens = new HashSet<>();
    }

    public boolean ofType(IToken type) {
        return this.getTokenType().equals(type.getLabel());
    }

    public boolean bothOfType(TokenType otherToken, IToken type) {

        boolean isOfType = this.ofType(type);
        boolean hasSameType = otherToken.getTokenType().equals(this.getTokenType());

        return  isOfType && hasSameType;
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

    public void setValue(String value) {
        this.value = value;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Set<TokenType> getAdjacentTokens() {
        return adjacentTokens;
    }

    public boolean isSpecial() {
        return SPECIAL_TOKEN_TYPE.contains(this.tokenType.toLowerCase());
    }

    public boolean hasNamedRegexValue(IToken type) {
        return this.getValue().startsWith("(?<"+type.getLabel()+">") && this.getValue().endsWith(")");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;

        TokenType other = (TokenType) obj;

        return Objects.equals(this.tokenType, other.tokenType) && 
            (Objects.equals(this.value, other.value) || this.isSpecial());
    }

    @Override
    public int hashCode() {
        if (this.isSpecial()) 
            return Objects.hash(this.tokenType);
        else 
            return Objects.hash(this.value, this.tokenType);
    }
  
}
