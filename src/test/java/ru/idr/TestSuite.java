package ru.idr;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

import ru.idr.datamarkingeditor.helper.MarkedDataParserTest;
import ru.idr.datamarkingeditor.model.InnerRegexMapTest;
import ru.idr.datamarkingeditor.model.entity.EntityMapTest;
import ru.idr.datamarkingeditor.model.entity.EntityTest;
import ru.idr.datamarkingeditor.model.entity.arbitrage.KeywordEntityTest;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.CompetitionManagerTest;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.ComplainantTest;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.DefendantTest;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.FinancialManagerTest;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.PersonEntityTest;
import ru.idr.datamarkingeditor.model.entity.arbitrage.person.ThirdPartyTest;
import ru.idr.datamarkingeditor.model.entity.common.MoneySumEntityTest;
import ru.idr.datamarkingeditor.model.entity.common.WordEntityTest;
import ru.idr.datamarkingeditor.model.token.ITokenTest;


@SpringBootTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
    //#region Data Marking Editor
    InnerRegexMapTest.class,
    ITokenTest.class,
    
    EntityTest.class,
    EntityMapTest.class,
    
    WordEntityTest.class,
    MoneySumEntityTest.class,

    PersonEntityTest.class,
    CompetitionManagerTest.class,
    ComplainantTest.class,
    DefendantTest.class,
    FinancialManagerTest.class,
    ThirdPartyTest.class,
    KeywordEntityTest.class,

    MarkedDataParserTest.class,
    //#endregion
})
public class TestSuite {

}
