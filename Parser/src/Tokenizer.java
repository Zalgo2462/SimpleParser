
import java.util.*;

public class Tokenizer {

    /**
     * Valid letters
     */
    private static final String l = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";

    /**
     * Valid digits
     */
    private static final String d = "0123456789";

    /**
     * Valid operators
     */
    private static final String o = "+-*/%()";

    /**
     * Turn our valid letters into a queryable set
     */
    private Set<Character> letters = new HashSet<>();

    /**
     * Turn our valid digits into a queryable set
     */
    private Set<Character> digits = new HashSet<>();

    /**
     * Turn our valid operations into a queryable set
     */
    private Set<Character> ops = new HashSet<>();


    /**
     * Make identifying Tokens easier for the parser
     */
    public enum TokenType {
        ADDOP, MULOP, ID, INTEGER, FLOAT, L_PAREN, R_PAREN, TK_EOF
    }

    /**
     * Token is a simple wrapper to be handed over to the parser to make the
     * job of creating the AST a little easier
     */
    public class Token {
        /**
         * What type of token this object holds
         */
        TokenType type;

        /**
         * The literal value of the token from the input
         */
        String literal;

        /**
         * Build a new Token
         * @param t TokenType with the type of the token being built
         * @param l String containing the literal parsed value that generated
         *          this token
         */
        Token(TokenType t, String l) {
            type = t;
            literal = l;
        }

        public String toString() {
            switch(type) {
                case ADDOP:
                    return "ADDOP(" + literal + ")";

                case MULOP:
                    return "MULOP(" + literal + ")";

                case ID:
                    return "ID(" + literal + ")";

                case FLOAT:
                    return "FLOAT(" + literal + ")";

                case INTEGER:
                    return "INTEGER(" + literal + ")";

                case L_PAREN:
                    return "L_PAREN(" + literal + ")";

                case R_PAREN:
                    return "R_PAREN(" + literal + ")";

                case TK_EOF:
                    return "TK_EOF(" + literal + ")";

            }

            return "";
        }
    }

    private List<Token> tokens;

    public Tokenizer(String input) throws ParseError {

        for(char c : l.toCharArray()) {
            letters.add(c);
        }

        for(char c : d.toCharArray()) {
            digits.add(c);
        }

        for(char c : o.toCharArray()) {
            ops.add(c);
        }

        tokens = new ArrayList<>();

        // Split the input string into whitespace delimited chunks
        String chunks[] = input.split("\\s+");

        for(String chunk : chunks) {
                parseTokens(chunk);
        }

        tokens.add(new Token(TokenType.TK_EOF, ""));

    }

    private void parseTokens(String s) throws ParseError {

        while(s.length() > 0) {
            if(letters.contains(s.charAt(0))) {
                s = parseId(s);
            }
            else if (digits.contains(s.charAt(0))) {
                s = parseNum(s);
            }
            else if (ops.contains(s.charAt(0))) {
                s = parseOps(s);
            }
            else
            {
                throw new ParseError("Unrecognized char in input" + s.charAt(0));
            }
        }
    }

    private String parseId(String s) {

        int offt = 0;
        StringBuilder lit = new StringBuilder();

        while(offt < s.length() &&
                (letters.contains(s.charAt(offt)) ||
                        digits.contains(s.charAt(offt)))) {
            lit.append(s.charAt(offt));
            offt++;
        }

        tokens.add(new Token(TokenType.ID, lit.toString()));
        if(offt == s.length()) {
            return "";
        }
        return s.substring(offt + 1);
    }

    private String parseNum(String s) throws ParseError {

        int offt = 0;
        int deccount = 0;
        StringBuilder lit = new StringBuilder();

        while(offt < s.length() &&
                (digits.contains(s.charAt(offt)) || s.charAt(offt) == '.')) {
            if(s.charAt(offt) == '.') {
                deccount++;
            }
            lit.append(s.charAt(offt));
            offt++;
        }

        if (deccount == 0) {
            tokens.add(new Token(TokenType.INTEGER, lit.toString()));
        }
        else if (deccount == 1) {
            tokens.add(new Token(TokenType.FLOAT, lit.toString()));
        }
        else {
            throw new ParseError("Unrecognized token pattern, contains too many \".\"");
        }
        if(offt == s.length()) {
            return "";
        }
        return s.substring(offt);
    }

    private String parseOps(String s) {
        switch (s.charAt(0)) {
            case '(':
                tokens.add(new Token(TokenType.L_PAREN, "("));
                break;
            case ')':
                tokens.add(new Token(TokenType.R_PAREN, ")"));
                break;
            case '+':
                tokens.add(new Token(TokenType.ADDOP, "+"));
                break;
            case '-':
                tokens.add(new Token(TokenType.ADDOP, "-"));
                break;
            case '*':
                tokens.add(new Token(TokenType.MULOP, "*"));
                break;
            case '/':
                tokens.add(new Token(TokenType.MULOP, "/"));
                break;
            case '%':
                tokens.add(new Token(TokenType.MULOP, "%"));
                break;
        }

        if(s.length() == 1) {
            return "";
        }
        return s.substring(1);
    }

    public String toString() {
        StringBuilder retval = new StringBuilder("[ ");
        for(Token t : tokens) {
            if (t.type == TokenType.TK_EOF) {
                retval.append(t.toString());
                break;
            }
            retval.append(t.toString()).append(", ");
        }

        return retval + " ]";
    }
}
