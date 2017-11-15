package EasyDoesIt.Easy.AbstractSyntaxisTrees

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class SelectionSpec extends ASTSpec {

    def 'Selection without escape'() {

        given: 'Parser a selection statment'
        Parser parser = getParserFor(
                'SELECT select OF' +
                '   CASE (case1):' +
                '      DECLARE (x, ra) REAL;' +
                '      DECLARE sqrt INTEGER;' +
                '   ;' +
                'END SELECT;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Selection with escape'() {

        given: 'Parser a selection statment'
        Parser parser = getParserFor(
                'SELECT select OF' +
                '   CASE (case1):' +
                '      DECLARE (x, ra) REAL;' +
                '      DECLARE sqrt INTEGER;' +
                '   ;' +

                'OTHERWISE:' +
                '   DECLARE (x, ra) REAL;' +
                '   ;' +
                'END SELECT;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

}