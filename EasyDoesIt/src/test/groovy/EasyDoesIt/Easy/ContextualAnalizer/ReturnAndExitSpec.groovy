package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class ReturnAndExitSpec extends ASTSpec {

    def 'Return statement without expression'() {

        given: 'Just return'
        Parser parser = getParserFor('RETURN;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Return statement with expression'() {

        given: 'Return an expression'
        Parser parser = getParserFor(
                'DECLARE sql STRING;' +
                'RETURN sql;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Exit statement'() {

        given: 'Just exit'
        Parser parser = getParserFor('EXIT;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

}