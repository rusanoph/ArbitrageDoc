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
	 * Enter a parse tree produced by {@link FindContentParser#interestingPart}.
	 * @param ctx the parse tree
	 */
	void enterInterestingPart(FindContentParser.InterestingPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link FindContentParser#interestingPart}.
	 * @param ctx the parse tree
	 */
	void exitInterestingPart(FindContentParser.InterestingPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link FindContentParser#content}.
	 * @param ctx the parse tree
	 */
	void enterContent(FindContentParser.ContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link FindContentParser#content}.
	 * @param ctx the parse tree
	 */
	void exitContent(FindContentParser.ContentContext ctx);
}