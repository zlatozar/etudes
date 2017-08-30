package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class AssignmentsSpec extends ASTSpec {

    def 'Assignments definition'() {

        given: 'Parser and assignment'
        Parser parser = getParserFor(
                'DECLARE variable INTEGER;' +
                'SET variable := 0;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'Checker should finish without errors'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Multiple assignments definition'() {

        given: 'Parser and assignment'
        Parser parser = getParserFor(
                'DECLARE (variable1, variable2, variable3) INTEGER;' +
                'SET variable1, variable2, variable3 := 0;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();


        then: 'Checker should finish without errors'
        getChecker().check(theAST)
        assert theAST
    }
}