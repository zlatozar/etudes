package EasyDoesIt.Easy.SyntacticAnalizer;

/**
 * The interface between the scanner and the parser is a stream of tokens.
 * A token is an atomic symbol of the source program. The interface between
 * the scanner and the parser is a stream of tokens.
 */
final class Token {

    private final static int firstReservedWord = Token.ARRAY;
    private final static int lastReservedWord = Token.WHILE;

    private static String[] spellingTable =
            {
                    // terminals
                    "<int>",
                    "<real>",
                    "<char>",
                    "<identifier>",
                    "<operator>",

                    "ARRAY",
                    "BEGIN",
                    "BY",
                    "CALL",
                    "CASE",
                    "DECLARE",
                    "DO",
                    "ELSE",
                    "END",
                    "EXIT",
                    "FI",
                    "FIELD",
                    "FOR",
                    "FUNCTION",
                    "IF",
                    "INPUT",
                    "IS",
                    "NAME",
                    "OF",
                    "OTHERWISE",
                    "OUTPUT",
                    "PROCEDURE",
                    "PROGRAM",
                    "REPEAT",
                    "REPENT",
                    "RETURN",
                    "SELECT",
                    "SET",
                    "STRUCTURE",
                    "THEN",
                    "TO",
                    "TYPE",
                    "WHILE",

                    ".",
                    ":",
                    ";",
                    ",",
                    ":=",

                    "(",
                    ")",
                    "[",
                    "]",

                    "",       // EOT
                    "<error>"
            };

    protected int kind;
    protected final String spelling;
    protected final SourcePosition position;

    // Each token is completely described by its kind and spelling
    public Token(int kind, String spelling, SourcePosition position) {

        // The lexical grammar did not distinguish between identifiers and keywords.
        // Nevertheless, the scanner must properly classify these tokens.
        if (kind == Token.IDENTIFIER) {

            int currentKind = firstReservedWord;
            boolean searching = true;

            while (searching) {
                int comparison = spellingTable[currentKind].compareTo(spelling);

                if (comparison == 0) {
                    this.kind = currentKind;
                    searching = false;

                } else if (comparison > 0 || currentKind == lastReservedWord) {
                    this.kind = Token.IDENTIFIER;
                    searching = false;

                } else {
                    currentKind++;
                }
            }

        } else {
            this.kind = kind;

        }

        this.spelling = spelling;
        this.position = position;
    }

    public static String spell(int kind) {
        return spellingTable[kind];
    }

//_____________________________________________________________________________
//                               Constants denoting different kinds of <Token>

    // literals, identifiers, operators...

    public static final int INTLITERAL    = 0;
    public static final int REALLITERAL   = 1;
    public static final int CHARLITERAL   = 2;
    public static final int IDENTIFIER    = 3;
    public static final int OPERATOR      = 4;

    // reserved words - must be in alphabetical order...

    public static final int ARRAY =      5;   // first reserved word
    public static final int BEGIN =      6;
    public static final int BY =         7;
    public static final int CALL =       8;
    public static final int CASE =       9;
    public static final int DECLARE =    10;
    public static final int DO =        11;
    public static final int ELSE =      12;
    public static final int END =       13;
    public static final int EXIT =      14;
    public static final int FI =        15;
    public static final int FIELD =     16;
    public static final int FOR =       17;
    public static final int FUNCTION =  18;
    public static final int IF =        19;
    public static final int INPUT =     20;
    public static final int IS =        21;
    public static final int NAME =      22;
    public static final int OF =        23;
    public static final int OTHERWISE = 24;
    public static final int OUTPUT    = 25;
    public static final int PROCEDURE = 26;
    public static final int PROGRAM =   27;
    public static final int REPEAT =    28;
    public static final int REPENT =    29;
    public static final int RETURN =    30;
    public static final int SELECT =    31;
    public static final int SET =       32;
    public static final int STRUCTURE = 33;
    public static final int THEN =      34;
    public static final int TO =        35;
    public static final int TYPE =      36;
    public static final int WHILE =     37;  // last reserved word

    // punctuation...

    public static final int DOT       = 38;
    public static final int COLON     = 39;
    public static final int SEMICOLON = 40;
    public static final int COMMA     = 41;
    public static final int BECOMES   = 42;  // :=

    // brackets...

    public static final int LPAREN   = 43;
    public static final int RPAREN   = 44;
    public static final int LBRACKET = 45;
    public static final int RBRACKET = 46;

    // special tokens...

    // Note that EOT represents the end of the source text
    public static final int EOT   = 47;
    public static final int ERROR = 48;

    @Override
    public String toString() {
        return "Kind=" + kind + ", spelling=" + spelling + ", position=" + position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;
        return kind == token.kind && spelling.equals(token.spelling);
    }

    @Override
    public int hashCode() {
        int result = kind;
        result = 31 * result + spelling.hashCode();
        return result;
    }
}
