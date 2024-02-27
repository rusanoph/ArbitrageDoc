package ru.idr.datamarkingeditor.helper;

import java.io.IOException;

import org.junit.Test;

import ru.idr.arbitragestatistics.helper.DocumentProcessor;
import ru.idr.datamarkingeditor.model.entity.EntityMap;

public class TextParserTest {

    private String MARKED_DATA_URI = "markedDataJson";
    
    @Test
    public void constructorTest() throws IOException {

        MarkedDataParser markedParser = new MarkedDataParser();
        EntityMap model = markedParser.combineAll(MARKED_DATA_URI, "JsonTest", "ModelOn20Files", "json");
        TextParser parser = new TextParser(model);

        String textToParse = DocumentProcessor.getText("2022010315_txt", "Parsed_0b4a95fc-a199-49f3-afa9-9b15918a51d1.txt");

        // var foundEntities = parser.parseEntities(textToParse);
        // for (Entity e : foundEntities) System.out.println(String.format("%s: %s {%s}", e.getType(), e.getRawValue(), e.getValue()));
        // for (Entity e : foundEntities) System.out.println(String.format("%s: %s", e.getType(), e.getRawValue()));

        // System.out.println(parser.getEM().toJsonObject());

        System.out.println(parser.parseEntitiesAsJson(textToParse).toString(4));
    }
}
