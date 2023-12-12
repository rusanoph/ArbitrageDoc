package ru.idr.arbitragestatistics.helper.regex;

public class RegExComplainantAndDefendat {
    
    //#region Has ID (INN LE, INN IE, OGRN, OGRN IE)
    // LE - Legal Entity
    // IE - Individual Entrepreneur

    // 2022010317_txt\Parsed_bece7c75-6dfc-4b68-896a-4351cad3070f.txt
    // === regex js: (исковое\s*заявление\s*(.*?)\s*города\s*(.*?)\s*\(.*?\)\s*к\s*(.*?)\s*города\s*(.*?)\s*\(.*?\)) ===
    public static final String regexComplainantAndDefendat_v1 = "(исковое\\s*заявление\\s*(.*?)\\s*города\\s*(.*?)\\s*\\(.*?\\)\\s*к\\s*(.*?)\\s*города\\s*(.*?)\\s*\\(.*?\\))";
    /*
        исковое заявление
    Муниципального казенного учреждения «Агентство по аренде земельных
    участков, организации торгов и приватизации муниципального жилищного
    фонда» города Ярославля (ИНН 7604093410, ОГРН 1067604080884)
    к Муниципальному унитарному предприятию «Дирекция городских парков
    культуры и отдыха» города Ярославля (ИНН 7604325950, ОГРН
    1177627016951)
     */

    
    // 2022010323_txt\Parsed_f31fdbb1-b9c9-4977-87be-e49710c67f12.txt  
    // === regex js: (дело\s*по\s*заявлению\s*(.*?),\s*(.*?),\s*(.*?),\s*((пос\.|г\.)\s*.*?),\s*(ОГРН\s*\d+),\s*к\s*(.*?),\s*(.*?),\s*((пос\.|г\.)\s*.*?),\s*(ОГРН\s*\d+)) === 
    public static final String regexComplainantAndDefendat_v2 = "(дело\\s*по\\s*заявлению\\s*(.*?),\\s*(.*?),\\s*(.*?),\\s*((пос\\.|г\\.)\\s*.*?),\\s*(ОГРН\\s*\\d+),\\s*к\\s*(.*?),\\s*(.*?),\\s*((пос\\.|г\\.)\\s*.*?),\\s*(ОГРН\\s*\\d+))";
    /*
        дело по заявлению открытого акционерного общества «Урожайное»,
    Ставропольский край, Новоалександровский район, пос. Равнинный, ОГРН 1022602823902,
    к администрации Новоалександровского городского округа Ставропольского края,
    Ставропольский край, г. Новоалександровск, ОГРН 1172651023643
     */

    // 2022010319_txt\Parsed_16498913-aa52-4cea-bde9-8f77aa04623c.txt
    // === regex js: (дело\s*по\s*иску\s*(.*?),\s*(.*?),\s*((пос\.|г\.)\s*.*?),\s*((ОГРН)\s*(\d+)),\s*к\s*(.*?),\s*(.*?),\s*((пос\.|г\.)\s*.*?),\s*((ОГРН|ОГРНИП)\s*(\d+)),) ===
    public static final String regexComplainantAndDefendat_v3 = "";
    /*
        дело по иску администрации Кировского городского округа
Ставропольского края, Ставропольский край, г. Новопавловск, ОГРН 1172651027009,
к индивидуальному предпринимателю Красину Владимиру Александровичу,
Ставропольский край, г. Новопавловск, ОГРНИП 320265100048381,
     */

    // 2022010321_txt\Parsed_f4452990-dba9-4590-8264-ad0d2efb318f.txt  
    // === regex js: (дело\s*по\s*заявлению\s*(.*?)\s*\(.*?\)\s*к\s*(.*?),\s*(.*?)\s*№\s*\d+/\d+/\d+-СД) ===
    public static final String regexComplainantAndDefendat_v4 = "(дело\\s*по\\s*заявлению\\s*(.*?)\\s*\\(.*?\\)\\s*к\\s*(.*?),\\s*(.*?)\\s*№\\s*\\d+/\\d+/\\d+-СД)";
    /*
        дело по заявлению общества с ограниченной
ответственностью «Мособлэксплуатация» (ИНН 7727321539, ОГРН 1177746577095)
к судебному приставу-исполнителю Сергиево-Посадского районного отдела судебных
приставов ГУФССП России по Московской области Козловой Елене Борисовне, Главному
управлению ФССП России по Московской области
о признании недействительным постановления от 06.07.2021 об аресте права требования
должника по договору поставки по сводному исполнительному производству №
30007/21/50037-СД
     */

    // 2022010319_txt\Parsed_8a579af2-ec66-4108-8fac-de0a1fe5a55c.txt  | formated
    // === regex js: (дело\s*по\s*иску\s*(.*?)\s*\(.*?\),\s*((г\.)\s*.*?),\s*к\s+ответчику\s*(.*?)\s*\(.*?\),\s*((г\.)\s*.*?),) ===
    public static final String regexComplainantAndDefendat_v5 = "(дело\\s*по\\s*иску\\s*(.*?)\\s*\\(.*?\\),\\s*((г\\.)\\s*.*?),\\s*к\\s+ответчику\\s*(.*?)\\s*\\(.*?\\),\\s*((г\\.)\\s*.*?),)";
    /*
        Дело По Иску Индивидуального Предпринимателя Харчва Андрея Сергеевича (ОГРНИП 316527500046390, ИНН 525914855614), Г. Нижний Новгород, К Ответчику Государственному Казенному Учреждению Нижегородской Области Главное Управление Автомобильных Дорог (ОГРН 1025202393886, ИНН 5257056163), Г.Нижний Новгород,
     */


    // 2022010320_txt\Parsed_d25e28fd-2a27-4e0c-acbb-1a65a00d011c.txt  
    // === regex js: (дело\s*по\s*заявлению\s*(.*?)\s*\(.*?\)\s*к\s*(.*?)\s*\(.*?\)) ===
    public static final String regexComplainantAndDefendat_v6 = "(дело\\s*по\\s*заявлению\\s*(.*?)\\s*\\(.*?\\)\\s*к\\s*(.*?)\\s*\\(.*?\\))";
    /*
        Дело По Заявлению Муниципального Унитарного Предприятия Городского Округа Воскресенск Московской Области Управление ДомамиВоскресенск (ИНН 5005060605, ОГРН 1155005000370) К Главному Управлению Московской Области Государственная Жилищная Инспекция Московской Области (ИНН 5018092629, ОГРН 1045003352261)
     */

    
    // 2022010314_txt\Parsed_b5e5bf57-5e44-43c6-93e3-a63bcc957eef.txt
    // === regex js: (иск\s*(.*?)\s*\(.*?\)\s*к\s*(.*?)\s*\(.*?\)) ===
    public static final String regexComplainantAndDefendat_v7 = "(иск\\s*(.*?)\\s*\\(.*?\\)\\s*к\\s*(.*?)\\s*\\(.*?\\))";
    /*
        Иск Общества С Ограниченной Ответственностью Альбион2002 (ИНН 5257056036, ОГРН 1025202393677) К Нижегородскому Областному Потребительскому Обществу (ИНН 5260133146, ОГРН 1045207453488)
     */

    // 2022010317_txt\Parsed_79e6f746-5fc9-47f7-967b-988f85858cd5.txt  
    // === regex js: (дело\s*по\s*иску(.*?)\(.*?\)\s*(.*?),\s*к\s+ответчику:?(.*?)\(.*?\)(\s*.*?,){3}) ===
    public static final String regexComplainantAndDefendat_v8 = "(дело\\s*по\\s*иску(.*?)\\(.*?\\)\\s*(.*?),\\s*к\\s+ответчику:?(.*?)\\(.*?\\)(\\s*.*?,){3})";
    /*
        Дело По Иску Общества С Ограниченной Ответственностью Инжиниринговое Объединение Инсайт (ОГРН 1085262012087, ИНН 5262233918) Нижний Новгород, К Ответчику: Обществу С Ограниченной Ответственностью Мир Бетона (ОГРН 1155262001575, ИНН 5262313137) Нижегородская Область, Кстовский Район, Территория Набережная Гребного Канала,
     */

    // 2022010321_txt\Parsed_fc58c510-8330-472a-89ad-3a9706efa8f0.txt
    // === regex js: (дело\s*по\s*заявлению\s*(.*?)\(.*?\)\s*к(\s*(судебному приставу|начальнику отделения|[^,]*ФССП|Г[^,]*У[^,]*Ф[^,]*С[^,]*С[^,]*П).*?,){0,50}) ===
    public static final String regexComplainantAndDefendat_v9 = "(дело\\s*по\\s*заявлению\\s*(.*?)\\(.*?\\)\\s*к(\\s*(судебному приставу|начальнику отделения|[^,]*ФССП|Г[^,]*У[^,]*Ф[^,]*С[^,]*С[^,]*П).*?,){0,50})";
    /*
        дело по заявлению общества с ограниченной
ответственностью «ДС Констракшен» (ИНН 5024200167, ОГРН 1195081077289)
к судебному приставу-исполнителю Красногорского РОСП ГУФССП России по Московской
области Щербачевой О.В., начальнику отделения – старшему судебному приставу
Красногорского РОСП ГУФССП России по Московской области Степаняну Эрику
Игоревичу, Красногорскому РОСП ГУФССП России по Московской области, Главному
управлению Федеральной службы судебных приставов по Московской области
о признании незаконным бездействия,
     */

    // 2022010313_txt\Parsed_2f497b47-2baa-4302-bced-6c154f826737.txt 
    // === regex js: (дело\s*по\s*заявлению\s*(.*?)\(.*?\)\s*к(\s*.*?)\(.*?\)) ===
    public static final String regexComplainantAndDefendat_v10 = "(дело\\s*по\\s*заявлению\\s*(.*?)\\(.*?\\)\\s*к(\\s*.*?)\\(.*?\\))";
    /*
        дело по заявлению
общества с ограниченной ответственностью «ВЕРТЕР ГРУПП»
(ОГРН 1207700310718, ИНН 9728011220)
к обществу с ограниченной ответственностью «ИЛЬИНОГОРСК АГРО КОММЕРЦ»
(ОГРН 1145249003350, ИНН 5214011785)
     */

    // 2022010400_txt\Parsed_ae6bbf60-5fc3-407e-889f-9947252ba9d2.txt
    // === regex js: (дело\s*по\s*заявлению\s*(.*?(МВД).*?),\s*(г\..*?),\s*к\s*(.*?)\(.*?\),\s*(г\..*?),) ===
    public static final String regexComplainantAndDefendat_v11 = "(дело\\s*по\\s*заявлению\\s*(.*?(МВД).*?),\\s*(г\\..*?),\\s*к\\s*(.*?)\\(.*?\\),\\s*(г\\..*?),)";
    /*
        Дело По Заявлению Управления МВД России По Городу Краснодару, Г. Краснодар, К Индивидуальному Предпринимателю Фурман Ирине Александровне (ОГРНИП 314230104200060, ИНН 233612311958), Г. Краснодар,
     */

    // 2022010312_txt\Parsed_5766ea0d-fddb-4e9c-8b83-9b33dc8cf0fd.txt 
    // === regex js: (исковое\s*заявление\s*(.*?)\(.*?\)\s*к\s*(.*?)\(.*?\)) ===
    public static final String regexComplainantAndDefendat_v12 = "(исковое\\s*заявление\\s*(.*?)\\(.*?\\)\\s*к\\s*(.*?)\\(.*?\\))";
    /*
        Исковое Заявление Общества С Ограниченной Ответственностью Прайм (ИНН 7728388529, ОГРН 5177746257156) К Государственному Бюджетному Учреждению Здравоохранения Ярославской Области Ростовская Центральная Районная Больница (ИНН 7609028319, ОГРН 1147609001209)
     */


    // 2022010322_txt\Parsed_55d5bf24-1119-4448-a443-3c129087f66f.txt  
    // 2022010322_txt\Parsed_b0e2593a-6310-4af0-a882-53423be74ff8.txt  
    // === regex js: дело\s*по\s*заявлению\s(.*?)\(.*?\)\s*к\s*(администрации[^,]*?\(.*?\))(\s*\(.*?\))? ===
    public static final String regexComplainantAndDefendat_v13 = "(дело\\s*по\\s*заявлению\\s(.*?)\\(.*?\\)\\s*к\\s*(администрации[^,]*?\\(.*?\\))(\\s*\\(.*?\\))?";
    /*
        дело по заявлению общества с ограниченной
ответственностью «Родник-2» (ИНН 7731609129, ОГРН 5087746546355)
к Администрации городского округа Можайск Московской области (административная
комиссия №7)

        дело по заявлению общества с ограниченной
ответственностью «Лесное-2» (ИНН 7731609312, ОГРН 5087746565066)
к Администрации Можайского г.о. Московской области (Административная комиссия № 7)
(ИНН 5028003963, ОГРН 1025003472097)
     */

    // 2022010319_txt\Parsed_0ad515c9-9233-4f2f-8e0c-d8f769785594.txt  
    // === regex js: (дело\s*по\s*иску(.*?)\(.*?\),\s*((г\.|пос\.).*?),\s*к\s*ответчику:?(.*?)\(.*?\),\s*((г\.|пос\.).*?),\s*(.*?район),\s*(.*?область),) ===
    public static final String regexComplainantAndDefendat_v14 = "(дело\\s*по\\s*иску(.*?)\\(.*?\\),\\s*((г\\.|пос\\.).*?),\\s*к\\s*ответчику:?(.*?)\\(.*?\\),\\s*((г\\.|пос\\.).*?),\\s*(.*?район),\\s*(.*?область),)";
    /*
        Дело По Иску Общества С Ограниченной Ответственностью Нефтересурс (ОГРН 1185275039487, ИНН 5256175009), Г. Нижний Новгород, К Ответчику: Закрытому Акционерному Обществу Калининское (ОГРН 1026900519403, ИНН 6924003149), Пос. Загородный, Калининский Район, Тверская Область,
     */

    // 2022010313_txt\Parsed_e36819d1-5df4-4979-86cf-eff11bac180a.txt  
    // === regex js: (исковое\s*заявление(.*?)\(.*?\)\s*к\s*(.*?)\(.*?\)) ===
    public static final String regexComplainantAndDefendat_v15 = "(исковое\\s*заявление(.*?)\\(.*?\\)\\s*к\\s*(.*?)\\(.*?\\))";
    /*
        Исковое Заявление Общества С Ограниченной Ответственностью Агентство Территориального Развития (ИНН 7733553954, ОГРН 1057748639486) К Обществу С Ограниченной Ответственностью Ростовский Управдом (ИНН 7604286796, ОГРН 1157627023212)
     */

    // 2022010322_txt\Parsed_1bcf4f74-936a-4e45-b9fc-7a6736cb8140.txt  
    // === regex js: (дело\s*по\s*(исковому\s*)?заявлению(.*?)\(.*?\),?\s*к\s*(.*?)\(.*?\)) ===
    public static final String regexComplainantAndDefendat_v16 = "(дело\\s*по\\s*(исковому\\s*)?заявлению(.*?)\\(.*?\\),?\\s*к\\s*(.*?)\\(.*?\\))";
    /*
        Дело По Исковому Заявлению Общества С Ограниченной Ответственностью Офисмаг (142006, Московская Область, Г. Домодедово, Мкр. Востряково, Ул. Заборье, Д. 2-Д, Стр. 10, ИНН 5009062762, ОГРН 1085009001318) К Обществу С Ограниченной Ответственностью Открытие (142116, Московская Обл., Подольский Район, Пос. Сельхозтехника, Ул. Домодедовское Ш, 1 В, ИНН 5074041931, ОГРН 1085074005466)
     */

    // 2022010321_txt\Parsed_f16cdb72-1897-4d1c-b3f6-39ba2d4bbd93.txt
    // === regex js: (дело\s*по\s*заявлению(.*?)\(.*?\),?\s*к\s*(.*?)\(.*?\)) ===
    public static final String regexComplainantAndDefendat_v17 = "(дело\\s*по\\s*заявлению(.*?)\\(.*?\\),?\\s*к\\s*(.*?)\\(.*?\\))";
    /*
        Дело По Заявлению Общества С Ограниченной Ответственностью Ситистрой-Проект (ИНН 7719798582, ОГРН 5117746016328), К Администрации Одинцовского Городского Округа Московской Области (ИНН 5032004222, ОГРН 1025004066966)
     */

    // 2022010323_txt\Parsed_133b2dbd-fa0c-476f-9e13-7a5accdbd87b.txt  
    // === regex js: (дело\s*по\s*заявлению\s*(.*?)\(.*?\)\s*к\s*(.*?)\s*(\(административная\s*комиссия\s*№\s*\d+\))?\s*\(.*?\)) ===
    public static final String regexComplainantAndDefendat_v18 = "(дело\\s*по\\s*заявлению\\s*(.*?)\\(.*?\\)\\s*к\\s*(.*?)\\s*(\\(административная\\s*комиссия\\s*№\\s*\\d+\\))?\\s*\\(.*?\\))";
    /*
        Дело По Заявлению Общества С Ограниченной Ответственностью Возрождение (ИНН 7725304048, ОГРН 1167746089378) К Администрации Можайского Городского Округа Московской Области (административная Комиссия 7)
     */

    // 2022010313_txt\Parsed_79c11573-7952-42a5-b9c0-7dd055373252.txt  
    // === regex js: (дело\s*по\s*иску:?\s*(истец:\s*.*?\(.*?\);?)\s*(ответчик[и]?:?(\s*\d+\.\s*.*?\(.*?\);?)+)) ===
    public static final String regexComplainantAndDefendat_v19 = "(дело\\s*по\\s*иску:?\\s*(истец:\\s*.*?\\(.*?\\);?)\\s*(ответчик[и]?:?(\\s*\\d+\\.\\s*.*?\\(.*?\\);?)+))";
    /*
        Дело По Иску: Истец: Государственное Унитарное Предприятие Водоканал Санкт-Петербурга (адрес: 191015, Санкт-Петербург, Ул. Кавалергардская, Д.42 ОГРН 1027809256254) Ответчики: 1. Федеральное Государственное Казенное Учреждение Северо-Западное Территориальное Управление Имущественных Отношений Министерства Обороны (адрес: 191119, Санкт-Петербург, Ул. Звенигородская, Д. 5 ОГРН: 1027810323342) 2. Российская Федерация В Лице Министерства Обороны Российской Федерации (адрес: 119019, Москва, Ул. Знаменка, Д. 19 ОГРН 1037700255284)
     */

    // 2022010313_txt\Parsed_691071f6-e68c-41bb-847b-6e6933e79f94.txt  
    // === regex js: (дело\s*по\s*иску\s*(.*?)\s*(.*?)\s*\(.*?\)\s*к\s*(.*?)\s*\(.*?\)) ===
    public static final String regexComplainantAndDefendat_v20 = "(дело\\s*по\\s*иску\\s*(.*?)\\s*(.*?)\\s*\\(.*?\\)\\s*к\\s*(.*?)\\s*\\(.*?\\))";
    /*
        Дело По Иску Акционерного Общества Нижегородский Водоканал (ИНН: 5257086827 ОГРН: 1065257065268) К Обществу Ограниченной Ответственностью Коммунальщик-НН (ИНН: 5245027023 ОГРН: 1155252003081)
     */

    //#endregion

    


    //#region Has NO ID (Only company title)

    // 2022010400_txt\Parsed_09c3b769-854d-47f7-8f91-1c89fb0758a5.txt  
    // 2022010322_txt\Parsed_1a7c9284-a7ed-4875-a6a5-e71984c1ecf0.txt 
    // 2022010320_txt\Parsed_f5f0255e-5477-421d-b44a-d313a029b75d.txt  
    // === regex js: (заявление\s*государственного\s*учреждения(\s*-\s*)?(.*?)\s*о\s*взыскании\s*с(.*?),) ===
    public static final String regexComplainantAndDefendatNoId_v1 = "(заявление\\s*государственного\\s*учреждения(\\s*-\\s*)?(.*?)\\s*о\\s*взыскании\\s*с(.*?),)";
    /*
        Заявление Государственного Учреждения - Отделение Пенсионного Фонда Российской Федерации По Карачаево-Черкесской Республике О Взыскании С Управления Федеральной Налоговой Службы По Карачаево-Черкесской Республике Финансовых Санкций,

        Заявление Государственного Учреждения - Отделение Пенсионного Фонда Российской Федерации По Карачаево-Черкесской Республике О Взыскании С Индивидуального Предпринимателя Васильевой Оксаны Викторовны Финансовых Санкций,

        Заявление Государственного Учреждения - Отделение Пенсионного Фонда Российской Федерации По Карачаево-Черкесской Республике О Взыскании С Общества С Ограниченной Ответственностью Межрайонное Управление Эксплуатации Сельских Водопроводов Финансовых Санкций,
     */

    // 2022010323_txt\Parsed_2c889263-8eae-4a80-b373-801eac13f449.txt  
    // === regex js: (дело\s*по\s*заявлению:?\s*(общество.*?)\s*к\s*(государственной.*?)(?=третье лицо:?)) ===
    public static final String regexComplainantAndDefendatNoId_v2 = "(дело\\s*по\\s*заявлению:?\\s*(общество.*?)\\s*к\\s*(государственной.*?)(?=третье лицо:?))";
    /*
        Дело По Заявлению: Общество С Ограниченной Ответственностью Экосити К Государственной Жилищной Инспекции Санкт-Петербурга 
     */

    // 2022010400_txt\Parsed_e004b0fb-bf2b-49d5-b040-32593c904699.txt
    // === regex js: (дело\s*по\s*иску\s*(ООО.*?),\s*(г\..*?),\s*к\s*(АО.*?),\s*(г\..*?),) ===
    public static final String regexComplainantAndDefendatNoId_v3 = "(дело\\s*по\\s*иску\\s*(ООО.*?),\\s*(г\\..*?),\\s*к\\s*(АО.*?),\\s*(г\\..*?),)";
    /*  
        Дело По Иску ООО Кубаньэкоплюс, Г. Туапсе, К АО Крайжилкомресурс, Г. Краснодар,
     */

    // 2022010321_txt\Parsed_f66f7e40-2807-4f3a-bed1-b6a109af90d6.txt  
    // === regex js: (заявление\s*государственного\s*учреждения\s*(.*?)\s*о\s*взыскании\s*с\s*(.*?),) ===
    public static final String regexComplainantAndDefendatNoId_v4 = "(заявление\\s*государственного\\s*учреждения\\s*(.*?)\\s*о\\s*взыскании\\s*с\\s*(.*?),)";
    /*
        Заявление Государственного Учреждения - Отделение Пенсионного Фонда Российской Федерации По Карачаево-Черкесской Республике О Взыскании С Общества С Ограниченной Ответственностью МегаСтрой Финансовых Санкций,
     */

    //#endregion


    // Template
    // === regex js: () ===
    public static final String regexComplainantAndDefendat_v100 = "()";
    /*
    
     */
}
