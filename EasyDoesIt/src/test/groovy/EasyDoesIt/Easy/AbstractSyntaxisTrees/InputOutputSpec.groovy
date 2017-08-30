package EasyDoesIt.Easy.AbstractSyntaxisTrees

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class InputOutputSpec extends ASTSpec {

    def 'Input definition'() {

        given: 'Parser and input statement'
        Parser parser = getParserFor('INPUT a, b, c;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Output definition'() {

        given: 'Parser and output statement'
        Parser parser = getParserFor('OUTPUT 1, 2, 3;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

}