package EasyDoesIt.Easy.AbstractSyntaxisTrees

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class SelectionSpec extends ASTSpec {

    def 'Selection without escape'() {

        given: 'Parser a selection statment'
        Parser parser = getParserFor(
                'SELECT select OF\n' +
                '  CASE (case1):\n' +
                '       DECLARE (x, ra) REAL;\n' +
                '       DECLARE sqrt INTEGER;\n' +
                '  ;' +
                'END SELECT;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Selection with escape'() {

        given: 'Parser a selection statment'
        Parser parser = getParserFor(
                'SELECT select OF\n' +
                '  CASE (case1):\n' +
                '       DECLARE (x, ra) REAL;\n' +
                '       DECLARE sqrt INTEGER;\n' +
                '  ;' +
                'OTHERWISE:' +
                '  DECLARE (x, ra) REAL;' +
                '  ;' +
                'END SELECT;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

}