package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class ForLoopSpec extends ASTSpec {

    def 'For loop statement with limit'() {

        given: 'Parser a simple loop definition'
        Parser parser = getParserFor(
                'DECLARE sqrt INTEGER;' +
                'FOR sqrt := 42 TO 100 DO' +
                '   ;' +
                'END FOR;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'For loop statement with while'() {

        given: 'Parser a simple loop definition'
        Parser parser = getParserFor(
                'DECLARE sqrt INTEGER;' +
                        'FOR sqrt := 42 TO 100 DO' +
                        '   ;' +
                        'END FOR;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'For loop statement with step'() {

        given: 'Parser a simple loop definition'
        Parser parser = getParserFor('FOR sqrt := 42 BY 1 DO ; END FOR;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'For loop statement with step and while'() {

        given: 'Parser a simple loop definition'
        Parser parser = getParserFor('FOR sqrt := 42 BY 1 WHILE 44 DO ; END FOR;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }
}