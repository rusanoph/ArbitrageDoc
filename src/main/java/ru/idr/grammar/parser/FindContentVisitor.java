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
	 * Visit a parse tree produced by {@link FindContentParser#lexem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexem(FindContentParser.LexemContext ctx);
	/**
	 * Visit a parse tree produced by {@link FindContentParser#token}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToken(FindContentParser.TokenContext ctx);
	/**
	 * Visit a parse tree produced by {@link FindContentParser#bracketedText}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketedText(FindContentParser.BracketedTextContext ctx);
	/**
	 * Visit a parse tree produced by {@link FindContentParser#law}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLaw(FindContentParser.LawContext ctx);
	/**
	 * Visit a parse tree produced by {@link FindContentParser#companyName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompanyName(FindContentParser.CompanyNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link FindContentParser#moneysum}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMoneysum(FindContentParser.MoneysumContext ctx);
	/**
	 * Visit a parse tree produced by {@link FindContentParser#date}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate(FindContentParser.DateContext ctx);
}