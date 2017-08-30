package EasyDoesIt.Easy.SyntacticAnalizer;

import EasyDoesIt.Easy.AbstractSyntaxTrees.*;
import EasyDoesIt.Easy.ErrorReporter;

public class Parser {

    private final Scanner lexicalAnalyser;
    private final ErrorReporter errorReporter;

    private Token currentToken;
    private SourcePosition previousTokenPosition;

    public Parser(Scanner lexer, ErrorReporter reporter) {
        this.lexicalAnalyser = lexer;
        this.errorReporter = reporter;
        this.previousTokenPosition = new SourcePosition();
    }

    void accept(int tokenExpected) throws SyntaxError {

        if (currentToken.kind == tokenExpected) {
            previousTokenPosition = currentToken.position;
            currentToken = lexicalAnalyser.scan();

        } else {
            syntacticError("\"%\" expected here", Token.spell(tokenExpected));
        }
    }

    void acceptIt() {
        previousTokenPosition = currentToken.position;
        currentToken = lexicalAnalyser.scan();
    }

    void start(SourcePosition position) {
        position.start = currentToken.position.start;
    }

    void finish(SourcePosition position) {
        position.finish = previousTokenPosition.finish;
    }

    void syntacticError(String messageTemplate, String tokenQuoted) throws SyntaxError {
        SourcePosition pos = currentToken.position;
        errorReporter.reportError(messageTemplate, tokenQuoted, pos);

        throw new SyntaxError();
    }

//_____________________________________________________________________________
//                                                                 Entry point

    public Program parseProgram() {

        Program programAST;

        // initialize, because everything starts from here
        previousTokenPosition.start = 0;
        previousTokenPosition.finish = 0;

        currentToken = lexicalAnalyser.scan();

        try {

            ProgramBody prgAST = parseProgramBody();
            programAST = new Program(previousTokenPosition, prgAST);

            if (currentToken.kind != Token.EOT) {
                syntacticError("\"%\" not expected after end of program", currentToken.spelling);
            }

        } catch (SyntaxError s) {
            return null;
        }

        return programAST;
    }

//_____________________________________________________________________________
//         PROGRAM contains definitions and executable statements
//_____________________________________________________________________________

    ProgramBody parseProgramBody() throws SyntaxError {

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.PROGRAM);
        Identifier prgName = parseIdentifier();
        accept(Token.COLON);

        Segment segment = parseSegment();

        accept(Token.END);
        accept(Token.PROGRAM);

        Identifier prgNameEnd = parseIdentifier();
        accept(Token.SEMICOLON);
        finish(srcPos);

        return new ProgramBody(srcPos, prgName, segment, prgNameEnd);
    }

    Segment parseSegment() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        Definition prgDefinition = parseDefinitionsSeq();
        Statement prgStatement = parseExecutableStatementSeq();
        finish(srcPos);

        return new Segment(srcPos, prgDefinition, prgStatement);
    }

//_____________________________________________________________________________
//                   DEFINITIONS has segments interface
//_____________________________________________________________________________


    Definition parseDefinitionsSeq() throws SyntaxError {

        Definition definition;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        if (isSegment()) {
            definition = parseSingleDefinition();

        } else {
            definition = new EmptyDefinition(srcPos);
        }

        return definition;
    }

    Definition parseSingleDefinition() throws SyntaxError {

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        Definition definition = parseDefinition();
        finish(srcPos);

        while (isSegment()) {
            Definition definition2 = parseDefinition();
            finish(srcPos);

            definition = new DefinitionSeq(srcPos, definition, definition2);
        }

        return definition;
    }

    Definition parseDefinition() throws SyntaxError {

        Definition definition = null;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        switch (currentToken.kind) {

            case Token.TYPE:
                definition = parseTypeDefinition();

                finish(srcPos);
                break;

            case Token.DECLARE:
                definition = parseDeclaration();

                finish(srcPos);
                break;

            case Token.FUNCTION:
            case Token.PROCEDURE:
                definition = parseInternalProcedure();

                finish(srcPos);
                break;

            default:
                syntacticError("\"%\" cannot start a segment", currentToken.spelling);
        }

        return definition;
    }

    private boolean isSegment() {
        int token = currentToken.kind;
        return token == Token.TYPE || token == Token.DECLARE || token == Token.FUNCTION || token == Token.PROCEDURE;
    }

//_____________________________________________________________________________
//                                                             Type definition

    Definition parseTypeDefinition() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.TYPE);

        Identifier typeName = parseIdentifier();
        accept(Token.IS);

        TypeDenoter typeDenoter = parseTypeDenoter();

        accept(Token.SEMICOLON);
        finish(srcPos);

        return new TypeDefinition(srcPos, typeName, typeDenoter);
    }

    TypeDenoter parseTypeDenoter() throws SyntaxError {

        TypeDenoter typeDenoter = null;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        switch (currentToken.kind) {

            case Token.ARRAY:
                typeDenoter = parseArrayTypeDenoter();
                finish(srcPos);
                break;

            case Token.STRUCTURE:
                typeDenoter = parseStructureTypeDenoter();
                finish(srcPos);
                break;

            case Token.IDENTIFIER:
                typeDenoter = parseIdentifierTypeDenoter();
                finish(srcPos);
                break;

            default:
                syntacticError("\"%\" cannot start a type definition", currentToken.spelling);

        }

        return typeDenoter;
    }

    TypeDenoter parseArrayTypeDenoter() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.ARRAY);
        ArrayBounds arrayBounds = parseArrayBounds();

        accept(Token.OF);

        TypeDenoter arrayType = parseTypeDenoter();

        finish(srcPos);
        return new ArrayType(srcPos, arrayBounds, arrayType);
    }

    ArrayBounds parseArrayBounds() throws SyntaxError {

        ArrayBounds arrayBounds;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.LBRACKET);

        Expression index = parseExpression();

        if (currentToken.kind == Token.COLON) {
            acceptIt();

            Expression to = parseExpression();

            finish(srcPos);
            arrayBounds = new SegmentedArrayBounds(srcPos, index, to);

        } else {
            finish(srcPos);
            arrayBounds = new SingleArrayBounds(srcPos, index);
        }

        accept(Token.RBRACKET);

        return arrayBounds;
    }

    TypeDenoter parseStructureTypeDenoter() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.STRUCTURE);

        Field fieldDenoterList = parseFieldList();

        accept(Token.END);
        accept(Token.STRUCTURE);

        finish(srcPos);

        return new StructureType(srcPos, fieldDenoterList);
    }

    Field parseFieldList() throws SyntaxError {

        Field fieldDenoter;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        fieldDenoter = parseField();

        while (currentToken.kind == Token.COMMA) {
            acceptIt();

            FieldDenoter fieldDenoter2 = parseField();
            finish(srcPos);

            fieldDenoter = new FieldList(srcPos, fieldDenoter, fieldDenoter2);
        }

        return fieldDenoter;
    }

    FieldDenoter parseField() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.FIELD);
        Identifier identifier = parseIdentifier();

        accept(Token.IS);
        TypeDenoter typeDenoter = parseTypeDenoter();

        return new FieldDenoter(srcPos, identifier, typeDenoter);
    }

    TypeDenoter parseIdentifierTypeDenoter() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        Identifier identifier = parseIdentifier();
        finish(srcPos);

        return new IdentifierType(srcPos, identifier);
    }

//_____________________________________________________________________________
//                                                                Declarations

    Definition parseDeclaration() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.DECLARE);

        Definition declaredNames = parseDeclaredNames();
        TypeDenoter typeDenoter = parseTypeDenoter();

        accept(Token.SEMICOLON);
        finish(srcPos);

        return new Declaration(srcPos, declaredNames, typeDenoter);
    }

    Definition parseDeclaredNames() throws SyntaxError {

        Definition declaredNames;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        if (currentToken.kind == Token.LPAREN) {
            declaredNames = parseMultipleDeclaredNames();
            finish(srcPos);

        } else {
            Identifier identifier = parseIdentifier();
            finish(srcPos);

            declaredNames = new SingleDeclaredName(srcPos, identifier);
        }

        return declaredNames;
    }

    Definition parseMultipleDeclaredNames() throws SyntaxError {

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.LPAREN);

        Definition multipleDeclaredNames = parseDeclaredNamesSeq();

        accept(Token.RPAREN);
        finish(srcPos);

        return multipleDeclaredNames;
    }

    Definition parseDeclaredNamesSeq() throws SyntaxError {

        Definition declaredNames;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        declaredNames = parseDeclaredNames();

        while (currentToken.kind == Token.COMMA) {
            acceptIt();

            Definition declaredNames2 = parseDeclaredNames();
            finish(srcPos);

            declaredNames = new MultipleDeclaredNames(srcPos, declaredNames, declaredNames2);
        }

        return declaredNames;
    }

//_____________________________________________________________________________
//                                                         Internal procedures

    Definition parseInternalProcedure() throws SyntaxError {

        Definition blockBlockCode;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        blockBlockCode = parseBlockCode();
        finish(srcPos);

        return new InternalProcedure(srcPos, blockBlockCode);
    }

    Definition parseBlockCode() throws SyntaxError {

        Definition blockBlockCode = null;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        switch (currentToken.kind) {

            case Token.PROCEDURE:
                blockBlockCode = parseProcedureDefinition();

                finish(srcPos);
                break;

            case Token.FUNCTION:
                blockBlockCode = parseFunctionDefinition();

                finish(srcPos);
                break;

            default:
                syntacticError("\"%\" cannot start a subprogram definition", currentToken.spelling);
        }

        return blockBlockCode;
    }

    Definition parseProcedureDefinition() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        ProcedureHead procHead = parseProcedureHead();
        accept(Token.COLON);

        Segment segment = parseSegment();

        ProcedureEnd procEnd = parseProcedureEnd();
        finish(srcPos);

        return new ProcedureDefinition(srcPos, procHead, segment, procEnd);
    }

    Definition parseFunctionDefinition() throws SyntaxError {

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        FunctionHead funcHead = parseFunctionHead();
        accept(Token.COLON);

        Segment segment = parseSegment();

        FunctionEnd funcEnd = parseFunctionEnd();
        finish(srcPos);

        return new FunctionDefinition(srcPos, funcHead, segment, funcEnd);
    }

    FunctionHead parseFunctionHead() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.FUNCTION);
        BlockCodeName blockCodeName = parseBlockCodeName();
        TypeDenoter typeDenoter = parseTypeDenoter();

        finish(srcPos);

        return new FunctionHead(srcPos, blockCodeName, typeDenoter);
    }

    FunctionEnd parseFunctionEnd() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.END);
        accept(Token.FUNCTION);

        Identifier identifier = parseIdentifier();
        accept(Token.SEMICOLON);

        finish(srcPos);

        return new FunctionEnd(srcPos, identifier);
    }

    ProcedureHead parseProcedureHead() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.PROCEDURE);
        BlockCodeName prgName = parseBlockCodeName();

        finish(srcPos);

        return new ProcedureHead(srcPos, prgName);
    }

    BlockCodeName parseBlockCodeName() throws SyntaxError {

        BlockCodeName blockCodeName;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        Identifier identifier = parseIdentifier();

        if (currentToken.kind == Token.LPAREN) {

            Parameter params = parseParameters();
            finish(srcPos);

            blockCodeName = new ProcedureNameWithParams(srcPos, identifier, params);

        } else {
            blockCodeName = new ProcedureName(srcPos, identifier);
            finish(srcPos);
        }

        return blockCodeName;
    }

    Parameter parseParameters() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.LPAREN);

        Parameter param = parseSingleParameter();

        while (currentToken.kind == Token.COMMA) {
            acceptIt();

            Parameter param2 = parseSingleParameter();
            finish(srcPos);

            param = new ParameterList(srcPos, param, param2);
        }

        accept(Token.RPAREN);
        finish(srcPos);

        return param;
    }

    Parameter parseSingleParameter() throws SyntaxError {

        Parameter parameter;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        Identifier identifier = parseIdentifier();
        TypeDenoter typeDenoter = parseTypeDenoter();

        if (currentToken.kind == Token.NAME) {
            acceptIt();
            finish(srcPos);

            parameter = new ParameterByName(srcPos, identifier, typeDenoter);

        } else {
            finish(srcPos);

            parameter = new ParameterByValue(srcPos, identifier, typeDenoter);
        }

        return parameter;
    }

    ProcedureEnd parseProcedureEnd() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.END);
        accept(Token.PROCEDURE);

        Identifier prgName = parseIdentifier();
        accept(Token.SEMICOLON);

        finish(srcPos);

        return new ProcedureEnd(srcPos, prgName);
    }


//_____________________________________________________________________________
//             STATEMENTS has statement(extends segment) interface
//_____________________________________________________________________________

    Statement parseExecutableStatementSeq() throws SyntaxError {

        Statement statement = null;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        if (isStatement()) {

            statement = parseSingleExecutableStatement();
            finish(srcPos);

        } else {
            syntacticError("At least one statement should exist in program", "");
        }

        return statement;
    }

    Statement parseSingleExecutableStatement() throws SyntaxError {

        Statement statement;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        statement = parseStatement();
        finish(srcPos);

        while (isStatement()) {

            Statement statementNext = parseStatement();
            finish(srcPos);

            statement = new StatementSeq(srcPos, statement, statementNext);
        }

        return statement;
    }

    Statement parseStatement() throws SyntaxError {

        Statement statement;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        switch (currentToken.kind) {

            case Token.SET:
                statement = parseAssignments();
                finish(srcPos);
                break;

            case Token.CALL:
                statement = parseInternalProcedureCall();
                finish(srcPos);
                break;

            case Token.RETURN:
                statement = parseReturns();
                finish(srcPos);
                break;

            case Token.EXIT:
                statement = parseExit();
                finish(srcPos);
                break;

            case Token.IF:
                statement = parseConditionalStmt();
                finish(srcPos);
                break;

            case Token.BEGIN:
                statement = parseCompoundStmt();
                finish(srcPos);
                break;

            case Token.FOR:
                statement = parseForLoopStmt();
                finish(srcPos);
                break;

            case Token.SELECT:
                statement = parseSelectionStmt();
                finish(srcPos);
                break;

            case Token.REPEAT:
                statement = parseRepeatStmt();
                finish(srcPos);
                break;

            case Token.REPENT:
                statement = parseRepentStmt();
                finish(srcPos);
                break;

            case Token.INPUT:
                statement = parseInputStmt();
                finish(srcPos);
                break;

            case Token.OUTPUT:
                statement = parseOutputStmt();
                finish(srcPos);
                break;

            case Token.SEMICOLON:
                acceptIt();
                statement = new NullStmt(srcPos);

                finish(srcPos);
                break;

            default:
                statement = new EmptyStatement(srcPos);
        }

        return statement;
    }

    private boolean isStatement() {
        int token = currentToken.kind;

        return token == Token.SET || token == Token.CALL || token == Token.RETURN || token == Token.EXIT ||
                token == Token.IF || token == Token.BEGIN || token == Token.FOR || token == Token.SELECT ||
                token == Token.REPEAT || token == Token.REPENT || token == Token.INPUT || token == Token.OUTPUT ||
                token == Token.SEMICOLON;
    }

    Statement parseAssignments() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.SET);

        Vname targetList = parseVariableList();
        accept(Token.BECOMES);
        Expression expression = parseExpression();
        accept(Token.SEMICOLON);

        finish(srcPos);

        return new AssignmentStmt(srcPos, targetList, expression);
    }

    Vname parseVariableList() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        Vname var = parseVariable();

        while (currentToken.kind == Token.COMMA) {
            acceptIt();

            Vname var2 = parseVariable();
            finish(srcPos);

            var = new VariableList(srcPos, var, var2);
        }

        return var;
    }

    Statement parseInternalProcedureCall() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.CALL);
        ProcedureRef procRef = parseProcedureRef();

        accept(Token.SEMICOLON);
        finish(srcPos);

        return new ProcedureCallStmt(srcPos, procRef);
    }

    ProcedureRef parseProcedureRef() throws SyntaxError {

        ProcedureRef procedureRef;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        Identifier identifier = parseIdentifier();
        finish(srcPos);

        if (currentToken.kind == Token.LPAREN) {
            Expression params = parseProcedureRefParams();
            finish(srcPos);

            procedureRef = new CallWithParams(srcPos, identifier, params);

        } else {
            procedureRef = new Call(srcPos, identifier);
        }

        return procedureRef;
    }

    Expression parseProcedureRefParams() throws SyntaxError {

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.LPAREN);

        Expression param = parseExpression();
        finish(srcPos);

        while (currentToken.kind == Token.COMMA) {
            acceptIt();

            Expression nextParam = parseExpression();
            finish(srcPos);

            param = new ExpressionList(srcPos, param, nextParam);
        }

        accept(Token.RPAREN);
        finish(srcPos);

        return param;
    }

    Statement parseReturns() throws SyntaxError {
        Statement returnStmt;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.RETURN);

        if (currentToken.kind == Token.SEMICOLON) {
            returnStmt = new Return(srcPos);

        } else {
            Expression expression = parseExpression();
            finish(srcPos);

            returnStmt = new ReturnWithExpression(srcPos, expression);
        }

        accept(Token.SEMICOLON);
        finish(srcPos);

        return returnStmt;
    }

    Statement parseExit() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.EXIT);
        accept(Token.SEMICOLON);

        finish(srcPos);

        return new ExitStmt(srcPos);
    }

    Statement parseConditionalStmt() throws SyntaxError {

        Statement conditionalStmt;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        ConditionalClause conditionalClause = parseConditionalClause();
        TrueBranch trueBranch = parseTrueBranch();
        finish(srcPos);

        if (currentToken.kind == Token.FI) {
            conditionalStmt = new IfStmt(srcPos, conditionalClause, trueBranch);

        } else {
            FalseBranch falseBranch = parseFalseBranch();
            finish(srcPos);

            conditionalStmt = new IfElseStmt(srcPos, conditionalClause, trueBranch, falseBranch);
        }

        accept(Token.FI);
        accept(Token.SEMICOLON);

        finish(srcPos);

        return conditionalStmt;
    }

    ConditionalClause parseConditionalClause() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.IF);

        Expression expression = parseExpression();
        finish(srcPos);

        return new ConditionalClause(srcPos, expression);
    }

    TrueBranch parseTrueBranch() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.THEN);
        Segment segment = parseSegment();

        finish(srcPos);

        return new TrueBranch(srcPos, segment);
    }

    FalseBranch parseFalseBranch() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.ELSE);
        Segment segment = parseSegment();

        finish(srcPos);

        return new FalseBranch(srcPos, segment);
    }

    Statement parseCompoundStmt() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.BEGIN);
        Segment segment = parseSegment();

        CompoundEnd compoundEnd = parseCompoundEnd();
        finish(srcPos);

        return new CompoundStmt(srcPos, segment, compoundEnd);
    }

    CompoundEnd parseCompoundEnd() throws SyntaxError {

        CompoundEnd compoundEnd;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.END);
        finish(srcPos);

        if (currentToken.kind == Token.SEMICOLON) {
            compoundEnd = new SimpleCompoundEnd(srcPos);

        } else {
            Identifier name = parseIdentifier();
            finish(srcPos);

            compoundEnd = new CompoundEndWithName(srcPos, name);
        }

        return compoundEnd;
    }

    Statement parseForLoopStmt() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        ForHead forHead = parseForHead();
        Segment segment = parseSegment();
        ForEnd forEnd = parseForEnd();

        finish(srcPos);

        return new ForLoopStmt(srcPos, forHead, segment, forEnd);
    }

    ForHead parseForHead() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.FOR);
        Vname var = parseVariable();

        accept(Token.BECOMES);
        LoopControl loopControl = parseControl();

        accept(Token.DO);
        finish(srcPos);

        return new ForHead(srcPos, var, loopControl);
    }

    LoopControl parseControl() throws SyntaxError {

        LoopControl loopControl;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        StepExpression stepExpression = parseStepExpression();

        if (currentToken.kind == Token.WHILE) {
            While whileExpression = parseWhileExpression();
            finish(srcPos);

            loopControl = new StepperWhile(srcPos, stepExpression, whileExpression);

        } else {
            loopControl = new Stepper(srcPos, stepExpression);
        }

        return loopControl;
    }

    While parseWhileExpression() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.WHILE);
        Expression expression = parseExpression();
        finish(srcPos);

        return new While(srcPos, expression);
    }

    StepExpression parseStepExpression() throws SyntaxError {

        StepExpression stepExpression = null;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        Expression expression = parseExpression();

        switch (currentToken.kind) {

            case Token.BY: {
                Step step = parseStep();
                finish(srcPos);

                if (currentToken.kind == Token.TO) {
                    Limit limit = parseLimit();
                    finish(srcPos);

                    stepExpression = new ExpressionStepLimit(srcPos, expression, step, limit);

                } else {
                    stepExpression = new ExpressionStep(srcPos, expression, step);
                }
            }
            break;

            case Token.TO:
                Limit limit = parseLimit();
                stepExpression = new ExpressionLimit(srcPos, expression, limit);
                break;

            default:
                syntacticError("\"%\" cannot start a loop control", currentToken.spelling);
        }

        return stepExpression;
    }

    Step parseStep() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.BY);
        Expression expression = parseExpression();
        finish(srcPos);

        return new Step(srcPos, expression);
    }

    Limit parseLimit() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.TO);
        Expression expression = parseExpression();
        finish(srcPos);

        return new Limit(srcPos, expression);
    }

    ForEnd parseForEnd() throws SyntaxError {

        ForEnd forEnd;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.END);
        accept(Token.FOR);

        if (currentToken.kind == Token.SEMICOLON) {
            forEnd = new SimpleForEnd(srcPos);
            finish(srcPos);

        } else {
            Identifier identifier = parseIdentifier();
            finish(srcPos);

            forEnd = new ForEndWithName(srcPos, identifier);
        }

        return forEnd;
    }

    Statement parseSelectionStmt() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        SelectionHead selectionHead = parseSelectionHead();
        SelectionBody selectionBody = parseSelectionBody();
        SelectionEnd selectionEnd = parseSelectionEnd();

        finish(srcPos);

        return new SelectionStmt(srcPos, selectionHead, selectionBody, selectionEnd);
    }

    SelectionHead parseSelectionHead() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.SELECT);
        Expression expression = parseExpression();

        accept(Token.OF);
        finish(srcPos);

        return new SelectionHead(srcPos, expression);
    }

    SelectionBody parseSelectionBody() throws SyntaxError {

        SelectionBody selectionBody;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        Case caseList = parseCaseList();

        if (currentToken.kind == Token.OTHERWISE) {
            EscapeCase escapeCase = parseEscapeCase();
            finish(srcPos);

            selectionBody = new SelectBodyWithEscape(srcPos, caseList, escapeCase);

        } else {
            selectionBody = new SelectBody(srcPos, caseList);
            finish(srcPos);
        }

        return selectionBody;
    }

    Case parseCaseList() throws SyntaxError {

        Case aCase;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        aCase = parseDenoterCase();
        finish(srcPos);

        while (currentToken.kind == Token.CASE) {
            Case nextCase = parseDenoterCase();
            finish(srcPos);

            aCase = new CaseSeq(srcPos, aCase, nextCase);
        }

        return aCase;
    }

    CaseList parseDenoterCase() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        CaseHead caseHead = parseCaseHead();
        Segment segment = parseSegment();
        finish(srcPos);

        return new CaseList(srcPos, caseHead, segment);
    }

    CaseHead parseCaseHead() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.CASE);
        Selector selector = parseSelector();

        accept(Token.COLON);

        finish(srcPos);

        return new CaseHead(srcPos, selector);
    }

    Selector parseSelector() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.LPAREN);

        Expression expression = parseExpression();

        while (currentToken.kind == Token.COMMA) {
            acceptIt();

            Expression nextParam = parseExpression();
            finish(srcPos);

            expression = new ExpressionList(srcPos, expression, nextParam);
        }

        accept(Token.RPAREN);

        return new Selector(srcPos, expression);
    }

    EscapeCase parseEscapeCase() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.OTHERWISE);
        accept(Token.COLON);

        Segment segment = parseSegment();

        finish(srcPos);

        return new EscapeCase(srcPos, segment);
    }

    SelectionEnd parseSelectionEnd() throws SyntaxError {

        SelectionEnd selectionEnd;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.END);
        accept(Token.SELECT);

        if (currentToken.kind == Token.SEMICOLON) {
            selectionEnd = new SelectEnd(srcPos);
            finish(srcPos);

        } else {
            Identifier identifier = parseIdentifier();
            finish(srcPos);

            selectionEnd = new SelectEndWithName(srcPos, identifier);
        }

        accept(Token.SEMICOLON);
        finish(srcPos);

        return selectionEnd;
    }

    Repeat parseRepeatStmt() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.REPEAT);
        Identifier identifier = parseIdentifier();

        accept(Token.SEMICOLON);
        finish(srcPos);

        return new Repeat(srcPos, identifier);
    }

    Repent parseRepentStmt() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.REPENT);
        Identifier identifier = parseIdentifier();

        accept(Token.SEMICOLON);
        finish(srcPos);

        return new Repent(srcPos, identifier);
    }

    Input parseInputStmt() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.INPUT);

        InputList inputList = parseInputList();

        accept(Token.SEMICOLON);
        finish(srcPos);

        return new Input(srcPos, inputList);
    }

    InputList parseInputList() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        Vname varList = parseVariableList();

        finish(srcPos);

        return new InputList(srcPos, varList);
    }

    Output parseOutputStmt() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        accept(Token.OUTPUT);

        OutputList outputList = parseOutputList();

        accept(Token.SEMICOLON);
        finish(srcPos);

        return new Output(srcPos, outputList);
    }

    OutputList parseOutputList() throws SyntaxError {
        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        Expression expr = parseExpression();

        while (currentToken.kind == Token.COMMA) {
            acceptIt();

            Expression nextExpr = parseExpression();
            finish(srcPos);

            expr = new ExpressionList(srcPos, expr, nextExpr);
        }

        finish(srcPos);

        return new OutputList(srcPos, expr);
    }

//_____________________________________________________________________________
//                                                                  Expression

    Expression parseExpression() throws SyntaxError {

        // in case there's a syntactic error
        Expression expressionAST;

        SourcePosition expressionPos = new SourcePosition();
        start(expressionPos);

        expressionAST = parsePrimaryExpression();

        while (currentToken.kind == Token.OPERATOR) {
            Operator opAST = parseOperator();
            Expression e2AST = parsePrimaryExpression();

            expressionAST = new BinaryExpression(expressionPos, expressionAST, opAST, e2AST);
        }

        return expressionAST;
    }

    Expression parsePrimaryExpression() throws SyntaxError {

        Expression expressionAST = null;

        SourcePosition srcPos = new SourcePosition();
        start(srcPos);

        switch (currentToken.kind) {

            case Token.INTLITERAL: {
                IntegerLiteral ilAST = parseIntegerLiteral();
                finish(srcPos);

                expressionAST = new IntegerExpression(srcPos, ilAST);
            }
            break;

            case Token.CHARLITERAL: {
                CharacterLiteral clAST = parseCharacterLiteral();
                finish(srcPos);

                expressionAST = new CharacterExpression(srcPos, clAST);
            }
            break;

            case Token.IDENTIFIER: {

                Identifier iAST = parseIdentifier();

                if (currentToken.kind == Token.LPAREN) {
                    acceptIt();

                    Expression param = parseExpression();
                    finish(srcPos);

                    while (currentToken.kind == Token.COMMA) {
                        acceptIt();

                        Expression nextParam = parseExpression();
                        finish(srcPos);

                        param = new ExpressionList(srcPos, param, nextParam);
                    }

                    accept(Token.RPAREN);
                    finish(srcPos);

                    expressionAST = new FunctionCall(srcPos, iAST, param);

                } else {
                    Vname vAST = parseRestOfVname(iAST);
                    finish(srcPos);

                    expressionAST = new VnameExpression(srcPos, vAST);
                }
            }
            break;

            case Token.OPERATOR: {
                Operator opAST = parseOperator();
                Expression eAST = parseExpression();  // recursive call
                finish(srcPos);

                expressionAST = new UnaryExpression(srcPos, opAST, eAST);
            }
            break;

            case Token.LPAREN:
                acceptIt();

                expressionAST = parseExpression();
                accept(Token.RPAREN);
                break;

            default:
                syntacticError("\"%\" cannot start an expression", currentToken.spelling);
                break;
        }

        return expressionAST;
    }

//_____________________________________________________________________________
//                                                                    Operator

    Operator parseOperator() throws SyntaxError {
        Operator O;

        if (currentToken.kind == Token.OPERATOR) {
            previousTokenPosition = currentToken.position;
            String spelling = currentToken.spelling;

            O = new Operator(previousTokenPosition, spelling);

            currentToken = lexicalAnalyser.scan();

        } else {
            O = null;
            syntacticError("operator expected here", "");
        }

        return O;
    }

//_____________________________________________________________________________
//                                                                   Variables

    Vname parseVariable() throws SyntaxError {

        Vname vnameAST;

        Identifier iAST = parseIdentifier();
        vnameAST = parseRestOfVname(iAST);

        return vnameAST;
    }

    Vname parseRestOfVname(Identifier identifierAST) throws SyntaxError {

        SourcePosition srcPos = identifierAST.position;

        Vname vAST = new SimpleVname(srcPos, identifierAST);

        while (currentToken.kind == Token.DOT || currentToken.kind == Token.LBRACKET) {

            if (currentToken.kind == Token.DOT) {
                acceptIt();

                Identifier iAST = parseIdentifier();
                vAST = new DotVname(srcPos, vAST, iAST);

            } else {
                acceptIt();

                Expression eAST = parseExpression();
                accept(Token.RBRACKET);
                finish(srcPos);

                vAST = new SubscriptVname(srcPos, vAST, eAST);
            }
        }

        return vAST;
    }


//_____________________________________________________________________________
//                                                                 Microsyntax

    IntegerLiteral parseIntegerLiteral() throws SyntaxError {
        IntegerLiteral IL;

        if (currentToken.kind == Token.INTLITERAL) {
            previousTokenPosition = currentToken.position;
            String spelling = currentToken.spelling;

            IL = new IntegerLiteral(previousTokenPosition, spelling);

            currentToken = lexicalAnalyser.scan();

        } else {
            IL = null;
            syntacticError("integer literal expected here", "");
        }

        return IL;
    }

    CharacterLiteral parseCharacterLiteral() throws SyntaxError {
        CharacterLiteral CL;

        if (currentToken.kind == Token.CHARLITERAL) {
            previousTokenPosition = currentToken.position;
            String spelling = currentToken.spelling;

            CL = new CharacterLiteral(previousTokenPosition, spelling);

            currentToken = lexicalAnalyser.scan();

        } else {
            CL = null;
            syntacticError("character literal expected here", "");
        }

        return CL;
    }

    Identifier parseIdentifier() throws SyntaxError {
        Identifier I;

        if (currentToken.kind == Token.IDENTIFIER) {

            previousTokenPosition = currentToken.position;
            String spelling = currentToken.spelling;

            I = new Identifier(previousTokenPosition, spelling);

            currentToken = lexicalAnalyser.scan();

        } else {
            I = null;
            syntacticError("identifier expected here", "");
        }

        return I;
    }

}
