package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class DeclarationSpec extends ASTSpec {

    def 'Single variable declaration'() {

        given: 'Parser and declaration code'
        Parser parser = getParserFor('DECLARE x REAL;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Book example'() {

        given: 'Parser and declaration code'
        Parser parser = getParserFor('DECLARE (x, ra) REAL;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

}