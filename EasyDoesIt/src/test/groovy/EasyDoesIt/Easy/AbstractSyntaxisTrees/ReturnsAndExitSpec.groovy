package EasyDoesIt.Easy.AbstractSyntaxisTrees

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class ReturnsAndExitSpec extends ASTSpec {

    def 'Return statement without expression'() {

        given: 'Just return'
        Parser parser = getParserFor('RETURN;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Return statement with expression'() {

        given: 'Return an expression'
        Parser parser = getParserFor('RETURN 5;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Exit statement'() {

        given: 'Just exit'
        Parser parser = getParserFor('EXIT;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }
}