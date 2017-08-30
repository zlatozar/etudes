package EasyDoesIt.Easy.AbstractSyntaxisTrees

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class ConditionalsSpec extends ASTSpec {

    def 'Conditional definition'() {

        given: 'Simple IF clause'
        Parser parser = getParserFor('IF 5 THEN ; FI;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Conditional definition with false branch'() {

        given: 'Parser and call with parameters'
        Parser parser = getParserFor('IF 5 THEN SET var := 0; ELSE ; FI;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

}