package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class RepeatRepentSpec extends ASTSpec {

    def 'Repeat definition'() {

        given: 'Parser and repeat statement'
        Parser parser = getParserFor('REPEAT repeat;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Repent definition'() {

        given: 'Parser and repent statement'
        Parser parser = getParserFor('REPENT repent;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

}