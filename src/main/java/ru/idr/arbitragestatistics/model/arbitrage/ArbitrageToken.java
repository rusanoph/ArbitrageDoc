package ru.idr.arbitragestatistics.model.arbitrage;

import ru.idr.arbitragestatistics.model.IToken;

public enum ArbitrageToken implements IToken {
    Complainant("Complainant"),
    Defendant("Defendant"),
    ThirdParty("ThirdParty"),

    FinancialManager("FinancialManager"),

    CourtCaseSum("CourtCaseSum"),
    Keyword("Keyword"),
    ;

    String label;

    private ArbitrageToken(String label) {
        this.label = label;
    }

    public static ArbitrageToken getByLabel(String label) {
        return ArbitrageToken.valueOf(label);
    }

    public String getLabel() {
        return this.label;
    }
}
