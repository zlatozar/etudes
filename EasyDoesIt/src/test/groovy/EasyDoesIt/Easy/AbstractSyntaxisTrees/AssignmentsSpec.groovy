package EasyDoesIt.Easy.AbstractSyntaxisTrees

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class AssignmentsSpec extends ASTSpec {

    def 'Assignments definition'() {

        given: 'Parser and assignment'
        Parser parser = getParserFor('SET variable1 := 0;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Multiple assignments definition'() {

        given: 'Parser and assignment'
        Parser parser = getParserFor('SET variable1, variable2, variable3 := 0;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }
}