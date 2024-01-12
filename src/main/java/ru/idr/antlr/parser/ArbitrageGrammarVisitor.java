// Generated from c:/Users/007/Desktop/Workspace/ArbitrageWorkspace/pdfStatisticsSpringApp/arbitrage_statistics/src/main/java/ru/idr/antlr/ArbitrageGrammar.g4 by ANTLR 4.13.1
package ru.idr.antlr.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ArbitrageGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ArbitrageGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ArbitrageGrammarParser#r}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitR(ArbitrageGrammarParser.RContext ctx);
}