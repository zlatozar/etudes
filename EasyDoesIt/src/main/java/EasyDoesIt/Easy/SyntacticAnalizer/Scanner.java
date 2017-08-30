package EasyDoesIt.Easy.SyntacticAnalizer;

import java.io.IOException;

/**
 * Since the source  program text actually consists of individual characters, and a token
 * may consist of several characters, scanning is needed to group the characters into tokens,
 * and to discard other text such as blank space and comments.
 */
public final class Scanner {

    // defines from where code is taken
    private final SourceFile sourceFile;
    private char currentChar;
    private boolean debug;

    private StringBuilder currentSpelling;
    private boolean currentlyScanningToken;

    public Scanner(SourceFile source) {
        this.sourceFile = source;
        this.currentChar = sourceFile.getSource();
        this.debug = false;
    }

    public Token scan() {

        Token token;
        SourcePosition pos;
        int kind;

        currentlyScanningToken = false;

        // part of the scanner's function is to discard blank space and comments
        while (isCommentBegins(currentChar) || currentChar == ' ' || currentChar == '\n' || currentChar == '\r' || currentChar == '\t') {
            scanSeparator();
        }

        currentlyScanningToken = true;
        currentSpelling = new StringBuilder();

        pos = new SourcePosition();
        pos.start = sourceFile.getCurrentLineNumber();

        kind = scanToken();

        pos.finish = sourceFile.getCurrentLineNumber();
        token = new Token(kind, currentSpelling.toString(), pos);

        if (debug) {
            System.out.println(token);
        }

        return token;
    }

    public Scanner withDebugging() {
        debug = true;
        return this;
    }

//_____________________________________________________________________________
//

    private boolean isCommentBegins(char c) {
        return c == '/' && nextIs('*');
    }

    private boolean nextIs(char c) {
        return c == probeIt();
    }

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    /**
     * Returns true iff the given character is an operator character.
     */
    private boolean isOperator(char c) {

        return (c == '+' || c == '-' || c == '*' || c == '/' ||
                c == '<' || c == '=' || c == '>' || c == '&' ||
                c == '|');
    }

    /**
     * Appends the current character to the current token, and gets
     * the next character from the source program. It is used to look
     * one symbol ahead.
     */
    private void takeIt() {

        if (currentlyScanningToken) {
            currentSpelling.append(currentChar);
        }

        currentChar = sourceFile.getSource();
    }

    private char probeIt() {
        char c;

        try {
            c = sourceFile.getProbe();

        } catch (IOException e) {
            c = SourceFile.EOT;
        }

        return c;
    }

    /**
     * Skips a single separator.
     */
    private void scanSeparator() {

        switch (currentChar) {

            case '/':
            {
                takeIt();

                if (currentChar == '*') {
                    takeIt();

                    while ((currentChar != '*') && (currentChar != SourceFile.EOT)) {
                        takeIt();
                    }

                    if (currentChar == '*') {
                        takeIt();

                        if (currentChar == '/') {
                            takeIt();
                        }
                    }
                }
            }
            break;

            case ' ':
            case '\n':
            case '\r':
            case '\t':
                takeIt();
                break;
        }
    }

    private int scanToken() {

        switch (currentChar) {

            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':

                takeIt();

                while (isLetter(currentChar) || isDigit(currentChar)) {
                    takeIt();
                }

                return Token.IDENTIFIER;

            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':

                takeIt();

                while (isDigit(currentChar) || currentChar == '.') {
                    takeIt();
                }

                return Token.INTLITERAL;

            case '+':
            case '-':
            case '*':
            case '/':
            case '<':
            case '=':
            case '>':
            case '&':
            case '|':

                takeIt();

                while (isOperator(currentChar)) {
                    takeIt();
                }

                return Token.OPERATOR;

            case '\"':

                takeIt();

                // empty string
                if (currentChar == '\"') {
                    return Token.CHARLITERAL;
                }

                while (currentChar != '\"' && (currentChar != SourceFile.EOT)) {
                    takeIt();
                }

                if (currentChar == '\"') {
                    takeIt();
                    return Token.CHARLITERAL;

                } else {
                    return Token.ERROR;
                }

            case '.':
                takeIt();
                return Token.DOT;

            case ':':
                takeIt();

                if (currentChar == '=') {
                    takeIt();
                    return Token.BECOMES;

                } else {
                    return Token.COLON;
                }

            case ';':
                takeIt();
                return Token.SEMICOLON;

            case ',':
                takeIt();
                return Token.COMMA;

            case '(':
                takeIt();
                return Token.LPAREN;

            case ')':
                takeIt();
                return Token.RPAREN;

            case '[':
                takeIt();
                return Token.LBRACKET;

            case ']':
                takeIt();
                return Token.RBRACKET;

            case SourceFile.EOT:
                return Token.EOT;

            default:
                takeIt();
                return Token.ERROR;
        }
    }

}

