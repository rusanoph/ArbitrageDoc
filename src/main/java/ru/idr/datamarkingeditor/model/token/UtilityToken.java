package ru.idr.datamarkingeditor.model.token;


public enum UtilityToken implements IToken {
    Initial("Initial"),  // Initial value doesn't has regex
    ;

    public String label;
    public String regex;

    private UtilityToken(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getRegex() {
        return this.regex;
    }
    
}
