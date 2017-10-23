
/**
 * Token is a simple wrapper to be handed over to the parser to make the
 * job of creating the AST a little easier
 */
public class Token {

    /**
     * Make identifying Tokens easier for the parser
     */
    public enum Type {
        ADDOP, MULOP, ID, INTEGER, FLOAT, L_PAREN, R_PAREN, TK_EOF
    }

    /**
     * What type of token this object holds
     */
    Type type;

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
    Token(Type t, String l) {
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