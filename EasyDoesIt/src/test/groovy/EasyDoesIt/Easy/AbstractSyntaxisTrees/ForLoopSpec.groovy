package EasyDoesIt.Easy.AbstractSyntaxisTrees

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class ForLoopSpec extends ASTSpec {

    def 'For loop statement with limit'() {

        given: 'Parser a simple loop definition'
        Parser parser = getParserFor('FOR sqrt := 42 TO 10 DO ; END FOR;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'For loop statement with step'() {

        given: 'Parser a simple loop definition'
        Parser parser = getParserFor('FOR sqrt := 42 BY 1 DO ; END FOR;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'For loop statement with step and while'() {

        given: 'Parser a simple loop definition'
        Parser parser = getParserFor('FOR sqrt := 42 BY 1 WHILE 44 DO ; END FOR;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }


}