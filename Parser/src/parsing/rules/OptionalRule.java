package parsing.rules;

import parsing.TokenStream;

/**
 * OptionalRule overrides ProductionRule's parse method to return
 * true whether or not the validateTokens method matches the tokenStream.
 */
public abstract class OptionalRule extends ProductionRule {

    @Override
    public boolean parse(final TokenStream tokenStream) {
        nextChomp = validateTokens(tokenStream);
        return true; //optional rules allow backtracking rules to continue
    }

}
