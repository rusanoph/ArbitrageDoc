package ru.idr.datamarkingeditor.model.token;

public enum CommonToken implements IToken {
    Word("Word")
    ;

    private String label;

    private CommonToken(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public boolean notCommon() {
        return false;
    }
}
