package parsing.rules;

import parsing.TokenStream;

/**
 * RepetitionRule is an optional rule for iteration in EBNF.
 * RepetitionRule tries to match as many copies of the
 * ProductionRule fed to it.
 */
public class RepitionRule extends OptionalRule {
    private final ProductionRule productionRule;

    public RepitionRule(final ProductionRule productionRule) {
        this.productionRule = productionRule;
    }

    @Override
    protected int validateTokens(TokenStream tokenStream) {
        int totalAdvance = 0;
        while (true) {
            if (!productionRule.parse(tokenStream)) {
                break;
            }
            totalAdvance += productionRule.getNextChomp();
            productionRule.consume(tokenStream);
        }
        tokenStream.backtrack(totalAdvance);
        return totalAdvance;
    }
}
