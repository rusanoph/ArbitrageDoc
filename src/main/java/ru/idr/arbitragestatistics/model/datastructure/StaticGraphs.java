package ru.idr.arbitragestatistics.model.datastructure;


import ru.idr.arbitragestatistics.helper.regex.RegExBuilder;
import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.ValueVertexMap;

public class StaticGraphs {
    
    // private static final String PARSE_COMPLAINANT_TYPE = "(?<complainantType>.*?)";

    private static final String PARSE_COMPLAINANT = "(?<complainant>.*?)";
    private static final String PARSE_DEFENDANT = "(?<defendant>.*?)";
    private static final String PARSE_THIRD_PARTY = "(?<thirdParty>.*?)";
    
    private static final String PARSE_PERSON_ID = "(?<personId>.*?)";
    private static final String PARSE_PERSON_ADDRESS = "(?<personAddress>.*?)";
    private static final String PARSE_COURT_CASE_SUM = "(?<courtCaseSum>"+RegExRepository.regexCourtCaseSum+")";
    
    private static final String PARSE_COMPETITION_MANAGER = "(?<competitionManager>.*?)";
    private static final String PARSE_FINANCIAL_MANAGER = "(?<financialManager>.*?)";

    private static final String PARSE_KEYWORD = "(?<keyword>"+
        "(продаж(и|а)\\s+имущества\\s+должника|"+
        "административной\\s+ответственности|"+
        "финансовых\\s+санкций|"+
        "несостоятельн(ым|ой)\\s+\\(?банкротом\\)?|"+
        "замене\\s+кредитора))";
 

    public static Graph<String> getCdpGraph() {
        Graph<String> cdpGraph = new Graph<>();
        ValueVertexMap<String> dict = ArbitrageDictionary.getWordMap();

        RegExBuilder rb = new RegExBuilder();

        String complainantToken = "(?<=(^|\\s*))" + PARSE_COMPLAINANT + rb.set("(о|к)").asWord().ends().build();
        dict.put(complainantToken);

        String defendantToken = "(?<=(^|\\s*))" + PARSE_DEFENDANT + rb.set("треть(е|и)\\s+лиц(о|а)|несостоятельн(ым|ой)|финансовых\\s+санкций").asStaticToken().ends().build();
        dict.put(defendantToken);

        String thirdPartyToken = "(?<=(^|\\s*))" + PARSE_THIRD_PARTY + rb.set("(о|к)").asWord().ends().build();
        dict.put(thirdPartyToken);

        String courtCaseSumToken = "(?<=(^|\\s*))" + PARSE_COURT_CASE_SUM;
        dict.put(courtCaseSumToken);

        String keywordToken = "(?<=(^|\\s*))" + PARSE_KEYWORD;
        dict.put(keywordToken);

        for (var word : dict.keySet()) {
            cdpGraph.addVertex(dict.get(word));
        }
        
        cdpGraph.addOrEdge(dict.get("Initial"), 
            dict.get("рассмотрев"), dict.get("ознакомившись"));

        //#region Constant to Constant literal
        cdpGraph.addOrEdge(dict.get("рассмотрев"), dict.get("в"));
        cdpGraph.addOrEdge(dict.get("открытом"), dict.get("судебном"));
        cdpGraph.addOrEdge(dict.get("судебном"), dict.get("заседании"));
        cdpGraph.addOrEdge(dict.get("реестре"), dict.get("требований"));
        cdpGraph.addOrEdge(dict.get("делу"), dict.get("о"));
        cdpGraph.addOrEdge(dict.get("ознакомившись"), dict.get("с"));
        cdpGraph.addOrEdge(dict.get("с"), dict.get("заявлением"));
        cdpGraph.addOrEdge(dict.get("включении"), dict.get("в"));
        cdpGraph.addOrEdge(dict.get("реестр"), dict.get("требований"));
        cdpGraph.addOrEdge(dict.get("рамках"), dict.get("дела"));
        cdpGraph.addOrEdge(dict.get("финансового"), dict.get("управляющего"));
        cdpGraph.addOrEdge(dict.get("управляющего"), dict.get("об"));
        cdpGraph.addOrEdge(dict.get("утверждении"), dict.get("положения"));
        cdpGraph.addOrEdge(dict.get("положения"), dict.get("о"));
        cdpGraph.addOrEdge(dict.get("порядке"), dict.get("сроках"));
        cdpGraph.addOrEdge(dict.get("сроках"), dict.get("и"));
        cdpGraph.addOrEdge(dict.get("и"), dict.get("об"));
        cdpGraph.addOrEdge(dict.get("привлечении"), dict.get("к"));
        cdpGraph.addOrEdge(dict.get("дело"), dict.get("по"));
        cdpGraph.addOrEdge(dict.get("третье"), dict.get("лицо"));
        cdpGraph.addOrEdge(dict.get("третьи"), dict.get("лица"));
        cdpGraph.addOrEdge(dict.get("материалы"), dict.get("дела"));
        cdpGraph.addOrEdge(dict.get("не"), dict.get("заявляющее"));
        cdpGraph.addOrEdge(dict.get("заявляющее"), dict.get("самостоятельных"));
        cdpGraph.addOrEdge(dict.get("самостоятельных"), dict.get("требований"));
        cdpGraph.addOrEdge(dict.get("относительно"), dict.get("предмета"));
        cdpGraph.addOrEdge(dict.get("предмета"), dict.get("спора"));
        cdpGraph.addOrEdge(dict.get("спора"), dict.get("конкурсный"));
        cdpGraph.addOrEdge(dict.get("конкурсный"), dict.get("управляющий"));
        cdpGraph.addOrEdge(dict.get("задолженности"), dict.get("в"));
        //#endregion
        
        //#region Constant to Multiple constant literal
        cdpGraph.addOrEdge(dict.get("взыскании"), 
            dict.get("задолженности"),
            dict.get("с"));

        cdpGraph.addOrEdge(dict.get("требований"), 
            dict.get("относительно"), 
            dict.get("кредиторов"));

        cdpGraph.addOrEdge(dict.get("дела"), 
            dict.get("по"),
            dict.get("о"));

        cdpGraph.addOrEdge(dict.get("по"), 
            dict.get("иску"),
            dict.get("делу"),
            dict.get("заявлению"));

        cdpGraph.addOrEdge(dict.get("заседании"), 
            dict.get("материалы"),
            dict.get("заявление"),
            dict.get("дело"));

        cdpGraph.addOrEdge(dict.get("об"), 
            dict.get("утверждении"),
            dict.get("условиях"));

        cdpGraph.addOrEdge(dict.get("кредиторов"), 
            dict.get("по"), dict.get("в"));
            
        cdpGraph.addOrEdge(dict.get("в"), 
            dict.get("рамках"),
            dict.get("открытом"), 
            dict.get("судебном"), 
            dict.get("реестре"), 
            dict.get("реестр"), 
            dict.get("размере"));
        //#endregion
        
        //#region Constant literal to token
        cdpGraph.addOrEdge(dict.get("заявлением"), dict.get(complainantToken));
        cdpGraph.addOrEdge(dict.get("заявлению"), dict.get(complainantToken));
        cdpGraph.addOrEdge(dict.get("иску"), dict.get(complainantToken));
        
        cdpGraph.addOrEdge(dict.get("с"), dict.get(defendantToken));
        cdpGraph.addOrEdge(dict.get("ответчику"), dict.get(defendantToken));
        cdpGraph.addOrEdge(dict.get("признании"), dict.get(defendantToken));
        
        cdpGraph.addOrEdge(dict.get("управляющий"), dict.get(thirdPartyToken));

        cdpGraph.addOrEdge(dict.get("размере"), dict.get(courtCaseSumToken));

        cdpGraph.addOrEdge(dict.get("условиях"), dict.get(keywordToken));
        
        //#region Constant literal to Multiple constant or token
        cdpGraph.addOrEdge(dict.get("к"), 
            dict.get("ответчику"),
            dict.get(defendantToken),
            dict.get(keywordToken));

        cdpGraph.addOrEdge(dict.get("заявление"), 
            dict.get("финансового"), 
            dict.get(complainantToken));

        cdpGraph.addOrEdge(dict.get("о"), 
            dict.get("привлечении"),
            dict.get("порядке"), 
            dict.get("признании"), 
            dict.get("взыскании"), 
            dict.get("включении"), 
            dict.get(keywordToken));

        cdpGraph.addOrEdge(dict.get("лицо"), 
            dict.get(thirdPartyToken), 
            dict.get("не"));
        cdpGraph.addOrEdge(dict.get("лица"), 
            dict.get(thirdPartyToken), 
            dict.get("не"));
        //#endregion

        //#region Token to constant
        cdpGraph.addOrEdge(dict.get(complainantToken), 
        dict.get("к"),
        dict.get("о"));
        
        cdpGraph.addOrEdge(dict.get(thirdPartyToken), dict.get("о"));
        cdpGraph.addOrEdge(dict.get(keywordToken), dict.get("по"));
        //#endregion

        //#region Token to token
        cdpGraph.addOrEdge(dict.get(defendantToken), 
            dict.get("третье"),
            dict.get("третьи"),
            dict.get(keywordToken));
        //#endregion

        return cdpGraph;
    }
    
}
