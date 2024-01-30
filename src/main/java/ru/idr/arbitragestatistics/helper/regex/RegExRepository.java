package ru.idr.arbitragestatistics.helper.regex;

import java.util.regex.Pattern;

public class RegExRepository {

    //#region Identifiers
    public static final String regexCourtCase = "№\\s*([Aa]|[Аа])\\s*(\\d+\\s*(–|-)\\s*)+\\d+\\s*(\\/|\\\\)\\s*(\\d+\\s*-\\s*)*\\d+";

    public static final String regexOgrn = "огрн:?\\s*\\d{13}";
    public static final String regexOgrnip = "огрнип:?\\s*\\d{15}";
    public static final String regexInnPerson = "инн:?\\s*\\d{12}";
    public static final String regexInnLegalEntity = "инн:?\\s*\\d{10}";
    //#endregion

    //#region Document Regions

    // public static String regexComplainantAndDefendat = "по\\s*(иску|заявлению)(.*?)(о|об)\\s*(взыскании|признании|обязании)";

    public static String regexComplainanDefendatStart = "(?<=дело\\s*по\\s*исковому\\s*заявлению\\s*|дело\\s*по\\s*заявлению\\s*|дело\\s*по\\s*иску\\s*|исковое\\s*заявление\\s*|заявление\\s*|иск\\s*)";
    public static String regexComplainanDefendatMain = "[^\\s](.*?)\\s+к\\s+(.*?)\\s*";
    public static String regexComplainanDefendatEnd = "(?=(о\\s*признании|о\\s*взыскании|об\\s*обязании|об\\s*изменении|о\\s*привлечении|о\\s*расторжении|о\\s*солидарном))";

    public static String regexComplainantAndDefendat_v2 = regexComplainanDefendatStart + regexComplainanDefendatMain + regexComplainanDefendatEnd;

    // public static String regexComplainantAndDefendat_v2 = "";
    //#endregion

    //#region MoneySum
    public static  String regexMoneySum = "([\\s*.]?\\d+)+\\s*(р\\.|руб\\.|рублей)";
    public static  String regexMoneySumFull = "([\\s*.]?\\d+)+\\s*(р\\.|руб\\.|рублей)\\s*(([\\s*.]?\\d+)+\\s*(к\\.|коп\\.|копеек))";
    public static  String regexMoneySumComma = "([\\s*.]?\\d+)+,\\s*([\\s*.]?\\d+)+\\s*(р\\.|руб\\.|рублей)";
    public static  String regexMoneySumComment = "([\\s*.]?\\d+)+\\s*(\\([А-Яа-я ]*\\))?\\s*(р\\.|руб\\.|рублей)\\s*(([\\s*.]?\\d+)+\\s*(к\\.|коп\\.|копеек))";    

    public static String regexCourtCaseSum = String.join("|", regexMoneySum, regexMoneySumFull, regexMoneySumComma, regexMoneySumComment);
    //#endregion

    //#region Other
    public static String regexCapital = "[А-Я]";
    public static String regexSpecialCharacters = "[^а-яА-Я0-9.,/\\-(): ]";
    public static String regexAfterHyphenText = "((\\s*[–-]\\s*[А-Яа-я ]+[\\.,;])?)";

    public static String streetAddress = "";
    public static String urlLink = "https?:\\/\\/(?:www\\.|(?!:www))[a-zA-Z0-9]";
    public static String date = "";
    public static String personFIO = "";
    public static String abbreviation = "";
    public static String numberDotDelimiter = "";

    //#endregion

    //#region === Regex Helpers ===
    public static Pattern regexFromString(String string) {
        return Pattern.compile(string, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    }

    public static String wrapWordAsRegex(String word) {
        word = word.replaceAll(" ", "\\\\s+");
        return String.format("(^|\\s+)%s(\\s+|$)", word, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    }

    public static String wrapStaticTokenAsRegex(String staticToken) {
        staticToken = staticToken.replaceAll(" ", "\\\\s+");
        return String.format("(^|\\s*)%s(\\s*|$)", staticToken, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    }
    //#endregion
}
