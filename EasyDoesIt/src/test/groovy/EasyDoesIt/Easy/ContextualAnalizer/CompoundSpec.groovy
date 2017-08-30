package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class CompoundSpec extends ASTSpec {

    def 'Compound definition'() {

        given: 'Simple compound clause'
        Parser parser = getParserFor(
                'BEGIN' +
                '   DECLARE var INTEGER;' +
                '   SET var := 0;' +
                'END;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST structure will be traced without error'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Compound clause with name'() {

        given: 'Parser and named compound statement'
        Parser parser = getParserFor(
                'BEGIN' +
                '   DECLARE var INTEGER;' +
                '   SET var := 0;' +
                'END setting;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST structure will be traced without error'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Combine compound clauses'() {

        given: 'Parser and named compound statement'
        Parser parser = getParserFor(
                'BEGIN DECLARE var INTEGER; SET var := 0; END;' + '\n' +
                'BEGIN DECLARE var INTEGER; SET var := 0; END setter;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST structure will be traced without error'
        getChecker().check(theAST)
        assert theAST
    }
}