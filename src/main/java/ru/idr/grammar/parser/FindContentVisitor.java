// Generated from c:/Users/007/Desktop/Workspace/ArbitrageWorkspace/pdfStatisticsSpringApp/arbitrage_statistics/src/main/java/ru/idr/grammar/FindContent.g4 by ANTLR 4.13.1
package ru.idr.grammar.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FindContentParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FindContentVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FindContentParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(FindContentParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link FindContentParser#companyName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompanyName(FindContentParser.CompanyNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link FindContentParser#interestingPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterestingPart(FindContentParser.InterestingPartContext ctx);
	/**
	 * Visit a parse tree produced by {@link FindContentParser#content}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContent(FindContentParser.ContentContext ctx);
}