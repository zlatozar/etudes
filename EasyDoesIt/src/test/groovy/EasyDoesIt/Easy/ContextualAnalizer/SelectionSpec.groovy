package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class SelectionSpec extends ASTSpec {

    def 'Selection without escape'() {

        given: 'Parser a selection statment'
        Parser parser = getParserFor(
                'DECLARE a INTEGER;' +
                'SET a := 1;' +

                'SELECT TRUE OF' +
                '   CASE (a > 0):' +
                '      DECLARE (x, ra) REAL;' +
                '      DECLARE sqrt INTEGER;' +
                '      ;' +
                'END SELECT;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Selection with escape'() {

        given: 'Parser a selection statment'
        Parser parser = getParserFor(
                'DECLARE a INTEGER;' +
                'SET a := 1;' +

                'SELECT TRUE OF' +
                '  CASE (a > 0):' +
                '     DECLARE (x, ra) REAL;' +
                '     DECLARE sqrt INTEGER;' +
                '     ;' +

                '   OTHERWISE:' +
                '      DECLARE (x, ra) BOOLEAN;' +
                '      ;' +
                'END SELECT;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Selection with escape and two cases'() {

        given: 'Parser a selection statment'
        Parser parser = getParserFor(
                'DECLARE a INTEGER;' +
                'SET a := 1;' +

                'SELECT TRUE OF' +
                '   CASE (a > 0):' +
                '      DECLARE sqrt INTEGER;' +
                '      ;' +

                '   CASE (a < 0):' +
                '      DECLARE (x, ra) REAL;' +
                '      ;' +

                '   OTHERWISE:' +
                '      DECLARE (x, ra) BOOLEAN;' +
                '      ;' +
                'END SELECT;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

}