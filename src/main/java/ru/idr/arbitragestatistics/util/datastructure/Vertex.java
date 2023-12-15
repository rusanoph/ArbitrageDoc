package ru.idr.arbitragestatistics.util.datastructure;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;

public class Vertex<T> {
    private int depth = 0;
    private T value;
    private Function<String, String> action;

    //#region Constructors
    public Vertex(T value) {
        this.value = value;
    }

    public Vertex(T value, Function<String, String> action) {
        this.value = value;
        this.action = action;
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
        return this.action.apply(text);
    }
    //#endregion

    //#region Standard Actoins
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
    //#endregion
    
}
