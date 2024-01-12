// Generated from c:/Users/007/Desktop/Workspace/ArbitrageWorkspace/pdfStatisticsSpringApp/arbitrage_statistics/src/main/java/ru/idr/antlr/ArbitrageGrammar.g4 by ANTLR 4.13.1
package ru.idr.antlr.parser;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link ArbitrageGrammarVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
@SuppressWarnings("CheckReturnValue")
public class ArbitrageGrammarBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements ArbitrageGrammarVisitor<T> {
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitR(ArbitrageGrammarParser.RContext ctx) { return visitChildren(ctx); }
}