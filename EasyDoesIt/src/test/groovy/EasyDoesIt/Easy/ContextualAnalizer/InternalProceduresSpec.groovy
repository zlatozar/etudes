package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class InternalProceduresSpec extends ASTSpec {

    def 'Procedure definition'() {

        given: 'Parser and simple procedure (param pass by name)'
        Parser parser = getParserFor(
                'PROCEDURE abs(x REAL):' +
                '   SET x := 0;' +
                'END PROCEDURE abs;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Procedure definition with many parameters'() {

        given: 'Parser and simple procedure (param pass by name)'
        Parser parser = getParserFor(
                'PROCEDURE abs(x REAL, y INTEGER, z STRING):' +
                '   SET x := 0;' +
                'END PROCEDURE abs;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Function definition'() {

        given: 'Parser and simple function (param pass by name)'
        Parser parser = getParserFor(
                'FUNCTION abs(x REAL NAME) REAL:' +
                '   SET x := 0;' +
                'END FUNCTION abs;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Function definition with many parameters'() {

        given: 'Parser and simple function'
        Parser parser = getParserFor(
                'FUNCTION abs(x REAL, y INTEGER, z STRING) REAL:' +
                '    ;' +
                'END FUNCTION abs;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Function and procedure'() {

        given: 'Parser both'
        Parser parser = getParserFor(
                'FUNCTION abs(x REAL NAME) REAL:' +
                '   SET x := 0;' +
                'END FUNCTION abs;' +

                'PROCEDURE abs2(x REAL, y INTEGER, z STRING):' +
                '   ;' +
                'END PROCEDURE abs2;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }
}