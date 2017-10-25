package parsing.rules;

import parsing.TokenStream;

/**
 * RepetitionRule tries to match as many copies of the
 * ProductionRule fed to it.
 */
public class RepitionRule extends ProductionRule {
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
