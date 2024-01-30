package ru.idr.arbitragestatistics.model.datastructure;

import java.util.Map;

import ru.idr.arbitragestatistics.helper.regex.RegExRepository;
import ru.idr.arbitragestatistics.util.datastructure.Graph;
import ru.idr.arbitragestatistics.util.datastructure.ValueVertexMap;
import ru.idr.arbitragestatistics.util.datastructure.Vertex;

public class StaticGraphs {

    private static final String PARSE_ALL_BEFORE_ACTION = "(.*?)";
    
    private static final String PARSE_COMPLAINANT_TYPE = "(?<complainantType>.*?)";
    private static final String PARSE_COMPLAINANT = "(?<complainant>.*?)";
    private static final String PARSE_DEFENDANT = "(?<defendant>.*?)";
    private static final String PARSE_THIRD_PARTY = "(?<thirdParty>.*?)";

    private static final String PARSE_COMPETITION_MANAGER = "(?<competitionManager>.*?)";
    private static final String PARSE_FINANCIAL_MANAGER = "(?<financialManager>.*?)";

    private static final String PARSE_KEYWORD = "(?<keyword>.*?)";
    private static final String PARSE_PERSON_ID = "(?<personId>.*?)";
    private static final String PARSE_PERSON_ADDRESS = "(?<personAddress>.*?)";
    private static final String PARSE_COURT_CASE_SUM = "(?<courtCaseSum>"+RegExRepository.regexCourtCaseSum+")";
 

    private static Map<String, Vertex<String>> getWordMap() {
        var wordMap = new ValueVertexMap<String>();

        wordMap
        // .put("Initial")
        .put("рассмотрев")
        .put("конкурсного")
        .put("конкурсным") 
        .put("управляющего")
        .put("управляющим")
        .put("введении")
        .put("признании")
        .put("встречного")
        .put("суда")
        .put("имущество")
        .put("взыскателя")
        .put("выплату")
        .put("ознакомившись")
        .put("заменив")
        .put("правонарушении")
        .put("от")
        .put("административном")
        .put("судебного")
        .put("заседания")
        .put("которое")
        .put("применении")
        .put("заявленных")
        .put("не")
        .put("заявляющего")
        .put("самостоятельные")       
        .put("требования")
        .put("относительно")
        .put("предмета")
        .put("спора")
        .put("открыто")
        .put("сроком")
        .put("на")
        .put("месяцев")
        .put("наложения")
        .put("открыть")
        .put("реестре")
        .put("мнимой")
        .put("сделкой")
        .put("нему")
        .put("выдаче")
        .put("часов")
        .put("минут")
        .put("утверждении")
        .put("государственной")
        .put("пошлины")
        .put("задолженность")
        .put("индивидуальному")
        .put("предпринимателю")
        .put("также")
        .put("ходатайство")
        .put("в")
        .put("размере")
        .put("судебных")
        .put("недействительности")
        .put("сделки")
        .put("основании")
        .put("протокола")
        .put("об")
        .put("кредиторов")
        .put("требований")
        .put("введена")
        .put("лице")
        .put("лиц")
        .put("наказания")
        .put("подано")
        .put("задолженности")
        .put("дела")
        .put("имущества")
        .put("по")
        .put("адресу")
        .put("зал")
        .put("возмездного")
        .put("оказания")
        .put("услуг")
        .put("вывозу")
        .put("и")
        .put("утилизации")
        .put("твердых")
        .put("коммунальных")
        .put("отходов")
        .put("период")
        .put("гражданина")
        .put("банкротом")
        .put("излишне")
        .put("уплаченную")
        .put("платежному")
        .put("поручению")
        .put("редакции")
        .put("ходе")
        .put("принять")
        .put("оплате")
        .put("предоставлении")
        .put("признан")
        .put("договору")
        .put("бездействия")
        .put("банкротстве")
        .put("третьего")
        .put("лица")
        .put("уступки")
        .put("истца")
        .put("арбитражный")
        .put("суд")
        .put("утвержден")
        .put("утверждена")
        .put("обоснованным")
        .put("конкурсное")
        .put("производство")
        .put("дела")
        .put("исковых")
        .put("при")
        .put("участии")
        .put("заседании")
        .put("кредитора")
        .put("удовлетворить")
        .put("прав")
        .put("систем")
        .put("видеоконференц-связи")
        .put("деле")
        .put("делу")
        .put("дело")
        .put("стоимости")
        .put("являющегося")
        .put("предметом")
        .put("хранения")
        .put("виде")
        .put("предупреждения")
        .put("как")
        .put("признано")
        .put("положения")
        .put("вопроса")
        .put("управляющий")
        .put("заключенного")
        .put("возмещении")
        .put("возмещение")
        .put("приказа")
        .put("установлении")
        .put("реквизитам")
        .put("положение")
        .put("третье")
        .put("лицо")
        .put("взыскать")
        .put("себя")
        .put("решением")
        .put("изменить")
        .put("возложенных")
        .put("назначить")
        .put("отказать")
        .put("поступило")
        .put("административной")
        .put("ответственности")
        .put("исполняющим")
        .put("документы")
        .put("заявителю")
        .put("уставном")
        .put("капитале")
        .put("условиях")
        .put("продажи")
        .put("штраф")
        .put("расходов")
        .put("назначенного")
        .put("встречному")
        .put("обязанности")
        .put("уплату")
        .put("включении")
        .put("должнику")
        .put("иску")
        .put("федерального")
        .put("бюджета")
        .put("реализации")
        .put("госпошлины")
        .put("должника")
        .put("объеме")
        .put("процедура")
        .put("финансовому")
        .put("управляющему")
        .put("убытков")
        .put("отношении")
        .put("права")
        .put("приложенные")
        .put("недействительно")
        .put("завершении")
        .put("него")
        .put("процессуальном")
        .put("оставить")
        .put("без")
        .put("движения")
        .put("правопреемстве")
        .put("заявление")
        .put("иска")
        .put("процедуры")
        .put("конкурсный")
        .put("лист")
        .put("решения")
        .put("исковым")
        .put("использования")
        .put("даты")
        .put("рассмотрению")
        .put("обоснованности")
        .put("заявления")
        .put("несостоятельным")
        .put("обратилась")
        .put("постановление")
        .put("приказа")
        .put("частично")
        .put("размера")
        .put("заявлению")
        .put("следует")
        .put("включены")
        .put("выявлено")
        .put("включено")
        .put("следующее")
        .put("республики")
        .put("республика")
        .put("договора")
        .put("финансовые")
        .put("исковому")
        .put("стоимости")
        .put("санкции")
        .put("требованиями")
        .put("ходатайства")
        .put("согласно")
        .put("протоколу")
        .put("отмене")
        .put("между")
        .put("порядке")
        .put("вознаграждения")
        .put("рассмотрения")
        .put("а")
        .put("к")
        .put("части")
        .put("о")
        .put("с")
        .put("расходы")
        .put("требование")
        .put("ходатайством")
        .put("пользу")
        .put("потребленную")
        .put("активную")
        .put("электроэнергию")
        .put("за")
        .put("года")
        .put("год")
        .put("доход")
        .put("качестве")
        .put("области")
        .put("открытом")
        .put("предоставить")
        .put("стороне")
        .put("ответчика")
        .put("дальнейшем")
        .put("сроках")
        .put("должник")
        .put("судебные")
        .put("оплату")
        .put("представителя")
        .put("руб")
        .put("уплате")
        .put("замену")
        .put("первоначальному")
        .put("обязанностей")
        .put("приложенными")
        .put("документами")
        .put("привлечь")
        .put("ответственного")
        .put("оборудования")
        .put("было")
        .put("произвести")
        .put("исполнения")
        .put("неустойки")
        .put("кодекса")
        .put("российской")
        .put("федерации")
        .put("административных")
        .put("правонарушениях")
        .put("неустойку")
        .put("до")
        .put("исковыми")
        .put("встречными")
        .put("третью")
        .put("очередь")
        .put("реестра")
        .put("ответчику")
        .put("обращению")
        .put("заявляющее")
        .put("самостоятельных")
        .put("материалы")
        .put("заседание")
        .put("взыскании")
        .put("предусмотренной")
        .put("статьи")
        .put("частью")
        .put("уплаченной")
        .put("чеку")
        .put("намерении")
        .put("заключенный")
        .put("из")
        .put("незаконным")
        .put("заявлением")
        .put("производства")
        .put("денежных")
        .put("расторгнуть")
        .put("судебное")
        .put("финансового")
        .put("материалов")
        .put("производству")
        .put("нарушением")
        .put("путем")
        .put("исходная")
        .put("цессии")
        .put("массы")
        .put("поданным")
        .put("постановления")
        .put("судебной")
        .put("наказание")
        .put("судебном")
        .put("определением")
        .put("конфискацией")
        .put("товара")
        .put("рублей")
        .put("р")
        .put("несвоевременное")
        .put("предоставление")
        .put("сведений")
        .put("форме")
        .put("средств")
        .put("общества")
        .put("доли")
        .put("арбитражного")
        .put("обратилось")
        .put("края")
        .put("край")
        .put("вызова")
        .put("предоставленные")
        .put("услуги")
        .put("возвратить")
        .put("отсрочку")
        .put("представителей")
        .put("участвующих")
        .put("отсрочки")
        .put("учетом")
        .put("уточнений")
        .put("расторжении")
        .put("привлечении")
        .put("принятое")
        .put("правопреемника")
        .put("удовлетворенными")
        .put("административное")
        .put("несостоятельности")
        .put("сторон")
        .put("предварительном")
        .put("действительной")
        .put("состоится")
        .put("судебную")
        .put("внесению")
        .put("погасить")
        .put("договор")
        .put("возбудить")
        .put("иском")
        .put("удовлетворении")
        .put("резолютивная")
        .put("суммы")
        .put("полном")
        .put("объеме")
        .put("остальной")
        .put("освобождении")
        .put("недействительным")
        .put("финансовых")
        .put("санкций")
        .put("освободить")
        .put("сроках")
        .put("последствий")
        ;


        return wordMap.getMap();
    }

    public static Graph<String> getCdpGraph() {
        // Replace all Braces with '\\'Brace to correct regex interpretation

        Graph<String> cdpGraph = new Graph<>();

        // #region Part before any action
        cdpGraph
        .addVertex(new Vertex<String>("Initial"))
        // Level 1
        .nextDepthLevel()  
        .addVertex(new Vertex<String>("рассмотрев"))
        .addOrEdge(0, "Initial", 1, "рассмотрев")

        .addVertex(new Vertex<String>("ознакомившись"))
        .addOrEdge(0, "Initial", 1, "ознакомившись")

        // Level 2
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("без"))
        .addOrEdge("рассмотрев", "без")

        .addVertex(new Vertex<String>("в"))
        .addOrEdge(1, "рассмотрев", 2, "в")

        .addVertex(new Vertex<String>("с"))
        .addOrEdge(1, "ознакомившись", 2, "с")

        // Level 3
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("вызова"))
        .addOrEdge("без", "вызова")

        .addVertex(new Vertex<String>("ходатайством"))
        .addOrEdge("с", "ходатайством")

        .addVertex(new Vertex<String>("рамках"))
        .addOrEdge("в", "рамках")

        .addVertex(new Vertex<String>("поданным"))
        .addOrEdge("с", "поданным")
        .addOrEdge(3, "поданным", 2, "в") // Reverse Link (From high-level to low-level)

        .addVertex(new Vertex<String>("открытом"))
        .addOrEdge(2, "в", 3, "открытом")

        .addVertex(new Vertex<String>("предварительном"))
        .addOrEdge("в", "предварительном")

        .addVertex(new Vertex<String>("заявлением"))
        .addOrEdge(2, "с", 3, "заявлением")

        // Level 4
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("сторон"))
        .addOrEdge("вызова", "сторон")

        .addVertex(new Vertex<String>("финансового управляющего"))
        .addOrEdge("ходатайством", "финансового управляющего")

        .addVertex(new Vertex<String>("судебном"))
        .addOrEdge("предварительном", "судебном")
        .addOrEdge(3, "открытом", 4, "судебном")
        .addOrEdge(2, "в", 4, "судебном")

        // Level 5
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("заседании"))
        .addOrEdge(4, "судебном", 5, "заседании")


        // Level 6
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("в"))
        .addOrEdge("заседании", "в")
        .addOrEdge(6, "в", 3,  "рамках") // Reverse Link (From high-level to low-level)

        .addVertex(new Vertex<String>("дело"))
        .addOrEdge("заседании", "дело")

        .addVertex(new Vertex<String>("заявление"))
        .addOrEdge(1, "рассмотрев", 6, "заявление")
        .addOrEdge(5, "заседании", 6, "заявление")

        .addVertex(new Vertex<String>("материалы"))
        .addOrEdge(4, "сторон", 6, "материалы")
        .addOrEdge("заседании", "материалы")

        // Level 7
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("конкурсного управляющего").setAction(Vertex::noAction))
        .addOrEdge("заявление", "конкурсного управляющего")

        .addVertex(new Vertex<String>("финансового управляющего"))
        .addOrEdge(6, "заявление", 7, "финансового управляющего")

        .addVertex(new Vertex<String>("взыскателя").setAction(Vertex::noAction))
        .addOrEdge("заявление", "взыскателя")

        .addVertex(new Vertex<String>("дела"))
        .addOrEdge(3, "рамках", 7, "дела")
        .addOrEdge("материалы", "дела")

        // Level 8
        .nextDepthLevel() 
        .addVertex(new Vertex<String>("об"))
        .addOrEdge(7, "финансового управляющего", 8, "об")

        // Level 9
        .nextDepthLevel()
        .addVertex(new Vertex<String>("утверждении"))
        .addOrEdge("об", "утверждении")

        // Level 10
        .nextDepthLevel()
        .addVertex(new Vertex<String>(RegExRepository.wrapStaticTokenAsRegex("положения о порядке, сроках и об условиях продажи имущества должника")))
        .addOrEdge("утверждении", RegExRepository.wrapStaticTokenAsRegex("положения о порядке, сроках и об условиях продажи имущества должника"))

        // Level 11
        .nextDepthLevel()
        .addVertex(new Vertex<String>("по"))
        .addOrEdge(1, "рассмотрев", 11, "по")
        .addOrEdge(1, "ознакомившись", 11, "по")
        .addOrEdge(6, "дело", 11, "по")
        .addOrEdge(7, "дела", 11, "по")
        .addOrEdge(RegExRepository.wrapStaticTokenAsRegex("положения о порядке, сроках и об условиях продажи имущества должника"), "по")
        
        // Level 12
        .nextDepthLevel()
        .addVertex(new Vertex<String>("исковому"))
        .addOrEdge("по", "исковому")


        // Level 13
        .nextDepthLevel()
        .addVertex(new Vertex<String>("заявлению"))
        .addOrEdge(11, "по", 13, "заявлению")
        .addOrEdge("исковому", "заявлению")

        .addVertex(new Vertex<String>("иску").setAction(Vertex::noAction))
        .addOrEdge(11, "по", 13, "иску")

        .addVertex(new Vertex<String>("делу"))
        .addOrEdge(11, "по", 13, "делу")

        // Level 14
        .nextDepthLevel()
        .addVertex(new Vertex<String>("(о|к)"))
        .addOrEdge(7, "дела", 14, "(о|к)")
        .addOrEdge("делу", "(о|к)")

        // Level 15
        .nextDepthLevel()
        .addVertex(new Vertex<String>("несостоятельности (банкротстве)").setAction(Vertex::noAction))
        .addOrEdge("(о|к)", "несостоятельности (банкротстве)")

        .addVertex(new Vertex<String>("банкротстве").setAction(Vertex::noAction))
        .addOrEdge("(о|к)", "банкротстве")

        .addVertex(new Vertex<String>("признании"))
        .addOrEdge("(о|к)", "признании")
        ;
        //#endregion
        
        //#region 
        
        cdpGraph
        // Level 16
        .nextDepthLevel()
        .addVertex(new Vertex<String>(PARSE_COMPLAINANT_TYPE+RegExRepository.wrapStaticTokenAsRegex("-")+PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        .setAction(Vertex::noAction)) // NI - Action must extract adress (postcode, street, city, region), ogrn/ogrnip, inn
        .addOrEdge(3, "заявлением", 16, PARSE_COMPLAINANT_TYPE+RegExRepository.wrapStaticTokenAsRegex("-")+PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        .addOrEdge(6, "заявление", 16, PARSE_COMPLAINANT_TYPE+RegExRepository.wrapStaticTokenAsRegex("-")+PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        
        .addVertex(new Vertex<String>(PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        .setAction(Vertex::noAction)) // NI - Action must extract adress (postcode, street, city, region), ogrn/ogrnip, inn
        .addOrEdge(3, "заявлением", 16, PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        .addOrEdge(6, "заявление", 16, PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        .addOrEdge(13, "иску", 16, PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))
        .addOrEdge(13, "заявлению", 16, PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"))

        // Level 17
        .nextDepthLevel()
        .addVertex(new Vertex<String>("ответчику"))
        .addOrEdge(PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"), "ответчику")
        

        // Level 18
        .nextDepthLevel()
        .addVertex(new Vertex<String>(PARSE_DEFENDANT+RegExRepository.wrapStaticTokenAsRegex("(?=третье лицо(,|:)?)"))
        .setAction(Vertex::noAction)) // NI - Action must extract adress (postcode, street, city, region), ogrn/ogrnip, inn
        .addOrEdge("ответчику", PARSE_DEFENDANT+RegExRepository.wrapStaticTokenAsRegex("(?=третье лицо(,|:)?)"))
        .addOrEdge(16, PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"), 18, PARSE_DEFENDANT+RegExRepository.wrapStaticTokenAsRegex("(?=третье лицо(,|:)?)"))

        .addVertex(new Vertex<String>(PARSE_KEYWORD+RegExRepository.wrapWordAsRegex("(в|с)")).setAction(Vertex::noAction))
        .addOrEdge(16, PARSE_COMPLAINANT_TYPE+RegExRepository.wrapStaticTokenAsRegex("-")+PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"), 18, PARSE_KEYWORD+RegExRepository.wrapWordAsRegex("(в|с)"))
        .addOrEdge(16, PARSE_COMPLAINANT+RegExRepository.wrapWordAsRegex("(о|к)"), 18, PARSE_KEYWORD+RegExRepository.wrapWordAsRegex("(в|с)"))    
        
        // Level 19
        .nextDepthLevel()
        .addVertex(new Vertex<String>(RegExRepository.wrapStaticTokenAsRegex("(не)? (заявляющее)? (самостоятельных)? (требований)? (относительно)? (предмета)? (спора)?")))
        .addOrEdge(PARSE_DEFENDANT+RegExRepository.wrapStaticTokenAsRegex("(?=третье лицо(,|:)?)"), RegExRepository.wrapStaticTokenAsRegex("(не)? (заявляющее)? (самостоятельных)? (требований)? (относительно)? (предмета)? (спора)?"))

        .addVertex(new Vertex<String>("реестре?"))
        .addOrEdge(PARSE_KEYWORD+RegExRepository.wrapWordAsRegex("(в|с)"), "реестре?")
        
        .addVertex(new Vertex<String>(PARSE_DEFENDANT+RegExRepository.wrapWordAsRegex("финансовых санкций,?")))
        .addOrEdge(PARSE_KEYWORD+RegExRepository.wrapWordAsRegex("(в|с)"),  PARSE_DEFENDANT+RegExRepository.wrapWordAsRegex("финансовых санкций,?"))

        .addVertex(new Vertex<String>("третье лицо(,|:)?"+PARSE_THIRD_PARTY+"(?="+RegExRepository.wrapWordAsRegex("о")+")")
        .setAction(Vertex::noAction)) // NI - Action must extract ogrn/ogrnip, inn, adress
        .addOrEdge(PARSE_DEFENDANT+RegExRepository.wrapStaticTokenAsRegex("(?=третье лицо(,|:)?)"), "третье лицо(,|:)?"+PARSE_THIRD_PARTY+"(?="+RegExRepository.wrapWordAsRegex("о")+")")

        // Level 20
        .nextDepthLevel()
        .addVertex(new Vertex<String>(RegExRepository.wrapStaticTokenAsRegex("(конкурсный)? управляющий")))
        .addOrEdge(RegExRepository.wrapStaticTokenAsRegex("(не)? (заявляющее)? (самостоятельных)? (требований)? (относительно)? (предмета)? (спора)?"), RegExRepository.wrapStaticTokenAsRegex("(конкурсный)? управляющий"))

        .addVertex(new Vertex<String>(RegExRepository.wrapStaticTokenAsRegex("(финансовый)? управляющий")))
        .addOrEdge(RegExRepository.wrapStaticTokenAsRegex("(не)? (заявляющее)? (самостоятельных)? (требований)? (относительно)? (предмета)? (спора)?"), RegExRepository.wrapStaticTokenAsRegex("(финансовый)? управляющий"))

        .addVertex(new Vertex<String>("требований"))
        .addOrEdge("реестре?", "требований")

        // Level 21
        .nextDepthLevel()
        .addVertex(new Vertex<String>(PARSE_COMPETITION_MANAGER+RegExRepository.wrapWordAsRegex("о")))
        .addOrEdge(RegExRepository.wrapStaticTokenAsRegex("(конкурсный)? управляющий"), PARSE_COMPETITION_MANAGER+RegExRepository.wrapWordAsRegex("о"))

        .addVertex(new Vertex<String>(PARSE_FINANCIAL_MANAGER+RegExRepository.wrapWordAsRegex("о")))
        .addOrEdge(RegExRepository.wrapStaticTokenAsRegex("(финансовый)? управляющий"), PARSE_FINANCIAL_MANAGER+RegExRepository.wrapWordAsRegex("о"))

        .addVertex(new Vertex<String>("кредиторов"))
        .addOrEdge("требований", "кредиторов")

        // Level 22
        .nextDepthLevel()
        .addVertex(new Vertex<String>("взыскании"))
        .addOrEdge(PARSE_FINANCIAL_MANAGER+RegExRepository.wrapWordAsRegex("о"), "взыскании")
        .addOrEdge(PARSE_COMPETITION_MANAGER+RegExRepository.wrapWordAsRegex("о"), "взыскании")

        .addVertex(new Vertex<String>("по"))
        .addOrEdge("кредиторов", "по")

        .addVertex(new Vertex<String>("в"))
        .addOrEdge("кредиторов", "в")

        // Level 23
        .nextDepthLevel()
        .addVertex(new Vertex<String>("задолженности"))
        .addOrEdge("взыскании", "задолженности")

        .addVertex(new Vertex<String>("делу"))
        .addOrEdge("по", "делу")

        .addVertex(new Vertex<String>("рамках"))
        .addOrEdge("в", "рамках")

        // Level 24
        .nextDepthLevel()
        .addVertex(new Vertex<String>(RegExRepository.wrapStaticTokenAsRegex("в размере")+PARSE_COURT_CASE_SUM)
        .setAction(Vertex::noAction)) // NI - Split sum by rub and kop
        .addOrEdge("задолженности", RegExRepository.wrapStaticTokenAsRegex("в размере")+PARSE_COURT_CASE_SUM)

        .addVertex(new Vertex<String>("дела"))
        .addOrEdge("рамках", "дела")

        // Level 25
        .nextDepthLevel()
        .addVertex(new Vertex<String>("(о|к)"))
        .addOrEdge(23, "делу", 25, "(о|к)")
        .addOrEdge("дела", "(о|к)")

        // Level 26
        .nextDepthLevel()
        .addVertex(new Vertex<String>("признании"))
        .addOrEdge("(о|к)", "признании")

        // Level 27
        .nextDepthLevel()
        .addVertex(new Vertex<String>(PARSE_DEFENDANT+RegExRepository.wrapWordAsRegex("(несостоятельн(ым|ой) \\(?(банкротом)?\\)?,?)")).setAction(Vertex::noAction))
        .addOrEdge(15, "признании", 27, PARSE_DEFENDANT+RegExRepository.wrapWordAsRegex("(несостоятельн(ым|ой) \\(?(банкротом)?\\)?,?)"))
        .addOrEdge("признании", PARSE_DEFENDANT+RegExRepository.wrapWordAsRegex("(несостоятельн(ым|ой) \\(?(банкротом)?\\)?,?)"))

        // Level 28
        .nextDepthLevel()
        .addVertex(new Vertex<String>("при участии в заседании"))
        .addOrEdge(PARSE_DEFENDANT+RegExRepository.wrapWordAsRegex("(несостоятельн(ым|ой) \\(?(банкротом)?\\)?,?)"), "при участии в заседании")

        // Level 29
        .nextDepthLevel()
        .addVertex(new Vertex<String>("согласно протоколу судебного заседания"))
        .addOrEdge("при участии в заседании", "согласно протоколу судебного заседания")
        ;

        //#endregion

        return cdpGraph;
    }    
}
