package ru.idr.grammar.tool;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import ru.idr.grammar.listener.FindContentListener;
import ru.idr.grammar.parser.FindContentLexer;
import ru.idr.grammar.parser.FindContentParser;

public class FindContent {

    public String find(String text) {
        CharStream input = CharStreams.fromString(text);
        return this.find(input);
    }

    public String find(CharStream input) {
        FindContentLexer lexer = new FindContentLexer(input);
        CommonTokenStream token = new CommonTokenStream(lexer);

        FindContentParser parser = new FindContentParser(token);
        ParseTree tree = parser.start();

        FindContentListener fc = new FindContentListener(parser, input.toString());
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(fc, tree);

        return fc.getText();
        // return " ";
    }
}