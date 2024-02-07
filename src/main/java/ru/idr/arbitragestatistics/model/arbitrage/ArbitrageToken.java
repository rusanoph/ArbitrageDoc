package ru.idr.arbitragestatistics.model.arbitrage;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.datamarkingeditor.model.IToken;

public enum ArbitrageToken implements IToken {
    Complainant("Complainant"),
    Defendant("Defendant"),
    ThirdParty("ThirdParty"),

    CompetitionManager("CompetitionManager"),
    FinancialManager("FinancialManager"),

    Keyword("Keyword"),
    
    // No need to preprocess
    CourtCaseSum("CourtCaseSum")
    ;

    private static final String ComplainantRegex = "(?<Complainant>.+?)";        
    private static final String DefendantRegex = "(?<Defendant>.+?)";    
    private static final String ThirdPartyRegex = "(?<ThirdParty>.+?)";

    private static final String CompetitionManagerRegex = "(?<CompetitionManager>.+?)";
    private static final String FinancialManagerRegex = "(?<FinancialManager>.+?)";
    
    private static final String KeywordRegex = "(?<Keyword>)";  
    
    private static final String CourtCaseSumRegex = "(?<CourtCaseSum>" + RegExRepository.regexCourtCaseSum + ")";
          
    String label;
    String regex;

    private ArbitrageToken(String label) {
        this.label = label;
    }

    public static ArbitrageToken getByLabel(String label) {
        return ArbitrageToken.valueOf(label);
    }

    public String getLabel() {
        return this.label;
    }

    public String getRegex() {
        return this.regex;
    }

    // Assignin regex values
    static {
        Complainant.regex = ArbitrageToken.ComplainantRegex;
        Defendant.regex = ArbitrageToken.DefendantRegex;
        ThirdParty.regex = ArbitrageToken.ThirdPartyRegex;

        CompetitionManager.regex = ArbitrageToken.CompetitionManagerRegex;
        FinancialManager.regex = ArbitrageToken.FinancialManagerRegex;

        Keyword.regex = ArbitrageToken.KeywordRegex;
        
        CourtCaseSum.regex = ArbitrageToken.CourtCaseSumRegex;
    }
}
