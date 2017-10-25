package parsing.rules;

import parsing.TokenStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * OrRule implements combining multiple ProductionRules.
 * OrRule checks the ProductionRules given to it one by one, in order,
 * against a TokenStream and uses the first valid rule.
 */
public class OrRule extends ProductionRule {
    private final List<ProductionRule> ruleList;

    public OrRule() {
        this.ruleList = new ArrayList<>();
    }

    public OrRule(final ProductionRule[] rules) {
        this.ruleList = new ArrayList<>(Arrays.asList(rules));
    }

    @Override
    protected int validateTokens(final TokenStream tokenStream) {
        for (ProductionRule rule : ruleList) {
            boolean parsed = rule.parse(tokenStream);
            if (parsed) {
                return rule.getNextChomp();
            }
        }
        return 0;
    }

    public void appendRule(final ProductionRule rule) {
        ruleList.add(rule);
    }
}
