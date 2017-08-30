package EasyDoesIt.Easy.AbstractSyntaxisTrees

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class InternalProceduresSpec extends ASTSpec {

    def 'Procedure definition'() {

        given: 'Parser and simple procedure (param pass by name)'
        Parser parser = getParserFor(
                ' PROCEDURE abs(x REAL NAME):\n' +
                        '    ;\n' +
                        ' END PROCEDURE abs;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Procedure definition with many parameters'() {

        given: 'Parser and simple procedure (param pass by name)'
        Parser parser = getParserFor(
                ' PROCEDURE abs(x REAL, y INTEGER, z STRING):\n' +
                        '    ;\n' +
                        ' END PROCEDURE abs;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Function definition'() {

        given: 'Parser and simple function (param pass by name)'
        Parser parser = getParserFor(
                ' FUNCTION abs(x REAL NAME) REAL:\n' +
                '    ;\n' +
                ' END FUNCTION abs;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Function definition with many parameters'() {

        given: 'Parser and simple function'
        Parser parser = getParserFor(
                ' FUNCTION abs(x REAL, y INTEGER, z STRING) REAL:\n' +
                        '    ;\n' +
                        ' END FUNCTION abs;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Function and procedure'() {

        given: 'Parser both'
        Parser parser = getParserFor(
                ' FUNCTION abs(x REAL NAME) REAL:\n' +
                        '    ;\n' +
                        ' END FUNCTION abs;' +
                ' PROCEDURE abs(x REAL, y INTEGER, z STRING):\n' +
                        '    ;\n' +
                        ' END PROCEDURE abs;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

}