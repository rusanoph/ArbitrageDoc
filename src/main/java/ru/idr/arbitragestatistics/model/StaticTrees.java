package ru.idr.arbitragestatistics.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.util.Tree;

public class StaticTrees {

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
                        (new Tree<Pattern>(regexFromString("несостоятельности \\(?банкротстве\\)?"))).setAction(text -> text)
                    )
                )
            )    
        )
    )
    .appendChild(
        (new Tree<Pattern>(regexFromString("с\\s+")))
        .appendChild(
            // Leaf
            (new Tree<Pattern>(regexFromString("жалобой"))).setAction(text -> text)
        )
        .appendChild(
            // Leaf
            (new Tree<Pattern>(regexFromString("заявлением")))
            .setAction(text -> {
                String ogrn = RegExRepository.regexOgrn + "|" + RegExRepository.regexOgrnip;                
                String inn = RegExRepository.regexInnLegalEntity + "|" + RegExRepository.regexInnPerson;

                Matcher match = Pattern.compile(String.format(".*?\\s*\\((%s),\\s*(%s)\\)", ogrn, inn)).matcher(text);

                String result = text;
                if (match.find()) {
                    result = match.group();
                }
                
                return result;
            })
        )
        .appendChild(
            (new Tree<Pattern>(regexFromString("исковым")))
            .appendChild(
                // Leaf
                (new Tree<Pattern>(regexFromString("заявлением"))).setAction(text -> text)
            )
        )
    )
    .appendChild(
        (new Tree<Pattern>(regexFromString("со")))
        .appendChild(
            (new Tree<Pattern>(regexFromString("встречным")))
            .appendChild(
                (new Tree<Pattern>(regexFromString("исковым")))
                .appendChild(
                    // Leaf
                    (new Tree<Pattern>(regexFromString("заявлением"))).setAction(text -> text)
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
                        (new Tree<Pattern>(regexFromString("иску"))).setAction(text -> text)
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
                    (new Tree<Pattern>(regexFromString("заявления"))).setAction(text -> text)
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
                        (new Tree<Pattern>(regexFromString("заявление"))).setAction(text -> text)
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
                                    (new Tree<Pattern>(regexFromString("несостоятельности \\(?банкротстве\\)?"))).setAction(text -> text)
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
                            (new Tree<Pattern>(regexFromString("управляющего"))).setAction(text -> text)
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
                            (new Tree<Pattern>(regexFromString("иску"))).setAction(text -> text)
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
