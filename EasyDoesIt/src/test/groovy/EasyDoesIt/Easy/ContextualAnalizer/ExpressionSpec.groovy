package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class ExpressionSpec extends ASTSpec {

    def 'String concatenation'() {

        given: 'Output with concatenation in chain'
        Parser parser = getParserFor(
                'DECLARE count INTEGER;' +
                'OUTPUT "Prime[" || count || "] = ";')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }


    def 'Binary expression'() {

        given: 'Divide'
        Parser parser = getParserFor(
                'DECLARE (a, b, c) INTEGER;' +
                'SET a := b/c;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Unary expression'() {

        given: 'Function containing unary expression'
        Parser parser = getParserFor(
                'FUNCTION abs(x REAL) REAL:' +
                '   IF x < 0 THEN RETURN -x; ELSE RETURN x; FI;' +
                'END FUNCTION abs;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Unary of binary expression'() {

        given: 'Declarations'
        Parser parser = getParserFor(
                'DECLARE a INTEGER;' +
                'DECLARE ra REAL;' +

                'DECLARE epsilon REAL;' +
                'SET ra := FLOAT(a);' +
                'SET epsilon := 0.0000001-ra;');

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Function as LHS'() {

        given: 'Declarations'
        Parser parser = getParserFor(
                'FUNCTION abs(x REAL) REAL:' +
                '   IF x < 0 THEN RETURN -x; ELSE RETURN x; FI;' +
                'END FUNCTION abs;' +

                'FUNCTION integersqrt(a INTEGER) INTEGER:' +

                'SELECT TRUE OF' +
                '   CASE (a < 0): OUTPUT "a < 0 in FUNCTION integersqrt."; EXIT;' +
                '   CASE (a = 0): RETURN 0;' +

                '   CASE (a > 0):' +
                '      DECLARE (x, ra) REAL;' +
                '      DECLARE epsilon REAL;' +
                '      DECLARE sqrt INTEGER;' +

                '      SET ra := FLOAT(a);' +
                '      SET epsilon := 0.0000001*ra;' +

                '      FOR x := ra/2.0 BY (ra/x-x)/2.0 WHILE abs(ra-x*x) > epsilon' +
                '      DO ; END FOR;' +

                '      FOR sqrt := FIX(x)-1 BY 1 WHILE (sqrt+1)*(sqrt+1) <= a' +
                '      DO ; END FOR;' +

                '      RETURN sqrt;' +

                '   END SELECT;' +
                'END FUNCTION integersqrt;')

        when: 'Parser finish'
        AST theAST = parser.parseProgram();

        then: 'AST should be constructed'
        getChecker().check(theAST)
        assert theAST
    }

}