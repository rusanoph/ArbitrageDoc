package ru.idr.arbitragestatistics.model.arbitrage;

import java.util.regex.Pattern;

import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.Vertex;

public class StaticGraphs {

    public static Graph<Pattern> getCdpGraph() {
        Graph<Pattern> cdpGraph = new Graph<>();




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

    private static Pattern regexFromString(String string) {
        return Pattern.compile(string, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    }
}
