package EasyDoesIt.Easy.SyntacticAnalizer

import spock.lang.Specification
import spock.lang.Title

@Title('Scanner comments specification')
class ScannerCommentsSpec extends Specification {

    private String sample = '/*Sample*/'

    private String empty = ' /**/ '

    private String manyLines = '/*\n' +
            'First line\n' +
            'Second line\n' +
            '*/'
    private String weird = '/* Weird one\n' +
            '\n' +
            '\n' +
            '\n' +
            '\n' +
            '*/'

    Token currentToken

    def 'Sample C-style comment'() {
        given: 'Scanner and source that contains only one comment'
        SourceFile sourceFile = new SourceFile(sample, false)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanning starts'
        currentToken = scanner.scan()

        then: 'Sample one'
        assert currentToken.kind == Token.EOT
    }

    def 'Empty C-style comment'() {
        given: 'Scanner and source that contains only one comment'
        SourceFile sourceFile = new SourceFile(empty, false)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanning starts'
        currentToken = scanner.scan()

        then: 'Empty one'
        assert currentToken.kind == Token.EOT
    }

    def 'Many lines C-style comment'() {
        given: 'Scanner and source that contains only one comment but with many lines'
        SourceFile sourceFile = new SourceFile(manyLines, false)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanning starts'
        currentToken = scanner.scan()

        then: 'Many lines'
        assert currentToken.kind == Token.EOT
    }

    def 'Ugly C-style comment'() {
        given: 'Scanner and source that contains only one comment but with many lines'
        SourceFile sourceFile = new SourceFile(weird, false)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanning starts'
        currentToken = scanner.scan()

        then: 'Many weird lines will be skipped'
        assert currentToken.kind == Token.EOT
    }

    def 'Dived not a comment'() {
        given: 'Scanner and source that contains dividing expression'
        SourceFile sourceFile = new SourceFile('a/b', false)
        Scanner scanner = new Scanner(sourceFile)

        when: 'Scanning starts'
        currentToken = scanner.scan()

        then: 'All symbols will be return'
        assert currentToken.spelling == 'a'
    }

}