package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.Program
import EasyDoesIt.Easy.SyntacticAnalizer.Parser
import spock.lang.Ignore

@Ignore
class CheckerSpec extends ASTSpec {

    // example from the book
    private static final String SIMPLE_TRIANGLE_FILE = 'src/test/resources/example.esy'

    def 'How checker works'() {

        given: 'Easy source file reader, scanner, parser and checker'
        Parser parser = getParserForFile(SIMPLE_TRIANGLE_FILE)

        when: 'Second pass starts'
        Program programAST = parser.parseProgram()

        then: 'AST structure will be traced without error'
        getChecker().check(programAST)
        assert programAST
    }
}