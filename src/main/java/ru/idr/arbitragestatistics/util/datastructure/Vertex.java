package ru.idr.arbitragestatistics.util.datastructure;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.datamarkingeditor.model.TokenType;

public class Vertex<T> {
    private int depth = 0;
    private T value;
    private String type;
    private Function<String, String> action;

    //#region Constructors
    public Vertex(T value) {
        this.value = value;
    }

    public Vertex(T value, String type) {
        this.value = value;
        this.type = type;
    }

    public Vertex(T value, Function<String, String> action) {
        this.value = value;
        this.action = action;
    }
    
    
    public Vertex(T value, String type, Function<String, String> action) {
        this.value = value;
        this.type = type;
        this.action = action;
    }
    //#endregion

    //#region Json Serialization
    public JSONObject toJsonObject() {
        JSONObject vertexJson = new JSONObject();

        vertexJson.put("id", this.hashCode())
            .put("value", this.value)
            .put("depth", this.depth)
            .put("type", this.type)
            .put("hasAction", this.hasAction());

        return vertexJson;
    }
    //#endregion

    //#region Getter / Setter
    public T getValue() {
        return value;
    }

    public Vertex<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public int getDepth() {
        return this.depth;
    }

    public Vertex<T> setDepth(int depth) {
        this.depth = depth;

        return this;
    }
    //#endregion

    //#region Action
    public Vertex<T> setAction(Function<String, String> action) {
        this.action = action;

        return this;
    }

    public boolean hasAction() {
        return this.action != null;
    }

    public String doAction(String text) {
        try {
            return this.action.apply(text);
        } catch (UnsupportedOperationException uoe) {
            String errorMessage = String.format("Action on this vertex (d: %d, v: %s) doesn't implemented", this.depth, this.value);
            throw new UnsupportedOperationException(errorMessage);
        }
    }
    //#endregion

    
    //#region Type
    public boolean isSpecial() {
        return TokenType.SPECIAL_TOKEN_TYPE.contains(this.type);
    }
    //#endregion

    //#region Static Standard Actoins
    public static String parseComplainant(String text) {
        String ogrn = RegExRepository.regexOgrn + "|" + RegExRepository.regexOgrnip;                
        String inn = RegExRepository.regexInnLegalEntity + "|" + RegExRepository.regexInnPerson;

        Matcher match = Pattern.compile(String.format(".*?\\s*\\((%s),\\s*(%s)\\)", ogrn, inn)).matcher(text);

        String result = text;
        if (match.find()) {
            result = match.group();
        }
        
        return result;
    }

    public static String noAction(String noText) {
        String errorMessage = String.format("Action doesn't implemented");
        throw new UnsupportedOperationException(errorMessage);
    }
    //#endregion
    
}
