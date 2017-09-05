package EasyDoesIt.Easy.AbstractSyntaxisTrees

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class CallsSpec extends ASTSpec {

    def 'Call definition without parameters'() {

        given: 'Call procedure without parameters'
        Parser parser = getParserFor('CALL procedureName;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Call procedure with three parameters'() {

        given: 'Parser and call with parameters'
        Parser parser = getParserFor('CALL procedureName(1, 2, 3, 4);')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Call function with one parameter'() {

        given: 'Parser and call with parameters'
        Parser parser = getParserFor('SET limit := integersqrt(topnum) + 1;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

}