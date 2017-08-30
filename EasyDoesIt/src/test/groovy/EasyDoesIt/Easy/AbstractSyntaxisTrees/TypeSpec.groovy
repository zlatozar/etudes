package EasyDoesIt.Easy.AbstractSyntaxisTrees

import EasyDoesIt.Easy.ASTSpec
import EasyDoesIt.Easy.AbstractSyntaxTrees.AST
import EasyDoesIt.Easy.SyntacticAnalizer.Parser
import spock.lang.Title

@Title('AST')
class TypeSpec extends ASTSpec {

    private final String TYPE_IDENTIFIER = 'TYPE simple IS INTEGER;'
    private final String TYPE_IDENTIFIER_SEQ = 'TYPE firstName IS INTEGER;\nTYPE secondName IS BOOLEAN;\n' +
            'TYPE thirdName IS STRING;'

    private final String ARRAYED_TYPE = 'TYPE firstName IS ARRAY [5] OF INTEGER;'
    private final String ARRAYED_TYPE_SECTION = 'TYPE firstName IS ARRAY [5:15] OF STRING;'

    private final String ARRAYED_TYPE_SEQ =
            'TYPE firstName IS ARRAY [5:15] OF STRING;\n' +
            'TYPE secondName IS ARRAY [5] OF INTEGER;'

    private final String STRUCTURE_TYPE =
            'TYPE fistName IS\n' +
                    'STRUCTURE\n'+
                    'FIELD firstField IS STRING\n' +
                    'END STRUCTURE;'

    private final String STRUCTURE_TYPE_MANY_FIELDS =
            'TYPE fistName IS\n' +
                    'STRUCTURE\n'+
                    'FIELD firstField IS STRING,\n' +
                    'FIELD secondField IS INTEGER,\n' +
                    'FIELD thirdField IS REAL\n' +
                    'END STRUCTURE;'

    def 'Type definition'() {

        given: 'Parser a simple type identifier'
        Parser parser = getParserFor(TYPE_IDENTIFIER)

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Sequence of type definitions'() {

        given: 'Parser and simple program'
        Parser parser = getParserFor(TYPE_IDENTIFIER_SEQ)

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Parse a simple arrayed type definition'() {

        given: 'Parser and arrayed type'
        Parser parser = getParserFor(ARRAYED_TYPE)

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Parse a arrayed type with section definition'() {

        given: 'Parser and arrayed type'
        Parser parser = getParserFor(ARRAYED_TYPE_SECTION)

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Parse a arrayed type sequence definition'() {

        given: 'Parser and arrayed type sequence'
        Parser parser = getParserFor(ARRAYED_TYPE_SEQ)

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Parse a structured type sequence definition'() {

        given: 'Parser and structured type sequence'
        Parser parser = getParserFor(STRUCTURE_TYPE)

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }

    def 'Parse a structured type sequence definition with many fields'() {

        given: 'Parser and structured type sequence'
        Parser parser = getParserFor(STRUCTURE_TYPE_MANY_FIELDS)

        when: 'Parser finish'

        then: 'AST should be constructed'
        AST theAST = parser.parseProgram();
        assert theAST
    }
}