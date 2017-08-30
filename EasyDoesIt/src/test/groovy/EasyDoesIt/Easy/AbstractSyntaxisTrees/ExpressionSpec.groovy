package EasyDoesIt.Easy.AbstractSyntaxisTrees

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class ExpressionSpec extends ASTSpec {

    def 'Divide'() {

        given: 'String'
        Parser parser = getParserFor('SET a := b/c;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Multiply'() {

        given: 'String'
        Parser parser = getParserFor('SET a := b*c;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }


    def 'Sting literal'() {

        given: 'String'
        Parser parser = getParserFor('OUTPUT "a < 0 in FUNCTION integersqrt."')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Output with operator'() {

        given: 'String'
        Parser parser = getParserFor('OUTPUT "Prime[" || count || "] = " || i;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Operation'() {

        given: 'Less expression'
        Parser parser = getParserFor(
                'SELECT select OF\n' +
                        '  CASE (select > 0):\n' +
                        '       DECLARE (x, ra) REAL;\n' +
                        '       DECLARE sqrt INTEGER;\n' +
                        '  ;' +
                        'END SELECT;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Negative'() {

        given: 'if statement'
        Parser parser = getParserFor('IF x < 0 THEN RETURN -x; ELSE RETURN x; FI;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Array expression'() {

        given: 'For statement'
        Parser parser = getParserFor(
                'FOR i := 1 TO topnum DO' +
                '   SET sieve[i] := TRUE;' +
                '   SET count := count + 1;' +
                'END FOR;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Call function'() {

        given: 'For statement'
        Parser parser = getParserFor(
                'SET limit := integersqrt(topnum) + l; /* Avoid repeating square root */')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Complex expressions'() {

        given: 'For statement'
        Parser parser = getParserFor(
                'SET limit := ra/2;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Complex expressions in a loop'() {

        given: 'For statement'
        Parser parser = getParserFor(
                'FOR x := a/2.0 BY (b/x - y)/4.0 WHILE abs(c - z*z) > epsilon DO\n' +
                '   ;' +
                'END FOR;')

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }
}