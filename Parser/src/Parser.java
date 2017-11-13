import lexing.LexError;
import lexing.Token;
import lexing.Tokenizer;
import parsing.TokenStream;
import parsing.rules.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Implementation of the top level parsing functionality as defined in the
 * specifications. Contains the main() entry point for the program.
 */
public class Parser {

    /**
     * Entry point of the parser program
     *
     *  Options:
     *      -t    Output the debug style token string to what the tokenizer did
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String input;
        Tokenizer t;
        String tokenStr;
        StringBuilder validMsg;
        try {
            System.out.print("Enter expression: ");
            input = buff.readLine();
            while (!input.equals("")) {
                tokenStr = "";
                validMsg = new StringBuilder(input);
                try {
                    t = new Tokenizer(input);
                    tokenStr = t.toString();
                    if( parse(t.getTokens()) ) {
                        validMsg = t.cleanInput();
                        validMsg.append(" is a valid expression");
                    } else {
                        validMsg.append(" is not a valid expression");
                    }

                } catch (LexError e) {
                    validMsg.append(" is not a valid expression");
                }

                System.out.println(validMsg);

                if (args.length > 0 && args[0].equals("-t")) {
                    System.out.println(tokenStr);
                }

                System.out.print("\nEnter expression: ");
                input = buff.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        System.out.println("(end of input)");
    }

    /**
     * Launch the stream parser
     *
     * @param tokenStream Token stream to be parsed
     * @return true if stream contained a valid sequence of tokens, false otherwise
     */
    private static boolean parse(final TokenStream tokenStream) {
        BacktrackingRule expr = new BacktrackingRule();
        BacktrackingRule term = new BacktrackingRule();
        OrRule factor = new OrRule();


        expr.appendRule(term);
        expr.appendRule(new OptionalRule(new RepitionRule(new BacktrackingRule(new ProductionRule[]{
                new TypeRule(Token.Type.ADD_OP), term
        }))));

        term.appendRule(factor);
        term.appendRule(new OptionalRule(new RepitionRule(new BacktrackingRule(new ProductionRule[]{
                new TypeRule(Token.Type.MUL_OP), factor
        }))));

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
