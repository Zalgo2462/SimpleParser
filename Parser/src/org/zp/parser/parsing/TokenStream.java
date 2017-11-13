package org.zp.parser.parsing;

import org.zp.parser.lexing.Token;

import java.util.List;

/**
 * TokenStream encapsulates a list of tokens and an index for use
 * with a back tracking recursive descent parser
 */
public class TokenStream {
    /**
     * tokens holds the tokens to parse
     */
    private final List<Token> tokens;

    /**
     * index points into tokens, representing the current index
     */
    private int index = 0;

    /**
     * TokenStream encapsulates a list of tokens and an index for use
     * with a back tracking recursive descent parser
     *
     * @param tokens the tokens to parse
     */
    public TokenStream(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * advance moves the index along the token stream
     *
     * @param numTokens the number of tokens to advance
     * @return false if the caller advances the index out of bounds
     */
    public boolean advance(final int numTokens) {
        if (index + numTokens > tokens.size()) {
            return false;
        }
        index += numTokens;
        return true;
    }

    /**
     * backtrack moves the index backwards through the token stream
     *
     * @param numTokens the number of tokens to back up
     * @return false if the caller backtracks the index out of bounds
     */
    public boolean backtrack(final int numTokens) {
        if (index - numTokens < 0) {
            return false;
        }
        index -= numTokens;
        return true;
    }

    /**
     * peek retrieves the next "numTokens" tokens from the stream
     * offset by the "index"
     *
     * @param numTokens the next N tokens to retrieve
     * @return a list of the next N tokens
     */
    public List<Token> peek(final int numTokens) {
        if (index + numTokens > tokens.size()) {
            return null;
        }
        return tokens.subList(index, index + numTokens);
    }

    /**
     * @return whether or not the index has moved to the end of
     * the stream (i.e. consumed).
     */
    public boolean consumed() {
        return index == tokens.size();
    }
}
