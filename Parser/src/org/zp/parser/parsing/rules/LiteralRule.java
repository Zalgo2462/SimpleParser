package org.zp.parser.parsing.rules;

import org.zp.parser.lexing.Token;
import org.zp.parser.parsing.TokenStream;

import java.util.List;

/**
 * LiteralRule implements a ProductionRule for checking a token's literal value
 */
public class LiteralRule extends ProductionRule {

    /**
     * literal is the literal value to check against the next token
     */
    private final String literal;

    /**
     * LiteralRule implements a ProductionRule for checking a token's literal value
     *
     * @param literal the literal value to check against the next token
     */
    public LiteralRule(final String literal) {
        this.literal = literal;
    }

    @Override
    protected int validateTokens(final TokenStream tokenStream) {
        final List<Token> nextToken = tokenStream.peek(1);
        if (nextToken != null && nextToken.get(0).getLiteral().equals(literal)) {
            return 1;
        }
        return 0;
    }
}
