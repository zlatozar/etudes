package EasyDoesIt.Easy

import EasyDoesIt.Easy.ContextualAnalizer.Checker
import EasyDoesIt.Easy.SyntacticAnalizer.Parser
import EasyDoesIt.Easy.SyntacticAnalizer.Scanner
import EasyDoesIt.Easy.SyntacticAnalizer.SourceFile
import spock.lang.Specification

class ASTSpec extends Specification {

    private static final ErrorReporter reporter = new ErrorReporter();

    protected static Parser getParserFor(String statement) {
        String program = createProgram(statement)

        SourceFile sourceFile = new SourceFile(program, false)
        Scanner scanner = new Scanner(sourceFile);

        return new Parser(scanner, reporter);
    }

    protected static Parser getParserForFile(String programFile) {
        SourceFile sourceFile = new SourceFile(programFile)
        Scanner scanner = new Scanner(sourceFile);

        return new Parser(scanner, reporter);
    }

    protected static Checker getChecker() {
        return new Checker(reporter)
    }

    protected static String createProgram(String statement) {
        return "PROGRAM simpleProgram:\n" +
                "$statement\n" +
                ";" +
                "END PROGRAM simple;"
    }

    // Helper functions

    def 'dummy'() {
        given: 'some test'
        when: 'call it'
        then: assert true
    }
}