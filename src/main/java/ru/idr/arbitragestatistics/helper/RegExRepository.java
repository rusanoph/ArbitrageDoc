package ru.idr.arbitragestatistics.helper;

public class RegExRepository {

    //#region Identifiers
    public static final String regexOgrn = "ОГРН:?\\s*\\d{13}";
    public static final String regexOgrnip = "ОГРНИП:?\\s*\\d{15}";
    public static final String regexInnPerson = "ИНН:?\\s*\\d{12}";
    public static final String regexInnLegalEntity = "ИНН:?\\s*\\d{10}";
    //#endregion

    //#region Document Regions

    public static String regexComplainantAndDefendat = "по\\s*(иску|заявлению)(.*?)(о|об)\\s*(взыскании|признании|обязании)";

    //#endregion

    //#region MoneySum
    public static String regexMoneySum = "([\\s*.]\\d+)+\\s*(р\\.|руб\\.|рублей)";
    public static String regexMoneySumFull = "([\\s*.]\\d+)+\\s*(р\\.|руб\\.|рублей)\\s*(([\\s*.]\\d+)+\\s*(к\\.|коп\\.|копеек))";
    public static String regexMoneySumComma = "([\\s*.]\\d+)+,\\s*([\\s*.]\\d+)+\\s*(р\\.|руб\\.|рублей)";
    public static String regexMoneySumComment = "([\\s*.]\\d+)+\\s*(\\([А-Яа-я ]*\\))?\\s*(р\\.|руб\\.|рублей)\\s*(([\\s*.]\\d+)+\\s*(к\\.|коп\\.|копеек))";    

    public static String regexSpecial = String.join("|", regexMoneySum, regexMoneySumFull, regexMoneySumComma, regexMoneySumComment);
    //#endregion

    //#region Other
    public static String regexCapital = "[А-Я]";
    public static String regexSpecialCharacters = "[^а-яА-Я0-9. ]";
    public static String regexAfterHyphenText = "((\\s*[–-]\\s*[А-Яа-я ]+[\\.,;])?)";
    //#endregion
}
