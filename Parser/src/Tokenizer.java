
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
     * Turn our valid letters, digits, and operations into a queryable set
     */
    private Set<Character> letters, digits, ops;

    private List<Token> tokens;

    public Tokenizer(String input) throws ParseError {

        letters = new HashSet<Character>();
        digits = new HashSet<Character>();
        ops = new HashSet<Character>();

        for(char c : l.toCharArray()) {
            letters.add(c);
        }

        for(char c : d.toCharArray()) {
            digits.add(c);
        }

        for(char c : o.toCharArray()) {
            ops.add(c);
        }

        tokens = new ArrayList<Token>();

        // Split the input string into whitespace delimited chunks
        String chunks[] = input.split("\\s+");

        for(String chunk : chunks) {
                parseTokens(chunk);
        }

        tokens.add(new Token(Token.Type.TK_EOF, ""));

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

        tokens.add(new Token(Token.Type.ID, lit.toString()));
        if(offt == s.length()) {
            return "";
        }
        return s.substring(offt);
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
            tokens.add(new Token(Token.Type.INTEGER, lit.toString()));
        }
        else if (deccount == 1) {
            tokens.add(new Token(Token.Type.FLOAT, lit.toString()));
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
                tokens.add(new Token(Token.Type.L_PAREN, "("));
                break;
            case ')':
                tokens.add(new Token(Token.Type.R_PAREN, ")"));
                break;
            case '+':
                tokens.add(new Token(Token.Type.ADDOP, "+"));
                break;
            case '-':
                tokens.add(new Token(Token.Type.ADDOP, "-"));
                break;
            case '*':
                tokens.add(new Token(Token.Type.MULOP, "*"));
                break;
            case '/':
                tokens.add(new Token(Token.Type.MULOP, "/"));
                break;
            case '%':
                tokens.add(new Token(Token.Type.MULOP, "%"));
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
            if (t.type == Token.Type.TK_EOF) {
                retval.append(t.toString());
                break;
            }
            retval.append(t.toString()).append(", ");
        }

        return retval + " ]";
    }
}
