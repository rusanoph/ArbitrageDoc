package ru.idr.grammar.listener;

import org.antlr.v4.runtime.ParserRuleContext;

import ru.idr.arbitragestatistics.util.HTMLWrapper;
import ru.idr.grammar.parser.FindContentBaseListener;
import ru.idr.grammar.parser.FindContentParser;
import ru.idr.grammar.parser.FindContentParser.CompanyNameContext;
import ru.idr.grammar.parser.FindContentParser.ContentContext;
import ru.idr.grammar.parser.FindContentParser.InterestingPartContext;
import ru.idr.grammar.parser.FindContentParser.StartContext;

public class FindContentListener extends FindContentBaseListener {

    private FindContentParser parser;
    private String text;
    private String wrappedText = "";
    private int currentPosition = 0;

    public FindContentListener(FindContentParser parser, String text) {
        this.parser = parser;
        this.text = text;
    }

    public String getText() {
        return this.wrappedText;
    }

    private void enterWrap(ParserRuleContext ctx, String cssClass) {
        String matched = parser.getTokenStream().getText(ctx.getRuleContext());
        int startInd = ctx.start.getStartIndex();

        this.wrappedText += String.format(
            "%s<span class=%s>%s", 
            this.text.substring(this.currentPosition, startInd),
            cssClass,
            matched
            );

        this.currentPosition = startInd;
    }

    private void exitWrap(ParserRuleContext ctx) {
        int stopInd = ctx.stop.getStopIndex();

        this.wrappedText += "</span>";

        this.currentPosition = stopInd + 1;
    }


    //#region Rule Listener Handlers

    @Override
    public void enterInterestingPart(InterestingPartContext ctx) {
        enterWrap(ctx, "sub-accent");
    }

    @Override
    public void exitInterestingPart(InterestingPartContext ctx) {
        exitWrap(ctx);
    }

    @Override
    public void enterCompanyName(CompanyNameContext ctx) {
        enterWrap(ctx, "accent");
    }

    @Override
    public void exitCompanyName(CompanyNameContext ctx) {
        exitWrap(ctx);
    }
    //#endregion
}
