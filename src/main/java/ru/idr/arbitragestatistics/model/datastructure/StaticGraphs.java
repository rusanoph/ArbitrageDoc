package ru.idr.arbitragestatistics.model.datastructure;


import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.idr.arbitragestatistics.helper.regex.RegExBuilder;
import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.ValueVertexMap;
import ru.idr.arbitragestatistics.util.datastructure.Vertex;
import ru.idr.datamarkingeditor.helper.MarkedDataParser;
import ru.idr.datamarkingeditor.model.TokenType;

public class StaticGraphs {

    private static final ValueVertexMap<String> dict = ArbitrageDictionary.getWordMap();
    private static final RegExBuilder rb = new RegExBuilder();
    
    // private static final String PARSE_COMPLAINANT_TYPE = "(?<complainantType>.*?)";

    private static final String PARSE_COMPLAINANT = "(?<Complainant>.+?)";
    private static final String PARSE_DEFENDANT = "(?<Defendant>.+?)";
    private static final String PARSE_THIRD_PARTY = "(?<ThirdParty>.+?)";
    
    private static final String PARSE_PERSON_ID = "(?<PersonId>.+?)";
    private static final String PARSE_PERSON_ADDRESS = "(?<PersonAddress>.+?)";
    private static final String PARSE_COURT_CASE_SUM = "(?<CourtCaseSum>"+RegExRepository.regexCourtCaseSum+")";
    
    private static final String PARSE_COMPETITION_MANAGER = "(?<CompetitionManager>.+?)";
    private static final String PARSE_FINANCIAL_MANAGER = "(?<FinancialManager>.+?)";

    private static final String PARSE_KEYWORD = "(?<Keyword>"+
        "("+
            "(реализации\\s+имущества\\s+должника)" +           "|"+
            "(продаж(и|а)\\s+имущества\\s+должника)" +          "|"+
            "(административной\\s+ответственности)" +           "|"+
            "(финансовых\\s+санкций)" +                         "|"+
            "(несостоятельн(ым|ой)\\s+\\(?банкротом\\)?)" +     "|"+
            "(банкротстве)" + "|"+
            "(замене\\s+кредитора)" +                           "|"+ 
            "(выдаче\\s+судебного\\s+приказа)"+                 "|"+
            "(процессуальном\\s+правопреемстве)"+
        "))";

    //#region Token Getters
    private static String getComplainantToken() {
        String complainantToken = PARSE_COMPLAINANT 
        +rb.set(
            "(" +
            rb.set("(о|к)").asWord().build()+         "|"+
            "ходатайство"+
            ")"
        ).asStaticToken()
        .ends()
        .build();
        
        dict.put(complainantToken);
        dict.get(complainantToken).setAction(Vertex::noAction);

        return complainantToken;
    }

    private static String getDefendantToken() {
        String defendantToken = PARSE_DEFENDANT + 
            rb.set(
                rb.set("(о|к|и)").notEnds("\\s+почтовый").asWord().build() + "|"+
                    "треть(е|и)\\s+лиц(о|а)|"+
                    "несостоятельн(ым|ой)|"+
                    "финансовых\\s+санкций|"+
                    "ходатайство"
                )
            .asStaticToken()
            .ends()
            .build();

        dict.put(defendantToken);
        dict.get(defendantToken).setAction(Vertex::noAction);

        return defendantToken;
    }

    private static String getThirdPartyToken() {
        String thirdPartyToken = PARSE_THIRD_PARTY + rb.set("(о|к)").asWord().ends().build();
        dict.put(thirdPartyToken);
        dict.get(thirdPartyToken).setAction(Vertex::noAction);

        return thirdPartyToken;
    }

    private static String getFinancialManagerToken() {
        String financialManager = PARSE_FINANCIAL_MANAGER + rb.set("(о|к|об)").asWord().ends().build();
        dict.put(financialManager);
        dict.get(financialManager).setAction(Vertex::noAction);

        return financialManager;
    }

    private static String getCourtCaseSumToken() {
        String courtCaseSumToken = PARSE_COURT_CASE_SUM;
        dict.put(courtCaseSumToken);
        dict.get(courtCaseSumToken).setAction(Vertex::noAction);

        return courtCaseSumToken;
    }
    private static String getKeywordToken() {
        String keywordToken = PARSE_KEYWORD;
        dict.put(keywordToken);
        dict.get(keywordToken).setAction(Vertex::noAction);

        return keywordToken;
    }    
    //#endregion
    
    // === Refactor from static to instantiable object form
    // === Move cdpGraph parsing algorithm from Arbitrage Template Seeker to this

    public static Graph<String> getCdpGraph() {
        Graph<String> cdpGraph = new Graph<>();

        String complainantToken = getComplainantToken();
        String defendantToken = getDefendantToken();
        String thirdPartyToken = getThirdPartyToken();
        String financialManager = getFinancialManagerToken();
        String courtCaseSumToken = getCourtCaseSumToken();
        String keywordToken = getKeywordToken();

        for (var word : dict.keySet()) {
            cdpGraph.addVertex(dict.get(word));
        }

        
        cdpGraph.addOrEdge(dict.get("Initial"), 
            dict.get("рассмотрев"), dict.get("ознакомившись"));

        //#region Constant to Constant literal
        cdpGraph.addOrEdge(dict.get("открытом"), dict.get("судебном"));
        cdpGraph.addOrEdge(dict.get("судебном"), dict.get("заседании"));
        cdpGraph.addOrEdge(dict.get("реестре"), dict.get("требований"));
        cdpGraph.addOrEdge(dict.get("делу"), dict.get("о"));
        cdpGraph.addOrEdge(dict.get("с"), dict.get("заявлением"));
        cdpGraph.addOrEdge(dict.get("включении"), dict.get("в"));
        cdpGraph.addOrEdge(dict.get("реестр"), dict.get("требований"));
        cdpGraph.addOrEdge(dict.get("рамках"), dict.get("дела"));
        cdpGraph.addOrEdge(dict.get("финансового"), dict.get("управляющего"));
        cdpGraph.addOrEdge(dict.get("утверждении"), dict.get("положения"));
        cdpGraph.addOrEdge(dict.get("положения"), dict.get("о"));
        cdpGraph.addOrEdge(dict.get("порядке"), dict.get("сроках"));
        cdpGraph.addOrEdge(dict.get("сроках"), dict.get("и"));
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
        cdpGraph.addOrEdge(dict.get("ходатайство"), dict.get("финансового"));
        cdpGraph.addOrEdge(dict.get("приложенными"), dict.get("документами"));
        //#endregion
        
        //#region Constant to Multiple constant literal
        cdpGraph.addOrEdge(dict.get("и"), 
            dict.get("приложенными"),
            dict.get("условиях"),
            dict.get("об"));

        cdpGraph.addOrEdge(dict.get("ознакомившись"), 
            dict.get("по"),
            dict.get("с"));

        cdpGraph.addOrEdge(dict.get("рассмотрев"), 
            dict.get("взыскателя"),
            dict.get("в"));

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
        cdpGraph.addOrEdge(dict.get("взыскателя"), dict.get(complainantToken));
        
        cdpGraph.addOrEdge(dict.get("с"), dict.get(defendantToken));
        cdpGraph.addOrEdge(dict.get("ответчику"), dict.get(defendantToken));
        cdpGraph.addOrEdge(dict.get("должнику"), dict.get(defendantToken));
        cdpGraph.addOrEdge(dict.get("признании"), dict.get(defendantToken));
        
        cdpGraph.addOrEdge(dict.get("управляющий"), dict.get(thirdPartyToken));
        

        cdpGraph.addOrEdge(dict.get("размере"), dict.get(courtCaseSumToken));

        cdpGraph.addOrEdge(dict.get("условиях"), dict.get(keywordToken));
        
        //#region Constant literal to Multiple constant or token
        cdpGraph.addOrEdge(dict.get("управляющего"), 
            dict.get("об"),
            dict.get(financialManager));


        cdpGraph.addOrEdge(dict.get("к"), 
            dict.get("должнику"),
            dict.get("ответчику"),
            dict.get(defendantToken),
            dict.get(keywordToken));

        cdpGraph.addOrEdge(dict.get("заявление"), 
            dict.get("взыскателя"),
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
            dict.get("не"),
            dict.get(thirdPartyToken));
        cdpGraph.addOrEdge(dict.get("лица"), 
            dict.get("не"),
            dict.get(thirdPartyToken));
        //#endregion

        //#region Token to constant
        cdpGraph.addOrEdge(dict.get(complainantToken), 
            dict.get("ходатайство"),
            dict.get("к"),
            dict.get("о"));
            
        cdpGraph.addOrEdge(dict.get(thirdPartyToken), dict.get("о"));

        cdpGraph.addOrEdge(dict.get(financialManager), dict.get("об"));

        //#endregion
        
        //#region Token to token
        cdpGraph.addOrEdge(dict.get(keywordToken), 
            dict.get("в"),
            dict.get("по"),
            dict.get(defendantToken),
            dict.get(complainantToken));

        cdpGraph.addOrEdge(dict.get(defendantToken), 
            dict.get("ходатайство"),
            dict.get("и"),
            dict.get("о"),
            dict.get("третье"),
            dict.get("третьи"),
            dict.get(keywordToken));
        //#endregion

        return cdpGraph;
    }
    

    
}
