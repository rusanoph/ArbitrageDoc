package ru.idr.arbitragestatistics.model.datastructure;

import java.util.regex.Pattern;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.Vertex;

public class StaticGraphs {

    private static final String PARSE_ALL_BEFORE_ACTION = "(.*?)";
    
    private static final String PARSE_COMPLAINANT_TYPE = "(?<complainantType>.*?)";
    private static final String PARSE_COMPLAINANT = "(?<complainant>.*?)";
    private static final String PARSE_DEFENDANT = "(?<defendant>.*?)";

    private static final String PARSE_COMPETITION_MANAGER = "(?<competitionManager>.*?)";
    private static final String PARSE_FINANCIAL_MANAGER = "(?<financialManager>.*?)";

    private static final String PARSE_KEYWORD = "(?<keyword>.*?)";
    private static final String PARSE_PERSON_ID = "(?<personId>.*?)";
    private static final String PARSE_PERSON_ADDRESS = "(?<personAddress>.*?)";
    private static final String PARSE_COURT_CASE_SUM = "(?<courtCaseSum>"+RegExRepository.regexCourtCaseSum+")";
 
    public static Graph<String> getCdpGraph_test() {

        Graph<String> cdpGraph = new Graph<>();

        Vertex<String> v1 = new Vertex<String>("рассмотрев");
        
        var v2 = new Vertex<String>("в");

        var v3 = new Vertex<String>("открытом");
        var v4 = new Vertex<String>("реестре");


        cdpGraph.addOrEdge(v1, v2);

        cdpGraph.addOrEdge(v1, v3)
        .addOrEdge(v2, v4);


        return cdpGraph;
    }

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
        .addVertex(new Vertex<String>(wrapStaticTokenAsRegex("положения о порядке, сроках и об условиях продажи имущества должника")))
        .addOrEdge("утверждении", wrapStaticTokenAsRegex("положения о порядке, сроках и об условиях продажи имущества должника"))

        // Level 11
        .nextDepthLevel()
        .addVertex(new Vertex<String>("по"))
        .addOrEdge(1, "рассмотрев", 11, "по")
        .addOrEdge(1, "ознакомившись", 11, "по")
        .addOrEdge(6, "дело", 11, "по")
        .addOrEdge(7, "дела", 11, "по")
        .addOrEdge(wrapStaticTokenAsRegex("положения о порядке, сроках и об условиях продажи имущества должника"), "по")
        
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
        .addVertex(new Vertex<String>(PARSE_COMPLAINANT_TYPE+wrapStaticTokenAsRegex("-")+PARSE_COMPLAINANT+wrapWordAsRegex("(о|к)")).setAction(Vertex::noAction))
        .addOrEdge(3, "заявлением", 16, PARSE_COMPLAINANT_TYPE+wrapStaticTokenAsRegex("-")+PARSE_COMPLAINANT+wrapWordAsRegex("(о|к)"))
        .addOrEdge(6, "заявление", 16, PARSE_COMPLAINANT_TYPE+wrapStaticTokenAsRegex("-")+PARSE_COMPLAINANT+wrapWordAsRegex("(о|к)"))
        
        .addVertex(new Vertex<String>(PARSE_COMPLAINANT+wrapWordAsRegex("(о|к)")).setAction(Vertex::noAction))
        .addOrEdge(3, "заявлением", 16, PARSE_COMPLAINANT+wrapWordAsRegex("(о|к)"))
        .addOrEdge(6, "заявление", 16, PARSE_COMPLAINANT+wrapWordAsRegex("(о|к)"))
        .addOrEdge(13, "иску", 16, PARSE_COMPLAINANT+wrapWordAsRegex("(о|к)"))
        .addOrEdge(13, "заявлению", 16, PARSE_COMPLAINANT+wrapWordAsRegex("(о|к)"))

        // Level 17
        .nextDepthLevel()
        .addVertex(new Vertex<String>("ответчику"))
        .addOrEdge(PARSE_COMPLAINANT+wrapWordAsRegex("(о|к)"), "ответчику")
        

        // Level 18
        .nextDepthLevel()
        .addVertex(new Vertex<String>(PARSE_DEFENDANT+wrapWordAsRegex("третье лицо(,|:)?")))
        .addOrEdge("ответчику", PARSE_DEFENDANT+wrapWordAsRegex("третье лицо(,|:)?"))
        .addOrEdge(16, PARSE_COMPLAINANT+wrapWordAsRegex("(о|к)"), 18, PARSE_DEFENDANT+wrapWordAsRegex("третье лицо(,|:)?"))

        .addVertex(new Vertex<String>(PARSE_KEYWORD+wrapWordAsRegex("(в|с)")).setAction(Vertex::noAction))
        .addOrEdge(16, PARSE_COMPLAINANT_TYPE+wrapStaticTokenAsRegex("-")+PARSE_COMPLAINANT+wrapWordAsRegex("(о|к)"), 18, PARSE_KEYWORD+wrapWordAsRegex("(в|с)"))
        .addOrEdge(16, PARSE_COMPLAINANT+wrapWordAsRegex("(о|к)"), 18, PARSE_KEYWORD+wrapWordAsRegex("(в|с)"))    
        
        // Level 19
        .nextDepthLevel()
        .addVertex(new Vertex<String>(wrapStaticTokenAsRegex("(не)? (заявляющее)? (самостоятельных)? (требований)? (относительно)? (предмета)? (спора)?")))
        .addOrEdge(PARSE_DEFENDANT+wrapWordAsRegex("третье лицо(,|:)?"), wrapStaticTokenAsRegex("(не)? (заявляющее)? (самостоятельных)? (требований)? (относительно)? (предмета)? (спора)?"))

        .addVertex(new Vertex<String>("реестре?"))
        .addOrEdge(PARSE_KEYWORD+wrapWordAsRegex("(в|с)"), "реестре?")
        
        .addVertex(new Vertex<String>(PARSE_DEFENDANT+wrapWordAsRegex("финансовых санкций,?")))
        .addOrEdge(PARSE_KEYWORD+wrapWordAsRegex("(в|с)"),  PARSE_DEFENDANT+wrapWordAsRegex("финансовых санкций,?"))

        // Level 20
        .nextDepthLevel()
        .addVertex(new Vertex<String>(wrapStaticTokenAsRegex("(конкурсный)? управляющий")))
        .addOrEdge(wrapStaticTokenAsRegex("(не)? (заявляющее)? (самостоятельных)? (требований)? (относительно)? (предмета)? (спора)?"), wrapStaticTokenAsRegex("(конкурсный)? управляющий"))

        .addVertex(new Vertex<String>(wrapStaticTokenAsRegex("(финансовый)? управляющий")))
        .addOrEdge(wrapStaticTokenAsRegex("(не)? (заявляющее)? (самостоятельных)? (требований)? (относительно)? (предмета)? (спора)?"), wrapStaticTokenAsRegex("(финансовый)? управляющий"))

        .addVertex(new Vertex<String>("требований"))
        .addOrEdge("реестре?", "требований")

        // Level 21
        .nextDepthLevel()
        .addVertex(new Vertex<String>(PARSE_COMPETITION_MANAGER+wrapWordAsRegex("о")))
        .addOrEdge(wrapStaticTokenAsRegex("(конкурсный)? управляющий"), PARSE_COMPETITION_MANAGER+wrapWordAsRegex("о"))

        .addVertex(new Vertex<String>(PARSE_FINANCIAL_MANAGER+wrapWordAsRegex("о")))
        .addOrEdge(wrapStaticTokenAsRegex("(финансовый)? управляющий"), PARSE_FINANCIAL_MANAGER+wrapWordAsRegex("о"))

        .addVertex(new Vertex<String>("кредиторов"))
        .addOrEdge("требований", "кредиторов")

        // Level 22
        .nextDepthLevel()
        .addVertex(new Vertex<String>("взыскании"))
        .addOrEdge(PARSE_FINANCIAL_MANAGER+wrapWordAsRegex("о"), "взыскании")
        .addOrEdge(PARSE_COMPETITION_MANAGER+wrapWordAsRegex("о"), "взыскании")

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
        .addVertex(new Vertex<String>(wrapStaticTokenAsRegex("в размере")+PARSE_COURT_CASE_SUM))
        .addOrEdge("задолженности", wrapStaticTokenAsRegex("в размере")+PARSE_COURT_CASE_SUM)

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
        .addVertex(new Vertex<String>(PARSE_DEFENDANT+wrapWordAsRegex("(несостоятельн(ым|ой) \\(?(банкротом)?\\)?,?)")).setAction(Vertex::noAction))
        .addOrEdge(15, "признании", 27, PARSE_DEFENDANT+wrapWordAsRegex("(несостоятельн(ым|ой) \\(?(банкротом)?\\)?,?)"))
        .addOrEdge("признании", PARSE_DEFENDANT+wrapWordAsRegex("(несостоятельн(ым|ой) \\(?(банкротом)?\\)?,?)"))

        // Level 28
        .nextDepthLevel()
        .addVertex(new Vertex<String>("при участии в заседании"))
        .addOrEdge(PARSE_DEFENDANT+wrapWordAsRegex("(несостоятельн(ым|ой) \\(?(банкротом)?\\)?,?)"), "при участии в заседании")

        // Level 29
        .nextDepthLevel()
        .addVertex(new Vertex<String>("согласно протоколу судебного заседания"))
        .addOrEdge("при участии в заседании", "согласно протоколу судебного заседания")
        ;

        //#endregion

        return cdpGraph;
    }

    public static Graph<String> getCdpGraph_v1() {
        Graph<String> cdpGraph = new Graph<>();

        cdpGraph
        .addVertex(new Vertex<String>("Initial"))
        .nextDepthLevel()  // Level 0
            .addVertex(new Vertex<String>("рассмотрев")).addOrEdge(cdpGraph.getVertexByDepthValue(0, "Initial"), cdpGraph.getVertexByDepthValue(1, "рассмотрев"))
            .addVertex(new Vertex<String>("ознакомившись")).addOrEdge(cdpGraph.getVertexByDepthValue(0, "Initial"), cdpGraph.getVertexByDepthValue(1, "ознакомившись"))
            .nextDepthLevel() // Level 1
                .addVertex(new Vertex<String>("в")).addOrEdge(cdpGraph.getVertexByDepthValue(1, "рассмотрев"), cdpGraph.getVertexByDepthValue(2, "в"))
                .addVertex(new Vertex<String>("с")).addOrEdge(cdpGraph.getVertexByDepthValue(1, "ознакомившись"), cdpGraph.getVertexByDepthValue(2, "с"))
                .nextDepthLevel() // Level 2
                    .addVertex(new Vertex<String>("открытом")).addOrEdge(cdpGraph.getVertexByDepthValue(2, "в"), cdpGraph.getVertexByDepthValue(3, "открытом"))
                    .addVertex(new Vertex<String>("заявлением")).addOrEdge(cdpGraph.getVertexByDepthValue(2, "с"), cdpGraph.getVertexByDepthValue(3, "заявлением"))
                    .nextDepthLevel() // Level 3
                        .addVertex(new Vertex<String>("судебном")).addOrEdge(cdpGraph.getVertexByDepthValue(3, "открытом"), cdpGraph.getVertexByDepthValue(4, "судебном"))
                            .addOrEdge(cdpGraph.getVertexByDepthValue(2, "в"), cdpGraph.getVertexByDepthValue(4, "судебном"))
                        .nextDepthLevel() // Level 4
                            .addVertex(new Vertex<String>("заседании")).addOrEdge(cdpGraph.getVertexByDepthValue(4, "судебном"), cdpGraph.getVertexByDepthValue(5, "заседании"))
                            .nextDepthLevel() // Level 5
                                // Action here
                                .addVertex(new Vertex<String>("заявление")).addOrEdge(cdpGraph.getVertexByDepthValue(5, "заседании"), cdpGraph.getVertexByDepthValue(6, "заявление"))
                                .nextDepthLevel() // Level 6
                                    .addVertex(new Vertex<String>("финансового управляющего")).addOrEdge(cdpGraph.getVertexByDepthValue(6, "заявление"), cdpGraph.getVertexByDepthValue(7, "финансового управляющего"))
                                    .nextDepthLevel()
                                        .addVertex(new Vertex<String>("об")).addOrEdge(cdpGraph.getVertexByDepthValue(7, "финансового управляющего"), cdpGraph.getVertexByDepthValue(8, "об"))
        ;

        for (var v : cdpGraph.getVerticesSet()) {
            System.out.println(String.format("(d: %d, v: %s)", v.getDepth(), v.getValue()));
        }

        return cdpGraph;
    }
 
    public static Graph<Pattern> getTestGraph1() {

        Graph<Pattern> testGraph1 = new Graph<>();

        Vertex<Pattern> A1 = new Vertex<Pattern>(regexFromString("рассмотрев|рассматривает|рассмотрел"));
            Vertex<Pattern> B1 = new Vertex<Pattern>(regexFromString("вопрос\\s+"));
                Vertex<Pattern> C1 = new Vertex<Pattern>(regexFromString("о\\s+"));
                    Vertex<Pattern> D1 = new Vertex<Pattern>(regexFromString("принятии\\s+"));                    
                        Vertex<Pattern> E1 = new Vertex<Pattern>(regexFromString("заявления\\s+"));
            Vertex<Pattern> B2 = new Vertex<Pattern>(regexFromString("в\\s+"));
                Vertex<Pattern> C2 = new Vertex<Pattern>(regexFromString("открытом\\s+"));
                Vertex<Pattern> C3 = new Vertex<Pattern>(regexFromString("судебном\\s+"));
                    Vertex<Pattern> D2 = new Vertex<Pattern>(regexFromString("заседании\\s+"));
                Vertex<Pattern> C4 = new Vertex<Pattern>(regexFromString("порядке\\s+"));

        testGraph1
        .addVertex(A1)
            .addVertex(B1).addOrEdge(A1, B1)
                .addVertex(C1).addOrEdge(B1, C1)
                    .addVertex(D1).addOrEdge(C1, D1)
                        .addVertex(E1).addOrEdge(D1, E1)
            .addVertex(B2).addOrEdge(A1, B2)
                .addVertex(C2).addOrEdge(B2, C2)
                .addVertex(C3).addOrEdge(B2, C3)
                    .addVertex(D2).addOrEdge(C3, D2)
                .addVertex(C4).addOrEdge(B2, C4)
        ;
        

        return testGraph1;
    } 

    public static Graph<String> getTestGraph2() {
        Graph<String> testGraph2 = new Graph<>();

        testGraph2
        .addVertex(new Vertex<String>("Initial"))
        .nextDepthLevel()
            .addVertex(new Vertex<String>("A1"))
            .addOrEdge(
                testGraph2.getVertexByDepthValue(0, "Initial"), 
                testGraph2.getVertexByDepthValue(1, "A1")
            )
            .nextDepthLevel()
                .addVertex(new Vertex<String>("B1"))
                .addOrEdge(
                    testGraph2.getVertexByDepthValue(1, "A1"), 
                    testGraph2.getVertexByDepthValue(2, "B1")
                )
                .addVertex(new Vertex<String>("B2"))
                .addOrEdge(
                    testGraph2.getVertexByDepthValue(1, "A1"),
                    testGraph2.getVertexByDepthValue(2, "B2")
                )
                .nextDepthLevel()
                    .addVertex(new Vertex<String>("C1"))
                    .addOrEdge(
                        testGraph2.getVertexByDepthValue(2, "B1"), 
                        testGraph2.getVertexByDepthValue(3, "C1")    
                    )
                    .addOrEdge(
                        testGraph2.getVertexByDepthValue(2, "B2"), 
                        testGraph2.getVertexByDepthValue(3, "C1")    
                    )
                    .addVertex(new Vertex<String>("C2"))
                    .addOrEdge(
                        testGraph2.getVertexByDepthValue(2, "B1"), 
                        testGraph2.getVertexByDepthValue(3, "C2")    
                    )
                    .addOrEdge(
                        testGraph2.getVertexByDepthValue(2, "B2"), 
                        testGraph2.getVertexByDepthValue(3, "C2")    
                    )
                    .nextDepthLevel()
                        .addVertex(new Vertex<String>("D1"))
                        .addVertex(new Vertex<String>("D2"))
                        .addVertex(new Vertex<String>("D3"))
                        .addOrEdge(
                            testGraph2.getVertexByDepthValue(3, "C1"), 
                            testGraph2.getVertexByDepthValue(4, "D1")    
                        )
                        .addOrEdge(
                            testGraph2.getVertexByDepthValue(3, "C1"), 
                            testGraph2.getVertexByDepthValue(4, "D2")    
                        )
                        .addOrEdge(
                            testGraph2.getVertexByDepthValue(3, "C1"), 
                            testGraph2.getVertexByDepthValue(4, "D3")    
                        )
                        .addOrEdge(
                            testGraph2.getVertexByDepthValue(3, "C2"), 
                            testGraph2.getVertexByDepthValue(4, "D2")    
                        )
        ;

        return testGraph2;
    }


    //#region === Regex Helpers ===
    private static Pattern regexFromString(String string) {
        return Pattern.compile(string, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    }

    private static String wrapWordAsRegex(String word) {
        word = word.replaceAll(" ", "\\\\s+");
        return String.format("(^|\\s+)%s(\\s+|$)", word, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    }

    private static String wrapStaticTokenAsRegex(String staticToken) {
        staticToken = staticToken.replaceAll(" ", "\\\\s+");
        return String.format("(^|\\s*)%s(\\s*|$)", staticToken, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    }
    //#endregion
}
