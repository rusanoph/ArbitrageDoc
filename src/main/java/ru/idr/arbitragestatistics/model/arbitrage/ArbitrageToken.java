package ru.idr.arbitragestatistics.model.arbitrage;

import java.util.HashMap;
import java.util.Map;

public enum ArbitrageToken {
    Complainant("Complainant", "#FFD1DC"),
    Defendant("Defendant", "#ADD8E6"),
    ThirdParty("ThirdParty", "#b8e7c9"),

    FinancialManager("FinancialManager", "#FFDAB9"),

    CourtCaseSum("CourtCaseSum", "#F0E68C"),
    Keyword("Keyword", "#FFD700"),
    ;

    private String color;
    private String label;

    private ArbitrageToken(String label, String color) {
        this.label = label;
        this.color = color;
    }

    public static ArbitrageToken getByLabel(String label) {
        return ArbitrageToken.valueOf(label);
    }

    public String getColor() {
        return this.color;
    }

    public String getLabel() {
        return this.label;
    }
}
