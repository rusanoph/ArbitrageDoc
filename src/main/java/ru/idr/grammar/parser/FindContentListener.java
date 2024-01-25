// Generated from c:/Users/007/Desktop/Workspace/ArbitrageWorkspace/pdfStatisticsSpringApp/arbitrage_statistics/src/main/java/ru/idr/grammar/FindContent.g4 by ANTLR 4.13.1
package ru.idr.grammar.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FindContentParser}.
 */
public interface FindContentListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FindContentParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(FindContentParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link FindContentParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(FindContentParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link FindContentParser#lexem}.
	 * @param ctx the parse tree
	 */
	void enterLexem(FindContentParser.LexemContext ctx);
	/**
	 * Exit a parse tree produced by {@link FindContentParser#lexem}.
	 * @param ctx the parse tree
	 */
	void exitLexem(FindContentParser.LexemContext ctx);
	/**
	 * Enter a parse tree produced by {@link FindContentParser#token}.
	 * @param ctx the parse tree
	 */
	void enterToken(FindContentParser.TokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link FindContentParser#token}.
	 * @param ctx the parse tree
	 */
	void exitToken(FindContentParser.TokenContext ctx);
	/**
	 * Enter a parse tree produced by {@link FindContentParser#bracketedText}.
	 * @param ctx the parse tree
	 */
	void enterBracketedText(FindContentParser.BracketedTextContext ctx);
	/**
	 * Exit a parse tree produced by {@link FindContentParser#bracketedText}.
	 * @param ctx the parse tree
	 */
	void exitBracketedText(FindContentParser.BracketedTextContext ctx);
	/**
	 * Enter a parse tree produced by {@link FindContentParser#law}.
	 * @param ctx the parse tree
	 */
	void enterLaw(FindContentParser.LawContext ctx);
	/**
	 * Exit a parse tree produced by {@link FindContentParser#law}.
	 * @param ctx the parse tree
	 */
	void exitLaw(FindContentParser.LawContext ctx);
	/**
	 * Enter a parse tree produced by {@link FindContentParser#companyName}.
	 * @param ctx the parse tree
	 */
	void enterCompanyName(FindContentParser.CompanyNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link FindContentParser#companyName}.
	 * @param ctx the parse tree
	 */
	void exitCompanyName(FindContentParser.CompanyNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link FindContentParser#moneysum}.
	 * @param ctx the parse tree
	 */
	void enterMoneysum(FindContentParser.MoneysumContext ctx);
	/**
	 * Exit a parse tree produced by {@link FindContentParser#moneysum}.
	 * @param ctx the parse tree
	 */
	void exitMoneysum(FindContentParser.MoneysumContext ctx);
	/**
	 * Enter a parse tree produced by {@link FindContentParser#date}.
	 * @param ctx the parse tree
	 */
	void enterDate(FindContentParser.DateContext ctx);
	/**
	 * Exit a parse tree produced by {@link FindContentParser#date}.
	 * @param ctx the parse tree
	 */
	void exitDate(FindContentParser.DateContext ctx);
}