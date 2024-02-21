package ru.idr.datamarkingeditor.model.token;

public enum ArbitrageToken implements IToken {
    Complainant("Complainant"),
    Defendant("Defendant"),
    ThirdParty("ThirdParty"),

    CompetitionManager("CompetitionManager"),
    FinancialManager("FinancialManager"),

    Keyword("Keyword"),
    
    ;

    private static final String ComplainantRegex = "(?<Complainant>.+?)(?=())";
    private static final String DefendantRegex = "(?<Defendant>.+?)(?=())";
    private static final String ThirdPartyRegex = "(?<ThirdParty>.+?)(?=())";

    private static final String CompetitionManagerRegex = "(?<CompetitionManager>.+?)(?=())";
    private static final String FinancialManagerRegex = "(?<FinancialManager>.+?)(?=())";
    
    private static final String KeywordRegex = "(?<Keyword>)";  
          
    String label;
    String regex;

    private ArbitrageToken(String label) {
        this.label = label;
    }

    public static ArbitrageToken getByLabel(String label) { return ArbitrageToken.valueOf(label); }

    public String getLabel() { return this.label; }

    public String getRegex() { return this.regex; }

    public boolean isPerson() {
        return this == ArbitrageToken.Complainant ||
        this == ArbitrageToken.Defendant ||
        this == ArbitrageToken.ThirdParty ||
        this == ArbitrageToken.FinancialManager ||
        this == ArbitrageToken.CompetitionManager;
    }
    public boolean isKeyword() { return this == ArbitrageToken.Keyword; }

    // Assignin regex values
    static {
        Complainant.regex = ArbitrageToken.ComplainantRegex;
        Defendant.regex = ArbitrageToken.DefendantRegex;
        ThirdParty.regex = ArbitrageToken.ThirdPartyRegex;

        CompetitionManager.regex = ArbitrageToken.CompetitionManagerRegex;
        FinancialManager.regex = ArbitrageToken.FinancialManagerRegex;

        Keyword.regex = ArbitrageToken.KeywordRegex;
        
    }
}
