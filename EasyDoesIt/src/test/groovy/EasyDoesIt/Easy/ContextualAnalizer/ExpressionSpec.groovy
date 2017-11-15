package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class ExpressionSpec extends ASTSpec {

    def 'Binary expression'() {

        given: 'String'
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
}