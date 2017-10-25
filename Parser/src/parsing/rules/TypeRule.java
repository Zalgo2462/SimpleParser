package parsing.rules;

import lexing.Token;
import parsing.TokenStream;

import java.util.List;

/**
 * TypeRule implements a ProductionRule for checking a token's type
 */
public class TypeRule extends ProductionRule {
    /**
     * tokenType is the type to check the next token against
     */
    private final Token.Type tokenType;

    /**
     * TypeRule implements a ProductionRule for checking a token's type
     *
     * @param tokenType the type to check the next token against
     */
    public TypeRule(final Token.Type tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    protected int validateTokens(final TokenStream tokenStream) {
        final List<Token> nextToken = tokenStream.peek(1);
        if (nextToken != null && nextToken.get(0).getType() == tokenType) {
            return 1;
        }
        return 0;
    }
}
