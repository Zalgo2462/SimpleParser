import lexing.LexError;
import lexing.Token;
import lexing.Tokenizer;
import parsing.TokenStream;
import parsing.rules.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {
    public static void main(String[] args) {
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String input;
        Tokenizer t;
        try {
            System.out.print("Enter expression: ");
            input = buff.readLine();

            while (!input.equals("exit")) {
                try {
                    t = new Tokenizer(input);
                    System.out.println(t);
                    System.out.println(parse(t.getTokens()));

                } catch (LexError e) {
                    System.out.println(e.toString());
                }

                System.out.print("\nEnter expression: ");
                input = buff.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private static boolean parse(final TokenStream tokenStream) {
        BacktrackingRule expr = new BacktrackingRule();
        BacktrackingRule term = new BacktrackingRule();
        OrRule factor = new OrRule();


        expr.appendRule(term);
        expr.appendRule(new RepitionRule(new BacktrackingRule(new ProductionRule[]{
                new TypeRule(Token.Type.ADD_OP), term
        })));

        term.appendRule(factor);
        term.appendRule(new RepitionRule(new BacktrackingRule(new ProductionRule[]{
                new TypeRule(Token.Type.MUL_OP), factor
        })));

        factor.appendRule(new TypeRule(Token.Type.INTEGER));
        factor.appendRule(new TypeRule(Token.Type.FLOAT));
        factor.appendRule(new TypeRule(Token.Type.ID));
        factor.appendRule(new BacktrackingRule(new ProductionRule[]{
                new TypeRule(Token.Type.L_PAREN), expr, new TypeRule(Token.Type.R_PAREN)
        }));
        factor.appendRule(new BacktrackingRule(new ProductionRule[]{
                new LiteralRule("-"), factor
        }));


        return expr.parse(tokenStream) && expr.consume(tokenStream) && tokenStream.consumed();
    }
}
