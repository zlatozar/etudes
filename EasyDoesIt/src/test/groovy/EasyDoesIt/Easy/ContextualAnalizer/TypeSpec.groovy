package EasyDoesIt.Easy.ContextualAnalizer

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.Program
import EasyDoesIt.Easy.SyntacticAnalizer.Parser

class TypeSpec extends ASTSpec {

    def 'Indentifier type'() {
        given: 'Easy language source file reader, scanner, parser and checker'
        Parser parser = getParserFor('TYPE simple IS INTEGER;')

        when: 'Second pass starts'
        Program theAST = parser.parseProgram()

        then: 'AST structure will be traced without error'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Structure type'() {

        given: 'Easy language source file reader, scanner, parser and checker'
        Parser parser = getParserFor(
                'TYPE typeName IS\n' +
                '   STRUCTURE\n'+
                '      FIELD firstField IS STRING,\n' +
                '      FIELD secondField IS INTEGER,\n' +
                '      FIELD thirdField IS REAL\n' +
                '   END STRUCTURE;')

        when: 'Second pass starts'
        Program theAST = parser.parseProgram()

        then: 'AST structure will be traced without error'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Array type' () {
        given: 'Easy language source file reader, scanner, parser and checker'
        Parser parser = getParserFor(
                'DECLARE topnum INTEGER;' +
                'TYPE firstName IS ARRAY [topnum] OF INTEGER;')

        when: 'Second pass starts'
        Program theAST = parser.parseProgram()

        then: 'AST structure will be traced without error'
        getChecker().check(theAST)
        assert theAST
    }

    def 'Array type with bounds' () {
        given: 'Easy language source file reader, scanner, parser and checker'
        Parser parser = getParserFor(
                'DECLARE topnum INTEGER;' +
                'TYPE firstName IS ARRAY [1:topnum] OF INTEGER;')

        when: 'Second pass starts'
        Program theAST = parser.parseProgram()

        then: 'AST structure will be traced without error'
        getChecker().check(theAST)
        assert theAST
    }
}