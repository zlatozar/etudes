package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class CallsSpec extends ASTSpec {

    def 'Call definition without parameters'() {

        given: 'Simple call'
        Parser parser = getParserFor(
                'PROCEDURE abs:' +
                '   ;' +
                'END PROCEDURE abs;' +

                'CALL abs;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Call definition with three parameters'() {

        given: 'Parser and call with parameters'
        Parser parser = getParserFor(
                'PROCEDURE abs(x INTEGER, y INTEGER, z INTEGER):' +
                '   SET x := 0;' +
                'END PROCEDURE abs;' +

                'CALL abs(1, 2, 3);')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Call function with three parameters'() {

        given: 'Parser and function call with parameters'
        Parser parser = getParserFor(
                'FUNCTION dummy(x INTEGER) INTEGER:\n' +
                '   SET x := 0;' +
                'END FUNCTION dummy;' +

                'DECLARE a INTEGER;' +
                'SET a := dummy(42);')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

}