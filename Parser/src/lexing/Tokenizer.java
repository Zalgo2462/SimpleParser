package lexing;

import parsing.TokenStream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * lexing.Tokenizer takes in an input string and tokenizes it according to the
 * specifications of the PL HW #3 assignment.
 */
public class Tokenizer {

    /**
     * LETTERS represents the valid letters
     */
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";

    /**
     * DIGITS represents the valid digits
     */
    private static final String DIGITS = "0123456789";

    /**
     * OPERATORS represents the valid operators
     */
    private static final String OPERATORS = "+-*/%()";

    /**
     * Turn our valid letters, digits, and operations into a queryable set
     */
    private final Set<Character> letters;
    private final Set<Character> digits;
    private final Set<Character> ops;

    /**
     * lexing holds the parsed lexing from the input
     */
    private final List<Token> tokens;

    /**
     * lexing.Tokenizer takes in an input string and tokenizes it according to the
     * specifications of the PL HW #3 assignment.
     *
     * @param input The string to tokenize
     * @throws LexError Generated when the string does not conform to the specifications
     */
    public Tokenizer(final String input) throws LexError {

        // Initialize our hashsets
        letters = new HashSet<>();
        digits = new HashSet<>();
        ops = new HashSet<>();

        for (char c : LETTERS.toCharArray()) {
            letters.add(c);
        }

        for (char c : DIGITS.toCharArray()) {
            digits.add(c);
        }

        for (char c : OPERATORS.toCharArray()) {
            ops.add(c);
        }

        // Start lexing
        tokens = new ArrayList<>();

        // Split the input string into whitespace delimited chunks
        // This is a cheap way to remove the whitespace from the string
        parseTokens(input);

        //this.tokens.add(new Token(Token.Type.TK_EOF, ""));

    }

    /**
     * parseTokens generates lexing.Token objects and stores them in this.lexing.
     *
     * @param s The string to tokenize
     * @throws LexError Generated when the string does not conform to the specifications
     */
    private void parseTokens(String s) throws LexError {
        s = s.replaceAll("\\s+", "");
        while (s.length() > 0) {
            if (letters.contains(s.charAt(0))) {
                s = parseId(s);
            } else if (digits.contains(s.charAt(0))) {
                s = parseNum(s);
            } else if (ops.contains(s.charAt(0))) {
                s = parseOps(s);
            } else {
                throw new LexError("Unrecognized char in input" + s.charAt(0));
            }
        }
    }

    /**
     * parseId assumes the beginning of input is a valid lexing.Token.Type.ID, reads the ID from
     * input, stores the ID in this.lexing, and returns the input string sans the
     * ID.
     *
     * @param input the string to parse the id out of
     * @return the input string without the id, "" if input is invalid
     */
    private String parseId(final String input) {

        int offset = 0;
        StringBuilder literal = new StringBuilder();

        while (offset < input.length() &&
                (letters.contains(input.charAt(offset)) ||
                        digits.contains(input.charAt(offset)))) {
            literal.append(input.charAt(offset));
            offset++;
        }

        this.tokens.add(new Token(Token.Type.ID, literal.toString()));
        if (offset == input.length()) {
            return "";
        }
        return input.substring(offset);
    }

    /**
     * parseNum assumes the beginning of input is a valid lexing.Token.Type.INTEGER or lexing.Token.Type.FLOAT,
     * reads the number from input, stores the number in this.lexing, and returns the input string sans the
     * number.
     *
     * @param input the string to parse the number out of
     * @return the input string without the number, "" if input is invalid
     * @throws LexError Generated when there are multiple decimal points in the token
     */
    private String parseNum(final String input) throws LexError {

        int offset = 0;
        int decCount = 0;
        StringBuilder literal = new StringBuilder();

        while (offset < input.length() &&
                (digits.contains(input.charAt(offset)) ||
                        input.charAt(offset) == '.')) {

            if (input.charAt(offset) == '.') {
                decCount++;
            }
            literal.append(input.charAt(offset));
            offset++;
        }

        if (decCount == 0) {
            this.tokens.add(new Token(Token.Type.INTEGER, literal.toString()));
        } else if (decCount == 1) {
            this.tokens.add(new Token(Token.Type.FLOAT, literal.toString()));
        } else {
            throw new LexError("Unrecognized token pattern, contains too many \".\"");
        }
        if (offset == input.length()) {
            return "";
        }
        return input.substring(offset);
    }


    /**
     * parseOps assumes the beginning of input is a valid L_PAREN, R_PAREN, MUL_OP, or ADD_OP,
     * reads the operation from input, stores the operation in this.lexing, and returns the input string sans the
     * operation.
     *
     * @param input the string to parse the operation out of
     * @return the input string without the operation, "" if input is invalid
     */
    private String parseOps(final String input) {
        switch (input.charAt(0)) {
            case '(':
                this.tokens.add(new Token(Token.Type.L_PAREN, "("));
                break;
            case ')':
                this.tokens.add(new Token(Token.Type.R_PAREN, ")"));
                break;
            case '+':
                this.tokens.add(new Token(Token.Type.ADD_OP, "+"));
                break;
            case '-':
                this.tokens.add(new Token(Token.Type.ADD_OP, "-"));
                break;
            case '*':
                this.tokens.add(new Token(Token.Type.MUL_OP, "*"));
                break;
            case '/':
                this.tokens.add(new Token(Token.Type.MUL_OP, "/"));
                break;
            case '%':
                this.tokens.add(new Token(Token.Type.MUL_OP, "%"));
                break;
            default:
                return ""; //should be unreachable in normal operation
        }
        return input.substring(1);
    }

    /**
     * @return a list of lexing found by the tokenizer in order
     */
    public TokenStream getTokens() {
        return new TokenStream(tokens);
    }

    /**
     * @return an annotated description of the lexing generated from the given input
     */
    public String toString() {
        StringBuilder returnVal = new StringBuilder("[ ");
        for (int i = 0; i < this.tokens.size(); i++) {
            Token t = this.tokens.get(i);
            if (i == this.tokens.size() - 1) {
                returnVal.append(t.toString());
            } else {
                returnVal.append(t.toString()).append(", ");
            }
        }

        return returnVal + " ]";
    }
}
