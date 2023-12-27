package ru.idr.arbitragestatistics.model.datastructure;

import java.util.regex.Pattern;

import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.Vertex;

public class StaticGraphs {

    private static final String PARSE_ALL_BEFORE_ACTION = "(.*?)"; 

    public static Graph<String> getCdpGraph() {
        // Replace all Braces with '\\'Brace to correct regex interpretation

        Graph<String> cdpGraph = new Graph<>();

        // #region Part before any action
        cdpGraph
        .addVertex(new Vertex<String>("Initial"))
        // Level 1
        .nextDepthLevel()  
        .addVertex(new Vertex<String>("рассмотрев"))
        .addOrientedEdge(0, "Initial", 1, "рассмотрев")

        .addVertex(new Vertex<String>("ознакомившись"))
        .addOrientedEdge(0, "Initial", 1, "ознакомившись")

        // Level 2
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("без"))
        .addOrientedEdge("рассмотрев", "без")

        .addVertex(new Vertex<String>("заявление"))
        .addOrientedEdge("рассмотрев", "заявление")

        .addVertex(new Vertex<String>("в"))
        .addOrientedEdge(1, "рассмотрев", 2, "в")

        .addVertex(new Vertex<String>("с"))
        .addOrientedEdge(1, "ознакомившись", 2, "с")

        // Level 3
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("вызова"))
        .addOrientedEdge("без", "вызова")

        .addVertex(new Vertex<String>("ходатайством"))
        .addOrientedEdge("с", "ходатайством")

        .addVertex(new Vertex<String>("рамках"))
        .addOrientedEdge("в", "рамках")

        .addVertex(new Vertex<String>("поданным"))
        .addOrientedEdge("с", "поданным")
        .addOrientedEdge(3, "поданным", 2, "в") // Reverse Link (From high-level to low-level)

        .addVertex(new Vertex<String>("взыскателя").setAction(Vertex::noAction))
        .addOrientedEdge("заявление", "взыскателя")

        .addVertex(new Vertex<String>("открытом"))
        .addOrientedEdge(2, "в", 3, "открытом")

        .addVertex(new Vertex<String>("предварительном"))
        .addOrientedEdge("в", "предварительном")

        .addVertex(new Vertex<String>("заявлением").setAction(Vertex::noAction))
        .addOrientedEdge(2, "с", 3, "заявлением")

        // Level 4
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("сторон"))
        .addOrientedEdge("вызова", "сторон")

        .addVertex(new Vertex<String>("финансового управляющего"))
        .addOrientedEdge("ходатайством", "финансового управляющего")

        .addVertex(new Vertex<String>("судебном"))
        .addOrientedEdge("предварительном", "судебном")
        .addOrientedEdge(3, "открытом", 4, "судебном")
        .addOrientedEdge(2, "в", 4, "судебном")

        // Level 5
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("заседании"))
        .addOrientedEdge(4, "судебном", 5, "заседании")


        // Level 6
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("в"))
        .addOrientedEdge("заседании", "в")
        .addOrientedEdge(6, "в", 3,  "рамках") // Reverse Link (From high-level to low-level)

        .addVertex(new Vertex<String>("дело"))
        .addOrientedEdge("заседании", "дело")

        .addVertex(new Vertex<String>("заявление"))
        .addOrientedEdge(5, "заседании", 6, "заявление")

        .addVertex(new Vertex<String>("материалы"))
        .addOrientedEdge(4, "сторон", 6, "материалы")
        .addOrientedEdge("заседании", "материалы")

        // Level 7
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("конкурсного управляющего").setAction(Vertex::noAction))
        .addOrientedEdge("заявление", "конкурсного управляющего")

        .addVertex(new Vertex<String>("финансового управляющего").setAction(Vertex::noAction))
        .addOrientedEdge(6, "заявление", 7, "финансового управляющего")

        .addVertex(new Vertex<String>("дела"))
        .addOrientedEdge(3, "рамках", 7, "дела")
        .addOrientedEdge("материалы", "дела")

        // Level 8
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("об"))
        .addOrientedEdge(7, "финансового управляющего", 8, "об")

        // Level 9
        .nextDepthLevel()
        .addVertex(new Vertex<String>("утверждении"))
        .addOrientedEdge("об", "утверждении")

        // Level 10
        .nextDepthLevel()
        .addVertex(new Vertex<String>("Положения о порядке, сроках и об условиях продажи имущества должника"))
        .addOrientedEdge("утверждении", "Положения о порядке, сроках и об условиях продажи имущества должника")

        // Level 11
        .nextDepthLevel()
        .addVertex(new Vertex<String>("по"))
        .addOrientedEdge(1, "рассмотрев", 11, "по")
        .addOrientedEdge(1, "ознакомившись", 11, "по")
        .addOrientedEdge(6, "дело", 11, "по")
        .addOrientedEdge(7, "дела", 11, "по")
        .addOrientedEdge("Положения о порядке, сроках и об условиях продажи имущества должника", "по")
        
        // Level 12
        .nextDepthLevel()
        .addVertex(new Vertex<String>("исковому"))
        .addOrientedEdge("по", "исковому")


        // Level 13
        .nextDepthLevel()
        .addVertex(new Vertex<String>("заявлению").setAction(Vertex::noAction))
        .addOrientedEdge(11, "по", 13, "заявлению")
        .addOrientedEdge("исковому", "заявлению")

        .addVertex(new Vertex<String>("иску").setAction(Vertex::noAction))
        .addOrientedEdge(11, "по", 13, "иску")

        .addVertex(new Vertex<String>("делу"))
        .addOrientedEdge(11, "по", 13, "делу")

        // Level 14
        .nextDepthLevel()
        .addVertex(new Vertex<String>("о"))
        .addOrientedEdge(7, "дела", 14, "о")
        .addOrientedEdge("делу", "о")

        // Level 15
        .nextDepthLevel()
        .addVertex(new Vertex<String>("несостоятельности (банкротстве)").setAction(Vertex::noAction))
        .addOrientedEdge("о", "несостоятельности (банкротстве)")

        .addVertex(new Vertex<String>("банкротстве").setAction(Vertex::noAction))
        .addOrientedEdge("о", "банкротстве")

        .addVertex(new Vertex<String>("признании").setAction(Vertex::noAction))
        .addOrientedEdge("о", "признании")
        
        .nextDepthLevel();
        //#endregion

        //#region Action part (start from 16 level)

        cdpGraph
        // Level 16
        .addVertex(new Vertex<String>(PARSE_ALL_BEFORE_ACTION+wrapWordAsRegex("о")).setAction(Vertex::noAction))
        .addOrientedEdge(6, "заявление", 16, PARSE_ALL_BEFORE_ACTION+wrapWordAsRegex("о"))
        // Level 17
        .nextDepthLevel()
        .addVertex(new Vertex<String>(PARSE_ALL_BEFORE_ACTION+wrapWordAsRegex("в")).setAction(Vertex::noAction))
        .addOrientedEdge(PARSE_ALL_BEFORE_ACTION+wrapWordAsRegex("о"), PARSE_ALL_BEFORE_ACTION+wrapWordAsRegex("в"))
        // Level 18
        .nextDepthLevel()
        .addVertex(new Vertex<String>("реестре"))
        .addOrientedEdge(PARSE_ALL_BEFORE_ACTION+wrapWordAsRegex("в"), "реестре")
        
        // Level 19
        .nextDepthLevel()
        .addVertex(new Vertex<String>("требований"))
        .addOrientedEdge("реестре", "требований")

        // Level 20
        .nextDepthLevel()
        .addVertex(new Vertex<String>("кредиторов"))
        .addOrientedEdge("требований", "кредиторов")

        // Level 21
        .nextDepthLevel()
        .addVertex(new Vertex<String>("по"))
        .addOrientedEdge("кредиторов", "по")

        // Level 22
        .nextDepthLevel()
        .addVertex(new Vertex<String>("делу"))
        .addOrientedEdge("по", "делу")

        // Level 23
        .nextDepthLevel()
        .addVertex(new Vertex<String>("о"))
        .addOrientedEdge("делу", "о")

        // Level 24
        .nextDepthLevel()
        .addVertex(new Vertex<String>("признании"))
        .addOrientedEdge("о", "признании")

        // Level 25
        .nextDepthLevel()
        .addVertex(new Vertex<String>(PARSE_ALL_BEFORE_ACTION+wrapWordAsRegex("несостоятельным (банкротом)")).setAction(Vertex::noAction))
        .addOrientedEdge("признании", PARSE_ALL_BEFORE_ACTION+wrapWordAsRegex("несостоятельным (банкротом)"))

        // Level 26
        .nextDepthLevel()
        .addVertex(new Vertex<String>("при участии в заседании"))
        .addOrientedEdge(PARSE_ALL_BEFORE_ACTION+wrapWordAsRegex("несостоятельным (банкротом)"), "при участии в заседании")

        // Level 27
        .nextDepthLevel()
        .addVertex(new Vertex<String>("согласно протоколу судебного заседания"))
        .addOrientedEdge("при участии в заседании", "согласно протоколу судебного заседания")

        ;

        //#endregion

        return cdpGraph;
    }

    public static Graph<String> getCdpGraph_v1() {
        Graph<String> cdpGraph = new Graph<>();

        cdpGraph
        .addVertex(new Vertex<String>("Initial"))
        .nextDepthLevel()  // Level 0
            .addVertex(new Vertex<String>("рассмотрев")).addOrientedEdge(cdpGraph.getVertexByDepthValue(0, "Initial"), cdpGraph.getVertexByDepthValue(1, "рассмотрев"))
            .addVertex(new Vertex<String>("ознакомившись")).addOrientedEdge(cdpGraph.getVertexByDepthValue(0, "Initial"), cdpGraph.getVertexByDepthValue(1, "ознакомившись"))
            .nextDepthLevel() // Level 1
                .addVertex(new Vertex<String>("в")).addOrientedEdge(cdpGraph.getVertexByDepthValue(1, "рассмотрев"), cdpGraph.getVertexByDepthValue(2, "в"))
                .addVertex(new Vertex<String>("с")).addOrientedEdge(cdpGraph.getVertexByDepthValue(1, "ознакомившись"), cdpGraph.getVertexByDepthValue(2, "с"))
                .nextDepthLevel() // Level 2
                    .addVertex(new Vertex<String>("открытом")).addOrientedEdge(cdpGraph.getVertexByDepthValue(2, "в"), cdpGraph.getVertexByDepthValue(3, "открытом"))
                    .addVertex(new Vertex<String>("заявлением")).addOrientedEdge(cdpGraph.getVertexByDepthValue(2, "с"), cdpGraph.getVertexByDepthValue(3, "заявлением"))
                    .nextDepthLevel() // Level 3
                        .addVertex(new Vertex<String>("судебном")).addOrientedEdge(cdpGraph.getVertexByDepthValue(3, "открытом"), cdpGraph.getVertexByDepthValue(4, "судебном"))
                            .addOrientedEdge(cdpGraph.getVertexByDepthValue(2, "в"), cdpGraph.getVertexByDepthValue(4, "судебном"))
                        .nextDepthLevel() // Level 4
                            .addVertex(new Vertex<String>("заседании")).addOrientedEdge(cdpGraph.getVertexByDepthValue(4, "судебном"), cdpGraph.getVertexByDepthValue(5, "заседании"))
                            .nextDepthLevel() // Level 5
                                // Action here
                                .addVertex(new Vertex<String>("заявление")).addOrientedEdge(cdpGraph.getVertexByDepthValue(5, "заседании"), cdpGraph.getVertexByDepthValue(6, "заявление"))
                                .nextDepthLevel() // Level 6
                                    .addVertex(new Vertex<String>("финансового управляющего")).addOrientedEdge(cdpGraph.getVertexByDepthValue(6, "заявление"), cdpGraph.getVertexByDepthValue(7, "финансового управляющего"))
                                    .nextDepthLevel()
                                        .addVertex(new Vertex<String>("об")).addOrientedEdge(cdpGraph.getVertexByDepthValue(7, "финансового управляющего"), cdpGraph.getVertexByDepthValue(8, "об"))
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
            .addVertex(B1).addOrientedEdge(A1, B1)
                .addVertex(C1).addOrientedEdge(B1, C1)
                    .addVertex(D1).addOrientedEdge(C1, D1)
                        .addVertex(E1).addOrientedEdge(D1, E1)
            .addVertex(B2).addOrientedEdge(A1, B2)
                .addVertex(C2).addOrientedEdge(B2, C2)
                .addVertex(C3).addOrientedEdge(B2, C3)
                    .addVertex(D2).addOrientedEdge(C3, D2)
                .addVertex(C4).addOrientedEdge(B2, C4)
        ;
        

        return testGraph1;
    } 

    public static Graph<String> getTestGraph2() {
        Graph<String> testGraph2 = new Graph<>();

        testGraph2
        .addVertex(new Vertex<String>("Initial"))
        .nextDepthLevel()
            .addVertex(new Vertex<String>("A1"))
            .addOrientedEdge(
                testGraph2.getVertexByDepthValue(0, "Initial"), 
                testGraph2.getVertexByDepthValue(1, "A1")
            )
            .nextDepthLevel()
                .addVertex(new Vertex<String>("B1"))
                .addOrientedEdge(
                    testGraph2.getVertexByDepthValue(1, "A1"), 
                    testGraph2.getVertexByDepthValue(2, "B1")
                )
                .addVertex(new Vertex<String>("B2"))
                .addOrientedEdge(
                    testGraph2.getVertexByDepthValue(1, "A1"),
                    testGraph2.getVertexByDepthValue(2, "B2")
                )
                .nextDepthLevel()
                    .addVertex(new Vertex<String>("C1"))
                    .addOrientedEdge(
                        testGraph2.getVertexByDepthValue(2, "B1"), 
                        testGraph2.getVertexByDepthValue(3, "C1")    
                    )
                    .addOrientedEdge(
                        testGraph2.getVertexByDepthValue(2, "B2"), 
                        testGraph2.getVertexByDepthValue(3, "C1")    
                    )
                    .addVertex(new Vertex<String>("C2"))
                    .addOrientedEdge(
                        testGraph2.getVertexByDepthValue(2, "B1"), 
                        testGraph2.getVertexByDepthValue(3, "C2")    
                    )
                    .addOrientedEdge(
                        testGraph2.getVertexByDepthValue(2, "B2"), 
                        testGraph2.getVertexByDepthValue(3, "C2")    
                    )
                    .nextDepthLevel()
                        .addVertex(new Vertex<String>("D1"))
                        .addVertex(new Vertex<String>("D2"))
                        .addVertex(new Vertex<String>("D3"))
                        .addOrientedEdge(
                            testGraph2.getVertexByDepthValue(3, "C1"), 
                            testGraph2.getVertexByDepthValue(4, "D1")    
                        )
                        .addOrientedEdge(
                            testGraph2.getVertexByDepthValue(3, "C1"), 
                            testGraph2.getVertexByDepthValue(4, "D2")    
                        )
                        .addOrientedEdge(
                            testGraph2.getVertexByDepthValue(3, "C1"), 
                            testGraph2.getVertexByDepthValue(4, "D3")    
                        )
                        .addOrientedEdge(
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
        return String.format("(^|\\s+)%s(\\s+|$)", word);
    }
    //#endregion
}
