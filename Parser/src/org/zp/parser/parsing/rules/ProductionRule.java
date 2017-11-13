package org.zp.parser.parsing.rules;

import org.zp.parser.parsing.TokenStream;

/**
 * ProductionRule encapsulates the logic of an (E)BNF production rule.
 */
public abstract class ProductionRule {
    /**
     * nextChomp holds the amount of tokens to remove from the stream. Set by parse()
     */
    protected int nextChomp = 0;


    /**
     * parse calls validateTokens in order to update nextChomp with how many
     * tokens match this rule.
     *
     * @param tokenStream the TokenStream to run the rule against
     * @return true if validateTokens returns > 0
     */
    public boolean parse(final TokenStream tokenStream) {
        nextChomp = validateTokens(tokenStream);
        return nextChomp > 0;
    }

    /**
     * consume advances the tokenStream by nextChomp. Call after calling parse.
     *
     * @param tokenStream the TokenStream to advance
     * @return true if the TokenStream could be advanced by nextChomp tokens
     */
    public boolean consume(final TokenStream tokenStream) {
        return nextChomp > 0 && tokenStream.advance(nextChomp);
    }

    /**
     * @return nextChomp, the number of tokens validateTokens reported this rule matched
     */
    public int getNextChomp() {
        return nextChomp;
    }

    /**
     * validateTokens is overridden in order to implement the logic of the (E)BNF rule
     *
     * @param tokenStream the TokenStream to run the rule against
     * @return the number of tokens this rule can consume
     */
    protected abstract int validateTokens(final TokenStream tokenStream);
}
