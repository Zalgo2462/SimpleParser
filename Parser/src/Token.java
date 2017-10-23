
/**
 * Token is a simple wrapper to be handed over to the parser to make the
 * job of creating the AST a little easier
 */
public class Token {

    /**
     * Type makes identifying Tokens easier for the parser
     */
    public enum Type {
        ADD_OP, MUL_OP, ID, INTEGER, FLOAT, L_PAREN, R_PAREN, TK_EOF
    }

    /**
     * type is the Type of the token this object holds
     */
    private final Type type;

    /**
     * literal is the value of the token from the input
     */
    private final String literal;

    /**
     * Build a new Token
     * @param type Type with the type of the token being built
     * @param literal String containing the literal parsed value that generated
     *          this token
     */
    public Token(final Type type, final String literal) {
        this.type = type;
        this.literal = literal;
    }


    public Type getType() {
        return type;
    }

    public String getLiteral() {
        return literal;
    }

    /**
     * @return a string representation with the Type and literal value of the token
     */
    public String toString() {
        switch(type) {
            case ADD_OP:
                return "ADD_OP(" + literal + ")";

            case MUL_OP:
                return "MUL_OP(" + literal + ")";

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