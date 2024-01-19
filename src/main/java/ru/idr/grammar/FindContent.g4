grammar FindContent;

// @header {
// package ru.idr.grammarfindcontent.parser;
// }

start   : (TEXT? content TEXT?)+ 
        | TEXT;

companyName : COMPANY_PREFIX? LDAQ (interestingPart | TEXT )+ RDAQ?;
interestingPart : '(' (companyName | TEXT )+ ')';

content : ( companyName | interestingPart | TEXT ) ;

// ----------------------------------------- Lexer -----------------------------------------
COMPANY_PREFIX  : 'ООО'
                | 'АО'
                | 'ПАО'
                | 'ИП'
                | '\u0417' '\u0410' '\u041e'
                ;

LDAQ: '«' ;    // Left double angle quote
RDAQ: '»' ;    // Right double angle quote

CYRILIC_LETTER: ('\u0400'..'\u04FF' | '\u0500'..'\u052F') ;
LATIN_LETTER: [A-Za-z] ;

TEXT : (CYRILIC_LETTER | LATIN_LETTER | ~[()«»])+ ;

WS: [ \t\r\n]+ -> skip ;