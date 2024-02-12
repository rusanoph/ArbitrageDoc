package ru.idr;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

import ru.idr.datamarkingeditor.model.EntityTest;
import ru.idr.datamarkingeditor.regex.PersonRegexTest;

@SpringBootTest
@RunWith(Suite.class)
@Suite.SuiteClasses({EntityTest.class, PersonRegexTest.class})
public class TestSuite {

}
