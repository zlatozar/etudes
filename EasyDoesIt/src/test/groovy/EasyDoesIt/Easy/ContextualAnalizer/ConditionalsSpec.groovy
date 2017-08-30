package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class ConditionalsSpec extends ASTSpec {

    def 'Conditional definition'() {

        given: 'Simple IF clause'
        Parser parser = getParserFor(
                'DECLARE var BOOLEAN;' +
                'SET var := TRUE;' +
                'IF var THEN ; FI;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Conditional definition with false branch'() {

        given: 'Parser and call with parameters'
        Parser parser = getParserFor(
                'DECLARE var INTEGER;' +
                'DECLARE con BOOLEAN;' +
                'SET con := TRUE;' +

                'IF con THEN' +
                        ' SET var := 0;' +
                'ELSE' +
                        ' SET var := 1;' +
                'FI;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Book example'() {

        given: 'Parser and call with parameters'
        Parser parser = getParserFor(
                'DECLARE x INTEGER;' +
                'IF x < 0 THEN RETURN -x; ELSE RETURN x; FI;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

}