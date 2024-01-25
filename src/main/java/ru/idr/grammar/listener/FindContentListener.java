package ru.idr.grammar.listener;

import org.antlr.v4.runtime.ParserRuleContext;

import ru.idr.grammar.parser.FindContentBaseListener;
import ru.idr.grammar.parser.FindContentParser;
import ru.idr.grammar.parser.FindContentParser.BracketedTextContext;
import ru.idr.grammar.parser.FindContentParser.CompanyNameContext;
import ru.idr.grammar.parser.FindContentParser.DateContext;
// import ru.idr.grammar.parser.FindContentParser.InnContext;
import ru.idr.grammar.parser.FindContentParser.LawContext;
import ru.idr.grammar.parser.FindContentParser.MoneysumContext;
// import ru.idr.grammar.parser.FindContentParser.OgrnContext;

public class FindContentListener extends FindContentBaseListener {

    private FindContentParser parser;
    private String text;
    private String wrappedText = "";
    private int currentPosition = 0;

    private ParserRuleContext lastContext;

    public FindContentListener(FindContentParser parser, String text) {
        this.parser = parser;
        this.text = text;
    }

    public String getText() {
        if (this.lastContext != null) {
            int parserStopIndex = this.lastContext.stop.getStopIndex();
            if (parserStopIndex != this.text.length()) {
                this.wrappedText += this.text.substring(parserStopIndex + 1);
            }
        } else {
            this.wrappedText = this.text;
        }

        return this.wrappedText;
    }

    private void enterWrap(ParserRuleContext ctx, String hexColor, String toolTipText) {
        String matched = parser.getTokenStream().getText(ctx.getRuleContext());
        int startInd = ctx.start.getStartIndex();

        if (this.lastContext != null && ctx.stop.getStopIndex() < this.lastContext.stop.getStopIndex()) {
            
        }

        this.wrappedText += String.format(
            "%s<span class='token' style='background: #%s;' onmouseover='showTooltip(event, \"%s\")' onmousemove='showTooltip(event, \"%s\")'>%s", 
            this.text.substring(this.currentPosition, startInd),
            hexColor,
            toolTipText,
            toolTipText,
            matched
        );

        this.currentPosition = startInd;
        this.lastContext = ctx;
    }

    private void exitWrap(ParserRuleContext ctx) {
        int stopInd = ctx.stop.getStopIndex();

        this.wrappedText += "</span>";
        this.currentPosition = stopInd + 1;
    }


    //#region Rule Listener Handlers

    @Override
    public void enterCompanyName(CompanyNameContext ctx) {
        enterWrap(ctx, "FFD1DC", "Название организации");
    }

    @Override
    public void exitCompanyName(CompanyNameContext ctx) {
        exitWrap(ctx);
    }

    @Override
    public void enterLaw(LawContext ctx) {
        enterWrap(ctx, "ADD8E6", "Закон");
    }

    @Override
    public void exitLaw(LawContext ctx) {
        exitWrap(ctx);
    }

    @Override
    public void enterMoneysum(MoneysumContext ctx) {
        enterWrap(ctx, "F0E68C", "Денежная сумма");
    }

    @Override
    public void exitMoneysum(MoneysumContext ctx) {
        exitWrap(ctx);
    }

    @Override
    public void enterDate(DateContext ctx) {
        enterWrap(ctx, "FFDAB9", "Дата");
    }

    @Override
    public void exitDate(DateContext ctx) {
        exitWrap(ctx);
    }

    @Override
    public void enterBracketedText(BracketedTextContext ctx) {
        enterWrap(ctx, "b8e7c9", "Группа");
    }

    @Override
    public void exitBracketedText(BracketedTextContext ctx) {
        exitWrap(ctx);
    }

    // @Override
    // public void enterInn(InnContext ctx) {
    //     enterWrap(ctx, "FF7711", "ИНН");
    // }

    // @Override
    // public void enterOgrn(OgrnContext ctx) {
    //     exitWrap(ctx);
    // }

    // @Override
    // public void exitInn(InnContext ctx) {
    //     enterWrap(ctx, "1155FF", "ОГРН");
    // }

    // @Override
    // public void exitOgrn(OgrnContext ctx) {
    //     exitWrap(ctx);
    // }

    
    //#endregion
}
