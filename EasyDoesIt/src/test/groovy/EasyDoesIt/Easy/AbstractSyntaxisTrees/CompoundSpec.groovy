package EasyDoesIt.Easy.AbstractSyntaxisTrees

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class CompoundSpec extends ASTSpec {

    def 'Compound definition'() {

        given: 'Simple compound clause'
        Parser parser = getParserFor('BEGIN SET var := 0; END;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Compound clause'() {

        given: 'Parser and named compound statement'
        Parser parser = getParserFor('BEGIN SET var := 0; END setter;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Combine compound clauses'() {

        given: 'Parser and named compound statement'
        Parser parser = getParserFor(
                'BEGIN SET var := 0; END;' + '\n' +
                'BEGIN SET var := 0; END setter;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }
}