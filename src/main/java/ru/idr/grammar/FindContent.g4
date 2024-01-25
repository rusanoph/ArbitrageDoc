grammar FindContent;
import CyrilicLetterFragments;

// @header {
// package ru.idr.grammarfindcontent.parser;
// }

@lexer::members {
private void printInfoLA(Integer i) {
    System.out.print(String.format("(i: %d, curr: '%c'); ", i, (char)_input.LA(i)));
}

private void printSeqLA(Integer n) {
    for (int i = 1; i <= n; i++) printInfoLA(i);
    System.out.println();
}

private boolean isNextCharSeq(String reference) {
    for (int i = 1; i <= reference.length(); i++) {

        // System.out.print(String.format("(i: %d, curr: '%c', ref: '%c'); ", i, (char)_input.LA(i), reference.charAt(i - 1)));
        
        if (_input.LA(i) != reference.charAt(i - 1)) {
            return false;
        }
    }

    return true;
}

private boolean isNextCharSeqAfterRegex(String regex, String reference) {
    String charsLA = "";

    int i = 1;
    char currentLA = (char) _input.LA(i);
    while (currentLA != IntStream.EOF && !charsLA.endsWith(reference)) {
        charsLA += currentLA;

        if (!("" + currentLA).matches(regex)) return false;

        currentLA = (char) _input.LA(i);
        i++;
    }

    return currentLA == IntStream.EOF;
}
}

start: lexem+ EOF;

lexem: (token | WORD) ;

token:  companyName
        | law
        | moneysum
        | date
        | bracketedText
        // | inn
        // | ogrn
        ;

bracketedText: '(' lexem+ ')';

law : (LDAQ LAW_PREFIX lexem+)+ RDAQ ;
companyName: COMPANY_PREFIX? ((LDAQ | '"') lexem+)+ (RDAQ | '"') ;

moneysum: MONEYSUM ;
date: DATE ;
// inn: INN ;
// ogrn: OGRN ;

// ----------------------------------------- Lexer -----------------------------------------

// INN: (И Н Н)? DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT END_SYMBOL? ;

// OGRN: (О Г Р Н)? DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT END_SYMBOL?
    // | (О Г Р Н И П)? DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT DIGIT END_SYMBOL?
    // ;

// LAW: (LDAQ LAW_PREFIX WORD+)+ RDAQ ;

LAW_PREFIX  : О WS
            | О Б WS
            ;                


// COMPANY_NAME: COMPANY_PREFIX? (LDAQ WORD+)+ RDAQ ;

COMPANY_PREFIX  : О О О
                | А О
                | П А О
                | И П
                | З А О
                | Ф Г А У
                | А А У
                ;

MONEYSUM: RUBSUM WS? KOPSUM?;

RUBSUM: (INT WS?)+ RUB
    | (INT WS?)+ ',' INT WS? RUB
    | (INT WS?)+ '(' WORD ')' RUB
    ;

fragment RUB: 'р.'
    | 'руб.'
    | 'рублей'
    | 'рубль'
    ;

KOPSUM: INT WS? KOP;

fragment KOP: 'к.'
    | 'коп.'
    | 'копеек'
    | 'копейку'
    | 'копейка'
    ;

DATE: LDAQ? DAY RDAQ? DATE_DELIMITER MONTH DATE_DELIMITER YEAR;

fragment DATE_DELIMITER: '.'
        | '/'
        | '-'
        | ' '
        ;

fragment DAY: DIGIT
    | [0-2] DIGIT
    | '3' [0-1]
    ;

fragment MONTH: MONTH_DIGIT
    | MONTH_TEXT
    ;

fragment MONTH_TEXT: ('январь'|'января')
    | ('февраль'|'февраля')
    | ('март'|'марта')
    | ('апрель'|'апреля')
    | ('май'|'мая')
    | ('июнь'|'июня')
    | ('июль'|'июля')
    | ('август'|'августа')
    | ('сентябрь'|'сентября')
    | ('октябрь'|'октября')
    | ('ноябрь'|'ноября')
    | ('декабрь'|'декабря')
    ;

fragment MONTH_DIGIT: DIGIT
            | '0' DIGIT
            | '1' [0-2]
            ;

fragment YEAR: DIGIT DIGIT
    | ('19'|'20') DIGIT DIGIT
    ;

LDAQ: '«';    // Left double angle quote
RDAQ: '»';    // Right double angle quote

INT: DIGIT+ ;
DIGIT: [0-9] ;

CYRILIC_LETTER: ('\u0400'..'\u04FF' | '\u0500'..'\u052F') ;
LATIN_LETTER: [A-Za-z] ;

WORD:   CYRILIC_LETTER+
        | LATIN_LETTER+ 
        | ~[«»]
        ;

fragment END_SYMBOL: (WS | '.' | ')');
fragment WS: [ \t\r\n]+ ;
WS_SKIP: [ \t\r]+ -> skip ;