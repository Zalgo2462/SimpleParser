package parsing.rules;

import parsing.TokenStream;

/**
 * OptionalRule overrides ProductionRule's parse method to return
 * true whether or not the validateTokens method matches the tokenStream.
 * Additionally, it takes a ProductionRule to wrap.
 */
public class OptionalRule extends ProductionRule {
    private final ProductionRule rule;

    public OptionalRule(final ProductionRule rule) {
        this.rule = rule;
    }

    @Override
    public boolean parse(final TokenStream tokenStream) {
        nextChomp = validateTokens(tokenStream);
        return true; //optional rules allow backtracking rules to continue
    }

    @Override
    protected int validateTokens(TokenStream tokenStream) {
        return rule.validateTokens(tokenStream);
    }

}
