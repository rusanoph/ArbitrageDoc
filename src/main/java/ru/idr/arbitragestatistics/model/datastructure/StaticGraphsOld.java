package ru.idr.arbitragestatistics.model.datastructure;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.Vertex;

public class StaticGraphsOld {
    private static final String PARSE_COMPLAINANT_TYPE = "(?<complainantType>.*?)";
    private static final String PARSE_COMPLAINANT = "(?<complainant>.*?)";
    private static final String PARSE_DEFENDANT = "(?<defendant>.*?)";
    private static final String PARSE_THIRD_PARTY = "(?<thirdParty>.*?)";

    private static final String PARSE_COMPETITION_MANAGER = "(?<competitionManager>.*?)";
    private static final String PARSE_FINANCIAL_MANAGER = "(?<financialManager>.*?)";

    // private static final String PARSE_PERSON_ID = "(?<personId>.*?)";
    // private static final String PARSE_PERSON_ADDRESS = "(?<personAddress>.*?)";
    private static final String PARSE_COURT_CASE_SUM = "(?<courtCaseSum>"+RegExRepository.regexCourtCaseSum+")";

    private static final String PARSE_KEYWORD = "(?<keyword>.*?)";

    public static Graph<String> getCdpGraph() {
        // Replace all Braces with '\\'Brace to correct regex interpretation

        Graph<String> cdpGraph = new Graph<>();

        // #region Part before any action
        cdpGraph
        .addVertex(new Vertex<String>("Initial"))
        // Level 1
        .nextDepthLevel()  
        .addVertex(new Vertex<String>("рассмотрев"))
        .addOrEdge(0, "Initial", 1, "рассмотрев")

        .addVertex(new Vertex<String>("ознакомившись"))
        .addOrEdge(0, "Initial", 1, "ознакомившись")

        // Level 2
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("без"))
        .addOrEdge("рассмотрев", "без")

        .addVertex(new Vertex<String>("в"))
        .addOrEdge(1, "рассмотрев", 2, "в")

        .addVertex(new Vertex<String>("с"))
        .addOrEdge(1, "ознакомившись", 2, "с")

        // Level 3
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("вызова"))
        .addOrEdge("без", "вызова")

        .addVertex(new Vertex<String>("ходатайством"))
        .addOrEdge("с", "ходатайством")

        .addVertex(new Vertex<String>("рамках"))
        .addOrEdge("в", "рамках")

        .addVertex(new Vertex<String>("поданным"))
        .addOrEdge("с", "поданным")
        .addOrEdge(3, "поданным", 2, "в") // Reverse Link (From high-level to low-level)

        .addVertex(new Vertex<String>("открытом"))
        .addOrEdge(2, "в", 3, "открытом")

        .addVertex(new Vertex<String>("предварительном"))
        .addOrEdge("в", "предварительном")

        .addVertex(new Vertex<String>("заявлением"))
        .addOrEdge(2, "с", 3, "заявлением")

        // Level 4
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("сторон"))
        .addOrEdge("вызова", "сторон")

        .addVertex(new Vertex<String>("финансового управляющего"))
        .addOrEdge("ходатайством", "финансового управляющего")

        .addVertex(new Vertex<String>("судебном"))
        .addOrEdge("предварительном", "судебном")
        .addOrEdge(3, "открытом", 4, "судебном")
        .addOrEdge(2, "в", 4, "судебном")

        // Level 5
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("заседании"))
        .addOrEdge(4, "судебном", 5, "заседании")


        // Level 6
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("в"))
        .addOrEdge("заседании", "в")
        .addOrEdge(6, "в", 3,  "рамках") // Reverse Link (From high-level to low-level)

        .addVertex(new Vertex<String>("дело"))
        .addOrEdge("заседании", "дело")

        .addVertex(new Vertex<String>("заявление"))
        .addOrEdge(1, "рассмотрев", 6, "заявление")
        .addOrEdge(5, "заседании", 6, "заявление")

        .addVertex(new Vertex<String>("материалы"))
        .addOrEdge(4, "сторон", 6, "материалы")
        .addOrEdge("заседании", "материалы")

        // Level 7
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("конкурсного управляющего").setAction(Vertex::noAction))
        .addOrEdge("заявление", "конкурсного управляющего")

        .addVertex(new Vertex<String>("финансового управляющего"))
        .addOrEdge(6, "заявление", 7, "финансового управляющего")

        .addVertex(new Vertex<String>("взыскателя").setAction(Vertex::noAction))
        .addOrEdge("заявление", "взыскателя")

        .addVertex(new Vertex<String>("дела"))
        .addOrEdge(3, "рамках", 7, "дела")
        .addOrEdge("материалы", "дела")

        // Level 8
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("об"))
        .addOrEdge(7, "финансового управляющего", 8, "об")

        // Level 9
        .nextDepthLevel()
        .addVertex(new Vertex<String>("утверждении"))
        .addOrEdge("об", "утверждении")

        // Level 10
        .nextDepthLevel()
        .addVertex(new Vertex<String>(RegExRepository.wrapStaticTokenAsRegex("положения о порядке, сроках и об условиях продажи имущества должника")))
        .addOrEdge("утверждении", RegExRepository.wrapStaticTokenAsRegex("положения о порядке, сроках и об условиях продажи имущества должника"))

        // Level 11
        .nextDepthLevel()
        .addVertex(new Vertex<String>("по"))
        .addOrEdge(1, "рассмотрев", 11, "по")
        .addOrEdge(1, "ознакомившись", 11, "по")
        .addOrEdge(6, "дело", 11, "по")
        .addOrEdge(7, "дела", 11, "по")
        .addOrEdge(RegExRepository.wrapStaticTokenAsRegex("положения о порядке, сроках и об условиях продажи имущества должника"), "по")
        
        // Level 12
        .nextDepthLevel()
        .addVertex(new Vertex<String>("исковому"))
        .addOrEdge("по", "исковому")


        // Level 13
        .nextDepthLevel()
        .addVertex(new Vertex<String>("заявлению"))
        .addOrEdge(11, "по", 13, "заявлению")
        .addOrEdge("исковому", "заявлению")

        .addVertex(new Vertex<String>("иску").setAction(Vertex::noAction))
        .addOrEdge(11, "по", 13, "иску")

        .addVertex(new Vertex<String>("делу"))
        .addOrEdge(11, "по", 13, "делу")

        // Level 14
        .nextDepthLevel()
        .addVertex(new Vertex<String>("(о|к)"))
        .addOrEdge(7, "дела", 14, "(о|к)")
        .addOrEdge("делу", "(о|к)")

        // Level 15
        .nextDepthLevel()
        .addVertex(new Vertex<String>("несостоятельности (банкротстве)").setAction(Vertex::noAction))
        .addOrEdge("(о|к)", "несостоятельности (банкротстве)")

        .addVertex(new Vertex<String>("банкротстве").setAction(Vertex::noAction))
        .addOrEdge("(о|к)", "банкротстве")

        .addVertex(new Vertex<String>("признании"))
        .addOrEdge("(о|к)", "признании")
        ;
        //#endregion
        
        //#region 
        
        cdpGraph
        // Level 16
        .nextDepthLevel()
        .addVertex(new Vertex<String>(PARSE_COMPLAINANT_TYPE+RegExRepository.wrapStaticTokenAsRegex("-")+PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        .setAction(Vertex::noAction)) // NI - Action must extract adress (postcode, street, city, region), ogrn/ogrnip, inn
        .addOrEdge(3, "заявлением", 16, PARSE_COMPLAINANT_TYPE+RegExRepository.wrapStaticTokenAsRegex("-")+PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        .addOrEdge(6, "заявление", 16, PARSE_COMPLAINANT_TYPE+RegExRepository.wrapStaticTokenAsRegex("-")+PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        
        .addVertex(new Vertex<String>(PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        .setAction(Vertex::noAction)) // NI - Action must extract adress (postcode, street, city, region), ogrn/ogrnip, inn
        .addOrEdge(3, "заявлением", 16, PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        .addOrEdge(6, "заявление", 16, PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        .addOrEdge(13, "иску", 16, PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        .addOrEdge(13, "заявлению", 16, PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))

        // Level 17
        .nextDepthLevel()
        .addVertex(new Vertex<String>("ответчику"))
        .addOrEdge(PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"), "ответчику")
        

        // Level 18
        .nextDepthLevel()
        .addVertex(new Vertex<String>(PARSE_DEFENDANT+RegExRepository.wrapStaticTokenAsRegex("(?=третье лицо(,|:)?)"))
        .setAction(Vertex::noAction)) // NI - Action must extract adress (postcode, street, city, region), ogrn/ogrnip, inn
        .addOrEdge("ответчику", PARSE_DEFENDANT+RegExRepository.wrapStaticTokenAsRegex("(?=третье лицо(,|:)?)"))
        .addOrEdge(16, PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"), 18, PARSE_DEFENDANT+RegExRepository.wrapStaticTokenAsRegex("(?=третье лицо(,|:)?)"))

        .addVertex(new Vertex<String>(PARSE_KEYWORD+RegExRepository.wrapWordAsRegex("(в|с)")).setAction(Vertex::noAction))
        .addOrEdge(16, PARSE_COMPLAINANT_TYPE+RegExRepository.wrapStaticTokenAsRegex("-")+PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"), 18, PARSE_KEYWORD+RegExRepository.wrapWordAsRegex("(в|с)"))
        .addOrEdge(16, PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"), 18, PARSE_KEYWORD+RegExRepository.wrapWordAsRegex("(в|с)"))    
        
        // Level 19
        .nextDepthLevel()
        .addVertex(new Vertex<String>(RegExRepository.wrapStaticTokenAsRegex("(не)? (заявляющее)? (самостоятельных)? (требований)? (относительно)? (предмета)? (спора)?")))
        .addOrEdge(PARSE_DEFENDANT+RegExRepository.wrapStaticTokenAsRegex("(?=третье лицо(,|:)?)"), RegExRepository.wrapStaticTokenAsRegex("(не)? (заявляющее)? (самостоятельных)? (требований)? (относительно)? (предмета)? (спора)?"))

        .addVertex(new Vertex<String>("реестре?"))
        .addOrEdge(PARSE_KEYWORD+RegExRepository.wrapWordAsRegex("(в|с)"), "реестре?")
        
        .addVertex(new Vertex<String>(PARSE_DEFENDANT+RegExRepository.wrapWordAsRegex("финансовых санкций,?")))
        .addOrEdge(PARSE_KEYWORD+RegExRepository.wrapWordAsRegex("(в|с)"),  PARSE_DEFENDANT+RegExRepository.wrapWordAsRegex("финансовых санкций,?"))

        .addVertex(new Vertex<String>("третье лицо(,|:)?"+PARSE_THIRD_PARTY+"(?="+RegExRepository.wrapWordAsRegex("о")+")")
        .setAction(Vertex::noAction)) // NI - Action must extract ogrn/ogrnip, inn, adress
        .addOrEdge(PARSE_DEFENDANT+RegExRepository.wrapStaticTokenAsRegex("(?=третье лицо(,|:)?)"), "третье лицо(,|:)?"+PARSE_THIRD_PARTY+"(?="+RegExRepository.wrapWordAsRegex("о")+")")

        // Level 20
        .nextDepthLevel()
        .addVertex(new Vertex<String>(RegExRepository.wrapStaticTokenAsRegex("(конкурсный)? управляющий")))
        .addOrEdge(RegExRepository.wrapStaticTokenAsRegex("(не)? (заявляющее)? (самостоятельных)? (требований)? (относительно)? (предмета)? (спора)?"), RegExRepository.wrapStaticTokenAsRegex("(конкурсный)? управляющий"))

        .addVertex(new Vertex<String>(RegExRepository.wrapStaticTokenAsRegex("(финансовый)? управляющий")))
        .addOrEdge(RegExRepository.wrapStaticTokenAsRegex("(не)? (заявляющее)? (самостоятельных)? (требований)? (относительно)? (предмета)? (спора)?"), RegExRepository.wrapStaticTokenAsRegex("(финансовый)? управляющий"))

        .addVertex(new Vertex<String>("требований"))
        .addOrEdge("реестре?", "требований")

        // Level 21
        .nextDepthLevel()
        .addVertex(new Vertex<String>(PARSE_COMPETITION_MANAGER+RegExRepository.wrapWordAsRegex("о")))
        .addOrEdge(RegExRepository.wrapStaticTokenAsRegex("(конкурсный)? управляющий"), PARSE_COMPETITION_MANAGER+RegExRepository.wrapWordAsRegex("о"))

        .addVertex(new Vertex<String>(PARSE_FINANCIAL_MANAGER+RegExRepository.wrapWordAsRegex("о")))
        .addOrEdge(RegExRepository.wrapStaticTokenAsRegex("(финансовый)? управляющий"), PARSE_FINANCIAL_MANAGER+RegExRepository.wrapWordAsRegex("о"))

        .addVertex(new Vertex<String>("кредиторов"))
        .addOrEdge("требований", "кредиторов")

        // Level 22
        .nextDepthLevel()
        .addVertex(new Vertex<String>("взыскании"))
        .addOrEdge(PARSE_FINANCIAL_MANAGER+RegExRepository.wrapWordAsRegex("о"), "взыскании")
        .addOrEdge(PARSE_COMPETITION_MANAGER+RegExRepository.wrapWordAsRegex("о"), "взыскании")

        .addVertex(new Vertex<String>("по"))
        .addOrEdge("кредиторов", "по")

        .addVertex(new Vertex<String>("в"))
        .addOrEdge("кредиторов", "в")

        // Level 23
        .nextDepthLevel()
        .addVertex(new Vertex<String>("задолженности"))
        .addOrEdge("взыскании", "задолженности")

        .addVertex(new Vertex<String>("делу"))
        .addOrEdge("по", "делу")

        .addVertex(new Vertex<String>("рамках"))
        .addOrEdge("в", "рамках")

        // Level 24
        .nextDepthLevel()
        .addVertex(new Vertex<String>(RegExRepository.wrapStaticTokenAsRegex("в размере")+PARSE_COURT_CASE_SUM)
        .setAction(Vertex::noAction)) // NI - Split sum by rub and kop
        .addOrEdge("задолженности", RegExRepository.wrapStaticTokenAsRegex("в размере")+PARSE_COURT_CASE_SUM)

        .addVertex(new Vertex<String>("дела"))
        .addOrEdge("рамках", "дела")

        // Level 25
        .nextDepthLevel()
        .addVertex(new Vertex<String>("(о|к)"))
        .addOrEdge(23, "делу", 25, "(о|к)")
        .addOrEdge("дела", "(о|к)")

        // Level 26
        .nextDepthLevel()
        .addVertex(new Vertex<String>("признании"))
        .addOrEdge("(о|к)", "признании")

        // Level 27
        .nextDepthLevel()
        .addVertex(new Vertex<String>(PARSE_DEFENDANT+RegExRepository.wrapWordAsRegex("(несостоятельн(ым|ой) \\(?(банкротом)?\\)?,?)")).setAction(Vertex::noAction))
        .addOrEdge(15, "признании", 27, PARSE_DEFENDANT+RegExRepository.wrapWordAsRegex("(несостоятельн(ым|ой) \\(?(банкротом)?\\)?,?)"))
        .addOrEdge("признании", PARSE_DEFENDANT+RegExRepository.wrapWordAsRegex("(несостоятельн(ым|ой) \\(?(банкротом)?\\)?,?)"))

        // Level 28
        .nextDepthLevel()
        .addVertex(new Vertex<String>("при участии в заседании"))
        .addOrEdge(PARSE_DEFENDANT+RegExRepository.wrapWordAsRegex("(несостоятельн(ым|ой) \\(?(банкротом)?\\)?,?)"), "при участии в заседании")

        // Level 29
        .nextDepthLevel()
        .addVertex(new Vertex<String>("согласно протоколу судебного заседания"))
        .addOrEdge("при участии в заседании", "согласно протоколу судебного заседания")
        ;

        //#endregion

        return cdpGraph;
    }
}
