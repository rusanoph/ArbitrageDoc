package ru.idr.datamarkingeditor.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonRegexTest {
    
    String personNameRegex = "(?<PersonName>([А-Яа-я]+\\s+)*(«([А-Яа-я,]+\\s*)+»)?)";
    String cityRegex =  "(?<City>(п\\.|пос\\.|г\\.|гор\\.|город(а)?)\\s+([А-Яа-я]+\\s*)+?)";
    String personIdRegex = "(?<PersonId>[(,]((ИНН\\s*\\d{10,12},\\s*(ОГРН\\s*\\d{13}|ОГРНИП\\s*\\d{15}))|((ОГРН\\s*\\d{13}|ОГРНИП\\s*\\d{15}),?\\s*ИНН\\s*\\d{10,12}))[,);.])";

    String personRegex = personNameRegex+"\\s*"+cityRegex+"\\s*"+personIdRegex;


    @Test
    void personRegex_Case1() {
        String testCase = "Муниципального казенного учреждения «Агентство по аренде земельных участков, организации торгов и приватизации муниципального жилищного фонда» города Ярославля (ИНН 7604093410, ОГРН 1067604080884)";

        Matcher m = Pattern.compile(personRegex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE).matcher(testCase);

        Assertions.assertTrue(m.find());
    }

    @Test
    void personRegex_Case2() {
        String testCase = "Муниципальному унитарному предприятию «Дирекция городских парков культуры и отдыха» города Ярославля (ИНН 7604325950, ОГРН 1177627016951)";

        Matcher m = Pattern.compile(personRegex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE).matcher(testCase);

        Assertions.assertTrue(m.find());
    }

    // @Test
    // void personRegex_Case3() {
    //     String testCase = "открытого акционерного общества «Урожайное», Ставропольский край, Новоалександровский район, пос. Равнинный, ОГРН 1022602823902,";

    //     Matcher m = Pattern.compile(personRegex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE).matcher(testCase);

    //     Assertions.assertTrue(m.find());
    // }

    
} 
