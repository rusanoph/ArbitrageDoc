package ru.idr.arbitragestatistics.model.arbitrage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.util.datastructure.Tree;

public class StaticTrees {
    //#region Test Tree

    public static Tree<String> testTree1 = 
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

    //#endregion

    //#region "ознакомившись"
    public static final Tree<Pattern> cdpTree_1 = 
    (new Tree<Pattern>(regexFromString("ознакомившись")))
    .appendChild(
        (new Tree<Pattern>(regexFromString("в\\s+")))
        .appendChild(
            (new Tree<Pattern>(regexFromString("рамках")))
            .appendChild(
                (new Tree<Pattern>(regexFromString("дела " + RegExRepository.regexCourtCase)))
                .appendChild(
                    (new Tree<Pattern>(regexFromString("о")))
                    .appendChild(
                        // Leaf
                        (new Tree<Pattern>(regexFromString("несостоятельности \\(?банкротстве\\)?"))).setAction(Tree::parseComplainant)
                    )
                )
            )
        )
    )
    .appendChild(
        (new Tree<Pattern>(regexFromString("с\\s+|co\\s+")))
        .appendChild(
            // Leaf
            (new Tree<Pattern>(regexFromString("жалобой"))).setAction(Tree::parseComplainant)
        )
        .appendChild(
            // Leaf
            (new Tree<Pattern>(regexFromString("заявлением")))
            .setAction(Tree::parseComplainant)
        )
        .appendChild(
            (new Tree<Pattern>(regexFromString("исковым")))
            .appendChild(
                // Leaf
                (new Tree<Pattern>(regexFromString("заявлением"))).setAction(Tree::parseComplainant)
            )
        )
        .appendChild(
            (new Tree<Pattern>(regexFromString("встречным")))
            .appendChild(
                (new Tree<Pattern>(regexFromString("исковым")))
                .appendChild(
                    // Leaf
                    (new Tree<Pattern>(regexFromString("заявлением"))).setAction(Tree::parseComplainant)
                )
            )
        )
    );
    //#endregion

    //#region "возобновлено"
    public static final Tree<Pattern> cdpTree_2 = 
    (new Tree<Pattern>(regexFromString("возобновлено")))
    .appendChild(
        (new Tree<Pattern>(regexFromString("производство")))
        .appendChild(
            (new Tree<Pattern>(regexFromString("по")))
            .appendChild(
                (new Tree<Pattern>(regexFromString("делу")))
                .appendChild(
                    (new Tree<Pattern>(regexFromString("по")))
                    .appendChild(
                        // Leaf
                        (new Tree<Pattern>(regexFromString("иску"))).setAction(Tree::parseComplainant)
                    )
                )
            )
        )
    );
    //#endregion
    

    //#region "рассмотрев|рассматривает|рассмотрел"
    public static final Tree<Pattern> cdpTree_3 = 
    (new Tree<Pattern>(regexFromString("рассмотрев|рассматривает|рассмотрел")))
    .appendChild(
        (new Tree<Pattern>(regexFromString("вопрос")))
        .appendChild(
            (new Tree<Pattern>(regexFromString("о\\s+")))
            .appendChild(
                (new Tree<Pattern>(regexFromString("принятии")))
                .appendChild(
                    // Leaf
                    (new Tree<Pattern>(regexFromString("заявления"))).setAction(Tree::parseComplainant)
                )
            )
        )
    )
    .appendChild(
        (new Tree<Pattern>(regexFromString("в\\s+")))
        .appendChild(
            (new Tree<Pattern>(regexFromString("открытом")))
            .appendChild(
                (new Tree<Pattern>(regexFromString("судебном")))
                .appendChild(
                    (new Tree<Pattern>(regexFromString("заседании")))
                    .appendChild(
                        // Leaf
                        (new Tree<Pattern>(regexFromString("заявление"))).setAction(Tree::parseComplainant)
                    )
                )
            )
        )
        .appendChild(
            (new Tree<Pattern>(regexFromString("судебном")))
            .appendChild(
                (new Tree<Pattern>(regexFromString("заседании")))
                .appendChild(
                    (new Tree<Pattern>(regexFromString("в\\s+")))
                    .appendChild(
                        (new Tree<Pattern>(regexFromString("рамках")))
                        .appendChild(
                            (new Tree<Pattern>(regexFromString("дела")))
                            .appendChild(
                                (new Tree<Pattern>(regexFromString("о")))
                                .appendChild(
                                    // Leaf
                                    (new Tree<Pattern>(regexFromString("несостоятельности \\(?банкротстве\\)?"))).setAction(Tree::parseComplainant)
                                )
                            )
                        )
                    )
                )
                .appendChild(
                    // Leaf
                    (new Tree<Pattern>(regexFromString("заявление")))
                )
                .appendChild(
                    (new Tree<Pattern>(regexFromString("исковое")))
                    .appendChild(
                        // Leaf
                        (new Tree<Pattern>(regexFromString("заявление")))
                    )
                )
                .appendChild(
                    (new Tree<Pattern>(regexFromString("ходатайство")))
                    .appendChild(
                        (new Tree<Pattern>(regexFromString("временного|конкурсного|финансового")))
                        .appendChild(
                            // Leaf
                            (new Tree<Pattern>(regexFromString("управляющего"))).setAction(Tree::parseComplainant)
                        )
                    )
                )
            )
        )
        .appendChild(
            (new Tree<Pattern>(regexFromString("порядке")))
            .appendChild(
                (new Tree<Pattern>(regexFromString("упрощенного")))
                .appendChild(
                    (new Tree<Pattern>(regexFromString("производства")))
                    .appendChild(
                        (new Tree<Pattern>(regexFromString("по")))
                        .appendChild(
                            // Leaf
                            (new Tree<Pattern>(regexFromString("иску"))).setAction(Tree::parseComplainant)
                        )
                    )
                )
            )
        )
    );
    //#endregion

    private static Pattern regexFromString(String string) {
        return Pattern.compile(string, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    } 

    private static Pattern regexWord(String word) {
        return Pattern.compile("\\s+"+word+"\\s+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    }
}
