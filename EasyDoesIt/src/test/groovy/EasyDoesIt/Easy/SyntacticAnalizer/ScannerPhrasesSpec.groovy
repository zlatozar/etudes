package EasyDoesIt.Easy.SyntacticAnalizer

import spock.lang.Specification
import spock.lang.Title

@Title('Scanner specification')
class ScannerPhrasesSpec extends Specification {

    private static final String DECLARATION = ' DECLARE topnum INTEGER;'
    private static final String PROGRAM = 'PROGRAM Eratosthenes:'
    private static final String FUNCTION = 'FUNCTION abs(x REAL) REAL:'
    private static final String IF_ELSE = '/*...*/ IF x < 0 THEN RETURN -x; ELSE RETURN x; FI;'
    private static final String SET = 'SET count := count + 1;'
    private static final String STING_LITERAL = 'OUTPUT "Prime[" || count'

    def 'DECLARE'() {
        given: 'Integer declaration'
        SourceFile sourceFile = new SourceFile(DECLARATION, false)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanner starts'
        Token declareKeyWord = scanner.scan()
        Token indentifier = scanner.scan()
        Token typeIndentifier = scanner.scan()

        then: 'Tokens should be with correct kind'
        assert declareKeyWord.kind == Token.DECLARE
        assert indentifier.kind == Token.IDENTIFIER
        assert typeIndentifier.kind == Token.IDENTIFIER
    }

    def 'PROGRAM'() {
        given: 'Integer declaration'
        SourceFile sourceFile = new SourceFile(PROGRAM, false)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanner starts'
        Token programKeyword = scanner.scan()
        Token indentifier = scanner.scan()

        then: 'Tokens should be with correct kind'
        assert programKeyword.kind == Token.PROGRAM
        assert indentifier.kind == Token.IDENTIFIER
    }

    def 'FUNCTION declaration'() {
        given: 'Integer declaration'
        SourceFile sourceFile = new SourceFile(FUNCTION, false)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanner starts'
        Token functionKeyWord = scanner.scan()
        Token funcName = scanner.scan()
        Token leftParen = scanner.scan()
        Token paramName = scanner.scan()
        Token typeName = scanner.scan()
        Token rightParen = scanner.scan()
        Token typeName2 = scanner.scan()
        Token colon = scanner.scan()

        then: 'Tokens should be with correct kind'
        assert functionKeyWord.kind == Token.FUNCTION
        assert funcName.kind == Token.IDENTIFIER
        assert leftParen.kind == Token.LPAREN
        assert paramName.kind == Token.IDENTIFIER
        assert typeName.kind == Token.IDENTIFIER
        assert rightParen.kind == Token.RPAREN
        assert typeName2.kind == Token.IDENTIFIER
        assert colon.kind == Token.COLON
    }

    def 'IF ELSE declaration'() {
        given: 'Integer declaration'
        SourceFile sourceFile = new SourceFile(IF_ELSE, false)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanner starts'
        Token ifKeyWord = scanner.scan()
        Token x = scanner.scan()
        Token less = scanner.scan()
        Token positiveDigit = scanner.scan()
        Token then = scanner.scan()
        Token returnKeyWord = scanner.scan()
        Token minus = scanner.scan()
        Token xTrue = scanner.scan()
        Token semicolon = scanner.scan()
        Token elseKeyWord = scanner.scan()
        Token returnKeyWord2 = scanner.scan()
        Token xFalse = scanner.scan()
        Token semicolon2 = scanner.scan()
        Token fi = scanner.scan()
        Token semicolon3 = scanner.scan()

        then: 'Tokens should be with correct kind'
        assert ifKeyWord.kind == Token.IF
        assert x.kind == Token.IDENTIFIER
        assert less.kind == Token.OPERATOR
        assert positiveDigit.kind == Token.INTLITERAL
        assert then.kind == Token.THEN
        assert returnKeyWord.kind == Token.RETURN
        assert minus.kind == Token.OPERATOR
        assert xTrue.kind == Token.IDENTIFIER
        assert semicolon.kind == Token.SEMICOLON
        assert elseKeyWord.kind == Token.ELSE
        assert returnKeyWord2.kind == Token.RETURN
        assert xFalse.kind == Token.IDENTIFIER
        assert semicolon2.kind == Token.SEMICOLON
        assert fi.kind == Token.FI
        assert semicolon3.kind == Token.SEMICOLON
    }

    def 'SET statement'() {
        given: 'Integer declaration'
        SourceFile sourceFile = new SourceFile(SET, false)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanner starts'
        Token set = scanner.scan()
        Token var = scanner.scan()
        Token becomes = scanner.scan()
        Token var2 = scanner.scan()
        Token plus = scanner.scan()
        Token integer = scanner.scan()
        Token semicolon = scanner.scan()

        then: 'Tokens should be with correct kind'
        assert set.kind == Token.SET
        assert var.kind == Token.IDENTIFIER
        assert becomes.kind == Token.BECOMES
        assert var2.kind == Token.IDENTIFIER
        assert plus.kind == Token.OPERATOR
        assert integer.kind == Token.INTLITERAL
        assert semicolon.kind == Token.SEMICOLON
    }

    def 'Not empty string literal'() {
        given: 'String literal declaration'
        SourceFile sourceFile = new SourceFile(STING_LITERAL, false)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanner starts'
        Token output = scanner.scan()
        Token stringLiteral = scanner.scan()
        Token operator1 = scanner.scan()
        Token variable = scanner.scan()

        then: 'Tokens should be with correct kind'
        assert output.kind == Token.OUTPUT
        assert stringLiteral.kind == Token.CHARLITERAL
        assert operator1.kind == Token.OPERATOR
        assert variable.kind == Token.IDENTIFIER
    }

    def 'Empty string literal'() {
        given: 'String literal declaration'
        SourceFile sourceFile = new SourceFile('""', false)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanner starts'
        Token stringLiteral = scanner.scan()

        then: 'Tokens should be with correct kind'
        assert stringLiteral.kind == Token.CHARLITERAL
    }

    def 'Real numbers'() {
        given: 'Real number'
        SourceFile sourceFile = new SourceFile('2.0', true)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanner starts'
        Token stringLiteral = scanner.scan()

        then: 'Token should be with correct kind'
        assert stringLiteral.kind == Token.INTLITERAL
    }
}