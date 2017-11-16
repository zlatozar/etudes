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
                '   SET var := 0;' +
                'ELSE' +
                '   SET var := 1;' +
                'FI;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Unary and binary operations'() {

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

    def 'Array index'() {

        given: 'Parser and call with parameters'
        Parser parser = getParserFor(
                'DECLARE sieve ARRAY[1:100] OF BOOLEAN;' +
                'DECLARE (i, limit, count) INTEGER;' +

                'FOR i := 1 TO 100 DO SET sieve[i] := TRUE; END FOR;' +

                'SET limit := 20;' +

                'FOR i := 2 TO limit DO' +
                '   IF sieve[i] THEN' +
                '      DECLARE j INTEGER;' +
                '      FOR j := 2*i BY i TO 10 DO SET sieve[j] := FALSE; END FOR;' +
                '   FI;' +
                'END FOR;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST

    }
}