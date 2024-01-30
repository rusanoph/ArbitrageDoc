package ru.idr.arbitragestatistics.model.datastructure;

import java.util.regex.Pattern;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.util.datastructure.Tree;

public class StaticTrees {
    
    public static Tree<String> getHeaderParseTree() {
        Tree<String> cdpTree = new Tree<String>(null);

        cdpTree
        .appendChild("рассмотрев")
            .appendChild("в")
                .appendChild("открытом")
                    .appendChild("судебном")
                        .appendChild("заседании")
                            .appendChild("заявление")
                                .appendChild(".*?" + RegExRepository.wrapWordAsRegex("о")).setAction(null)
                                    .appendChild("замене кредитора").setAction(null)
                                        .appendChild("в")  
                                            .appendChild("реестре")
                                                .appendChild("требований")
                                                    .appendChild("кредиторов")
                                                        .appendChild("по")
                                                            .appendChild("делу")
                                                                .appendChild("о")
                                                                    .appendChild("признании")
                                                                        .appendChild(".*?" + RegExRepository.wrapWordAsRegex("несостоятельным (банкротом),?")).setAction(null)
                                                                            .appendChild("при участии в заседании:?")
                                                                                .appendChild("согласно протоколу судебного заседания,?")
        .appendChild("ознакомившись")
            .appendChild("с")
                .appendChild("заявлением")
                    .appendChild(".*?" + RegExRepository.wrapWordAsRegex("о")).setAction(null)
                        .appendChild("включении")
                            .appendChild("в")
                                .appendChild("реестр")
                                    .appendChild("требований")
                                        .appendChild("кредиторов")
                                            .appendChild("в")
                                                .appendChild("рамках")
                                                    .appendChild("дела")
                                                        .appendChild("о")
                                                            .appendChild("признании")
                                                                .appendChild(".*?" + RegExRepository.wrapWordAsRegex("несостоятельным (банкротом),?")).setAction(null)
        ;
        

        cdpTree.recomputeDepthDFS();
        return cdpTree;
    }

    //#region Test Tree

    public static Tree<String> getTestTree1() {
        Tree<String> testTree1 = 
        (new Tree<String>("A1"))
        .appendChild(
            (new Tree<String>("B1"))
            .appendChild(
                (new Tree<String>("C1"))
                
            )
            .appendChild(
                (new Tree<String>("C2"))
                .appendChild(
                    (new Tree<String>("D1"))
                    
                )
                
            )
        );

        testTree1.recomputeDepthDFS();

        return testTree1;
    }

    //#endregion

    //#region "ознакомившись"
    public static Tree<Pattern> getCdpTree1() {
        Tree<Pattern> cdpTree_1 = 
        (new Tree<Pattern>(RegExRepository.regexFromString("ознакомившись")))
        .appendChild(
            (new Tree<Pattern>(RegExRepository.regexFromString("в\\s+")))
            .appendChild(
                (new Tree<Pattern>(RegExRepository.regexFromString("рамках")))
                .appendChild(
                    (new Tree<Pattern>(RegExRepository.regexFromString("дела " + RegExRepository.regexCourtCase)))
                    .appendChild(
                        (new Tree<Pattern>(RegExRepository.regexFromString("о")))
                        .appendChild(
                            // Leaf
                            (new Tree<Pattern>(RegExRepository.regexFromString("несостоятельности \\(?банкротстве\\)?"))).setAction(Tree::parseComplainant)
                        )
                    )
                )
            )
        )
        .appendChild(
            (new Tree<Pattern>(RegExRepository.regexFromString("с\\s+|co\\s+")))
            .appendChild(
                // Leaf
                (new Tree<Pattern>(RegExRepository.regexFromString("жалобой"))).setAction(Tree::parseComplainant)
            )
            .appendChild(
                // Leaf
                (new Tree<Pattern>(RegExRepository.regexFromString("заявлением")))
                .setAction(Tree::parseComplainant)
            )
            .appendChild(
                (new Tree<Pattern>(RegExRepository.regexFromString("исковым")))
                .appendChild(
                    // Leaf
                    (new Tree<Pattern>(RegExRepository.regexFromString("заявлением"))).setAction(Tree::parseComplainant)
                )
            )
            .appendChild(
                (new Tree<Pattern>(RegExRepository.regexFromString("встречным")))
                .appendChild(
                    (new Tree<Pattern>(RegExRepository.regexFromString("исковым")))
                    .appendChild(
                        // Leaf
                        (new Tree<Pattern>(RegExRepository.regexFromString("заявлением"))).setAction(Tree::parseComplainant)
                    )
                )
            )
        );

        cdpTree_1.recomputeDepthDFS();

        return cdpTree_1;
    }
    //#endregion

    //#region "возобновлено"
    public static Tree<Pattern> getCdpTree2() {
        Tree<Pattern> cdpTree_2 = 
        (new Tree<Pattern>(RegExRepository.regexFromString("возобновлено")))
        .appendChild(
            (new Tree<Pattern>(RegExRepository.regexFromString("производство")))
            .appendChild(
                (new Tree<Pattern>(RegExRepository.regexFromString("по")))
                .appendChild(
                    (new Tree<Pattern>(RegExRepository.regexFromString("делу")))
                    .appendChild(
                        (new Tree<Pattern>(RegExRepository.regexFromString("по")))
                        .appendChild(
                            // Leaf
                            (new Tree<Pattern>(RegExRepository.regexFromString("иску"))).setAction(Tree::parseComplainant)
                        )
                    )
                )
            )
        );

        cdpTree_2.recomputeDepthDFS();

        return cdpTree_2;
    }
    //#endregion
    

    //#region "рассмотрев|рассматривает|рассмотрел"
    public static Tree<Pattern> getCdpTree3() {
        Tree<Pattern> cdpTree_3 = 
        (new Tree<Pattern>(RegExRepository.regexFromString("рассмотрев|рассматривает|рассмотрел")))
        .appendChild(
            (new Tree<Pattern>(RegExRepository.regexFromString("вопрос")))
            .appendChild(
                (new Tree<Pattern>(RegExRepository.regexFromString("о\\s+")))
                .appendChild(
                    (new Tree<Pattern>(RegExRepository.regexFromString("принятии")))
                    .appendChild(
                        // Leaf
                        (new Tree<Pattern>(RegExRepository.regexFromString("заявления"))).setAction(Tree::parseComplainant)
                    )
                )
            )
        )
        .appendChild(
            (new Tree<Pattern>(RegExRepository.regexFromString("в\\s+")))
            .appendChild(
                (new Tree<Pattern>(RegExRepository.regexFromString("открытом")))
                .appendChild(
                    (new Tree<Pattern>(RegExRepository.regexFromString("судебном")))
                    .appendChild(
                        (new Tree<Pattern>(RegExRepository.regexFromString("заседании")))
                        .appendChild(
                            // Leaf
                            (new Tree<Pattern>(RegExRepository.regexFromString("заявление"))).setAction(Tree::parseComplainant)
                        )
                    )
                )
            )
            .appendChild(
                (new Tree<Pattern>(RegExRepository.regexFromString("судебном")))
                .appendChild(
                    (new Tree<Pattern>(RegExRepository.regexFromString("заседании")))
                    .appendChild(
                        (new Tree<Pattern>(RegExRepository.regexFromString("в\\s+")))
                        .appendChild(
                            (new Tree<Pattern>(RegExRepository.regexFromString("рамках")))
                            .appendChild(
                                (new Tree<Pattern>(RegExRepository.regexFromString("дела")))
                                .appendChild(
                                    (new Tree<Pattern>(RegExRepository.regexFromString("о")))
                                    .appendChild(
                                        // Leaf
                                        (new Tree<Pattern>(RegExRepository.regexFromString("несостоятельности \\(?банкротстве\\)?"))).setAction(Tree::parseComplainant)
                                    )
                                )
                            )
                        )
                    )
                    .appendChild(
                        // Leaf
                        (new Tree<Pattern>(RegExRepository.regexFromString("заявление")))
                    )
                    .appendChild(
                        (new Tree<Pattern>(RegExRepository.regexFromString("исковое")))
                        .appendChild(
                            // Leaf
                            (new Tree<Pattern>(RegExRepository.regexFromString("заявление")))
                        )
                    )
                    .appendChild(
                        (new Tree<Pattern>(RegExRepository.regexFromString("ходатайство")))
                        .appendChild(
                            (new Tree<Pattern>(RegExRepository.regexFromString("временного|конкурсного|финансового")))
                            .appendChild(
                                // Leaf
                                (new Tree<Pattern>(RegExRepository.regexFromString("управляющего"))).setAction(Tree::parseComplainant)
                            )
                        )
                    )
                )
            )
            .appendChild(
                (new Tree<Pattern>(RegExRepository.regexFromString("порядке")))
                .appendChild(
                    (new Tree<Pattern>(RegExRepository.regexFromString("упрощенного")))
                    .appendChild(
                        (new Tree<Pattern>(RegExRepository.regexFromString("производства")))
                        .appendChild(
                            (new Tree<Pattern>(RegExRepository.regexFromString("по")))
                            .appendChild(
                                // Leaf
                                (new Tree<Pattern>(RegExRepository.regexFromString("иску"))).setAction(Tree::parseComplainant)
                            )
                        )
                    )
                )
            )
        );

        cdpTree_3.recomputeDepthDFS();

        return cdpTree_3;
    }
    //#endregion

}
