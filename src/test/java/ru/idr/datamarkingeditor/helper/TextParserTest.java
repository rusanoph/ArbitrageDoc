package ru.idr.datamarkingeditor.helper;

import java.io.IOException;

import org.junit.Test;

import ru.idr.arbitragestatistics.helper.ServerFile;

public class TextParserTest {

    private String MARKED_DATA_URI = "markedDataJson";
    
    @Test
    public void constructorTest() throws IOException {

        String modelRawJson = ServerFile.fileText("model.json", MARKED_DATA_URI, "JsonTest", "TextParserTest");
        TextParser parser = new TextParser(modelRawJson);

        System.out.println(parser.getEM().toJsonObject());
    }
}
