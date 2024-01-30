package ru.idr.arbitragestatistics.model.arbitrage;

import java.util.HashMap;
import java.util.Map;

public enum ArbitrageToken {
    Complainant("complainant", "#FFD1DC"),
    Defendant("defendant", "#ADD8E6"),
    ThirdParty("thirdParty", "#b8e7c9"),
    CourtCaseSum("courtCaseSum", "#F0E68C"),
    Keyword("keyword", "#FFD700"),
    ;

    private String color;
    private String label;

    private ArbitrageToken(String label, String color) {
        this.label = label;
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public String getLabel() {
        return this.label;
    }
}
