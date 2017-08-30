package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class InputOutputSpec extends ASTSpec {

    def 'Input definition'() {

        given: 'Parser and input statement'
        Parser parser = getParserFor(
                'DECLARE topnum INTEGER;' +
                'INPUT topnum;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Output definition'() {

        given: 'Parser and output statement'
        Parser parser = getParserFor('OUTPUT "Must be string";')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

}