package parsing.rules;

import parsing.TokenStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BacktrackingRule implements combining multiple ProductionRules in series.
 * Either all of the ProductionRules succeed in order, or the BacktrackingRule fails.
 */
public class BacktrackingRule extends ProductionRule {
    private final List<ProductionRule> ruleList;

    public BacktrackingRule() {
        this.ruleList = new ArrayList<>();
    }

    public BacktrackingRule(final ProductionRule[] rules) {
        this.ruleList = new ArrayList<>(Arrays.asList(rules));
    }

    @Override
    protected int validateTokens(final TokenStream tokenStream) {
        int totalAdvance = 0;
        boolean completeParse = true;
        for (ProductionRule rule : ruleList) {
            boolean parsed = rule.parse(tokenStream);
            if (!parsed) {
                completeParse = false;
                break;
            }
            if (rule.consume(tokenStream)) {
                totalAdvance += rule.getNextChomp();
            }

        }

        tokenStream.backtrack(totalAdvance);
        return completeParse ? totalAdvance : 0;
    }

    public void appendRule(final ProductionRule rule) {
        ruleList.add(rule);
    }
}
