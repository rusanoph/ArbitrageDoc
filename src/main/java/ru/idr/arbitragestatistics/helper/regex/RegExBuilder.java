package ru.idr.arbitragestatistics.helper.regex;

public class RegExBuilder {
    
    private String text = "";

    public RegExBuilder() {}

    public RegExBuilder(String text) { this.text = text.replaceAll("\\s+", "\\\\s+"); }

    public RegExBuilder set(String text) {
        this.text = text;

        return this;
    }

    public String build() {
        return this.text;
    }

    public RegExBuilder starts() {
        text = "(?<=(" + text + "))";

        return this;
    }

    public RegExBuilder starts(String startRegex) {
        text = "(?<=(" + startRegex + "))" + text;

        return this;
    }

    public RegExBuilder ends() {
        text = "(?=(" + text + "))";

        return this;
    }

    public RegExBuilder ends(String endRegex) {
        text += "(?=(" + endRegex + "))";

        return this;
    }

    public RegExBuilder asWord() {
        text = String.format("(^|\\s+)%s(\\s+|$)", text);

        return this;
    }

    public RegExBuilder asStaticToken() {
        text = String.format("(^|\\s*)%s(\\s*|$)", text);

        return this;
    }

}
