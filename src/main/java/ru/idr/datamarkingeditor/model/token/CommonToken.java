package ru.idr.datamarkingeditor.model.token;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;

public enum CommonToken implements IToken {

    Word("Word"),
    MoneySum("MoneySum"),
    ;

    private static final String MoneySumRegex = "(?<MoneySum>" + RegExRepository.regexCourtCaseSum + ")";

    private String label;
    private String regex;

    private CommonToken(String label) {
        this.label = label;
    }
    
    public String getLabel() { return this.label; }
    public String getRegex() { return this.regex; }
    @Override
    public boolean notCommon() { return false; }

    static {
        MoneySum.regex = CommonToken.MoneySumRegex;
    }
}
