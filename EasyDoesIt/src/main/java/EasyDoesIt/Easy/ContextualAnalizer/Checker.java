package EasyDoesIt.Easy.ContextualAnalizer;

import EasyDoesIt.Easy.AbstractSyntaxTrees.*;
import EasyDoesIt.Easy.ErrorReporter;
import EasyDoesIt.Easy.StdEnvironment;
import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public final class Checker implements Visitor {

    // Always returns null. Does not use the given object.
    private static SourcePosition dummyPos = new SourcePosition();

    private final static Identifier dummyI = new Identifier(dummyPos, "");

    private IdentificationTable indTable;
    private ErrorReporter reporter;

    public Checker(ErrorReporter reporter) {
        this.reporter = reporter;
        this.indTable = new IdentificationTable();

        establishStdEnvironment();
    }

    public IdentificationTable getIdentificationTable() {
        return indTable;
    }

    // Checker entry point
    public void check(Program ast) {
        ast.visit(this, null);
    }


//_____________________________________________________________________________
//                                                          Value or variables

    @Override
    public Object visitIdentifier(Identifier I, Object o) {
        Definition binding = indTable.retrieve(I.spelling);

        if (binding != null) {
            I.decl = binding;
        }

        return binding;
    }

    public Object visitDotVname(DotVname ast, Object o) {
       return null;
    }

    @Override
    public Object visitSimpleVname(SimpleVname ast, Object o) {
        System.out.println("              SimpleVname");

        ast.variable = false;
        ast.type = StdEnvironment.errorType;

        Definition binding = (Definition) ast.I.visit(this, null);

        if (binding == null) {
            reportUndeclared(ast.I);

        } else if (binding instanceof IntTypeDenoter) {
            ast.type = ((IntTypeDenoter) binding);
        }

        return ast.type;
    }

    @Override
    public Object visitSubscriptVname(SubscriptVname ast, Object o) {
        System.out.println("SubscriptVname");
        return null;
    }

//_____________________________________________________________________________
//                                                                 Expressions

    @Override
    public Object visitVnameExpression(VnameExpression ast, Object o) {

        // Vname successors return inferred types
        ast.type = (TypeDenoter) ast.V.visit(this, null);

        return ast.type;
    }

    @Override
    public Object visitConstantExpression(ConstantExpression ast, Object o) {
        System.out.println("ConstantExpression");
        return null;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression ast, Object o) {
        System.out.println("BinaryExpression");

        TypeDenoter e1Type = (TypeDenoter) ast.E1.visit(this, null);
        TypeDenoter e2Type = (TypeDenoter) ast.E2.visit(this, null);

        // return pointer to the 'declaration' of Op so
        Definition binding = (Definition) ast.O.visit(this, null);

        if (binding == null) {
            reportUndeclared(ast.O);

        } else {

            if (!(binding instanceof BinaryOperatorDefinition)) {
                reporter.reportError("\"%\" is not a binary operator", ast.O.spelling, ast.O.position);
            }

            BinaryOperatorDefinition bbinding = (BinaryOperatorDefinition) binding;

            if (bbinding.ARG1 == StdEnvironment.anyType) {

                // this operator must be "=" or "\="
                if (!e1Type.equals(e2Type)) {
                    reporter.reportError("incompatible argument types for \"%\"", ast.O.spelling, ast.position);
                }

            } else if (!e1Type.equals(bbinding.ARG1)) {
                reporter.reportError("wrong argument type for \"%\"", ast.O.spelling, ast.E1.position);

            } else if (!e2Type.equals(bbinding.ARG2)) {
                reporter.reportError("wrong argument type for \"%\"", ast.O.spelling, ast.E2.position);
            }

            ast.type = bbinding.RES;
        }

        return ast.type;
    }

    @Override
    public Object visitOperator(Operator O, Object o) {

        Definition binding = indTable.retrieve(O.spelling);

        if (binding != null) {
            O.decl = binding;
        }

        return binding;
    }

    @Override
    public Object visitCharacterLiteral(CharacterLiteral ast, Object o) {
        System.out.println("CharacterLiteral");
        return StdEnvironment.charType;
    }

    @Override
    public Object visitIntegerLiteral(IntegerLiteral ast, Object o) {
        System.out.println("              IntegerLiteral");
        return StdEnvironment.integerType;
    }

    @Override
    public Object visitUnaryExpression(UnaryExpression ast, Object o) {
        System.out.println("UnaryExpression");
        return null;
    }

    @Override
    public Object visitIntegerExpression(IntegerExpression ast, Object o) {
        ast.type = StdEnvironment.integerType;

        return ast.type;
    }

    @Override
    public Object visitFunctionCall(FunctionCall ast, Object o) {
        System.out.println("FunctionCall");
        return null;
    }

    @Override
    public Object visitCharacterExpression(CharacterExpression ast, Object o) {
        System.out.println("CharacterExpression");
        ast.type = StdEnvironment.charType;

        return ast.type;
    }

//_____________________________________________________________________________
//                                                                     Program

    @Override
    public Object visitProgram(Program ast, Object o) {
        System.out.println("Program");

        ast.program.visit(this, null);
        return null;
    }

    @Override
    public Object visitProgramBody(ProgramBody ast, Object o) {
        System.out.println("ProgramBody");

        indTable.openScope();

        ast.prgBody.visit(this, null);

        indTable.closeScope();

        return null;
    }

    @Override
    public Object visitCommand(Segment ast, Object o) {
        System.out.println("   Command");

        ast.definition.visit(this, null);
        ast.statement.visit(this, null);

        return null;
    }

//_____________________________________________________________________________
//                                                                 Definitions

    // 1. Always returns null and does not use the given subtree(phrase)
    // 2. Enters all declared identifiers into the identification table

    @Override
    public Object visitDefinitionSeq(DefinitionSeq ast, Object o) {

        // order matters
        ast.definitionSeq.visit(this, o);
        ast.definition.visit(this, o);

        return null;
    }

    @Override
    public Object visitEmptyDefinition(EmptyDefinition ast, Object o) {
        return null;
    }

    @Override
    public Object visitTypeDefinition(TypeDefinition ast, Object o) {
        ast.T = (TypeDenoter) ast.T.visit(this, null);
        indTable.enter(ast.I.spelling, ast);

        if (ast.duplicated) {
            reporter.reportError("identifier \"%\" already declared", ast.I.spelling, ast.position);
        }

        return null;
    }

    @Override
    public Object visitIdentifierType(IdentifierType ast, Object o) {
        System.out.println("         IdentifierType");
        Definition binding = (Definition) ast.identifier.visit(this, null);

        if (binding == null) {
            reportUndeclared(ast.identifier);

            return StdEnvironment.errorType;

        } else if (!(binding instanceof TypeDefinition)) {
            reporter.reportError("\"%\" is not a type identifier", ast.identifier.spelling, ast.identifier.position);

            return StdEnvironment.errorType;
        }

        return ((TypeDefinition) binding).T;
    }

    @Override
    public Object visitArrayType(ArrayType ast, Object o) {
        System.out.println("      ArrayType");

        ast.type =  (TypeDenoter) ast.type.visit(this, null);
        TypeDenoter boundsType  = (TypeDenoter) ast.arrayBounds.visit(this, null);

        if (! (boundsType instanceof IntTypeDenoter) ) {
            reporter.reportError("Array bound type must be integer", "", ast.arrayBounds.position);
        }

        return ast;
    }

    @Override
    public Object visitSingleArrayBounds(SingleArrayBounds ast, Object o) {
        System.out.println("           SingleArrayBounds");

        TypeDenoter binding = (TypeDenoter) ast.expression.visit(this, null);
        ast.elemCount = 1;

        return binding;
    }

    @Override
    public Object visitSegmentedArrayBounds(SegmentedArrayBounds ast, Object o) {
        System.out.println("           SegmentedArrayBounds");

        TypeDenoter fromBinding = (TypeDenoter) ast.from.visit(this, null);
        TypeDenoter toBinding = (TypeDenoter) ast.to.visit(this, null);

        if (! (fromBinding instanceof IntTypeDenoter) ) {
            reporter.reportError("Array bound type must be integer", "", ast.from.position);
        }

        if (! (toBinding instanceof IntTypeDenoter) ) {
            reporter.reportError("Array bound type must be integer", "", ast.to.position);
        }

        // fromBiding < toBinding

        return toBinding;
    }

    @Override
    public Object visitStructureType(StructureType ast, Object o) {
        System.out.println("      StructureType");
        ast.fieldDenoter = (Field) ast.fieldDenoter.visit(this, null);

        return ast;
    }

    @Override
    public Object visitFieldList(FieldList ast, Object o) {

        ast.fieldSeq.visit(this, null);
        ast.field.visit(this, null);

        return null;
    }

    @Override
    public Object visitFieldDenoter(FieldDenoter ast, Object o) {

        TypeDenoter typeDenoter = (TypeDenoter) ast.typeDenoter.visit(this, null);
        indTable.enter(ast.I.spelling, typeDenoter);

        return null;
    }

    @Override
    public Object visitDeclaration(Declaration ast, Object o) {
        System.out.println("   Declaration");

        ast.typeDenoter = (TypeDenoter) ast.typeDenoter.visit(this, null);
        ast.declaredNames.visit(this, ast.typeDenoter);

        return ast.typeDenoter;
    }

    @Override
    public Object visitSingleDeclaredName(SingleDeclaredName ast, Object o) {
        System.out.println("         SingleDeclaredName");

        ast.identifier.visit(this, null);
        indTable.enter(ast.identifier.spelling, (Definition) o);

        return null;
    }

    @Override
    public Object visitMultipleDeclaredNames(MultipleDeclaredNames ast, Object o) {
        System.out.println("   MultipleDeclaredNames");

        ast.declaredNamesSeq.visit(this, o);
        ast.declaredNames.visit(this, o);

        return null;
    }

    @Override
    public Object visitEmptyDeclaredName(EmptyDeclaredName ast, Object o) {
        System.out.println("EmptyDeclaredName");
        return null;
    }

    @Override
    public Object visitInternalProcedure(InternalProcedure ast, Object o) {
        System.out.println("InternalProcedure");
        return null;
    }

    @Override
    public Object visitProcedureDefinition(ProcedureDefinition ast, Object o) {
        System.out.println("ProcedureDefinition");
        return null;
    }

    @Override
    public Object visitFunctionDefinition(FunctionDefinition ast, Object o) {
        System.out.println("FunctionDefinition");
        return null;
    }

    @Override
    public Object visitProcedureHead(ProcedureHead ast, Object o) {
        System.out.println("ProcedureHead");
        return null;
    }

    @Override
    public Object visitProcedureEnd(ProcedureEnd ast, Object o) {
        System.out.println("ProcedureEnd");
        return null;
    }

    @Override
    public Object visitProcedureName(ProcedureName ast, Object o) {
        System.out.println("ProcedureName");
        return null;
    }

    @Override
    public Object visitParameterList(ParameterList ast, Object o) {
        System.out.println("ParameterList");
        return null;
    }

    @Override
    public Object visitProcedureNameWithParams(ProcedureNameWithParams ast, Object o) {
        System.out.println("ProcedureNameWithParams");
        return null;
    }

    @Override
    public Object visitParameterByValue(ParameterByValue ast, Object o) {
        System.out.println("ParameterByValue");
        return null;
    }

    @Override
    public Object visitParameterByName(ParameterByName ast, Object o) {
        System.out.println("ParameterByName");
        return null;
    }

    @Override
    public Object visitFunctionHead(FunctionHead ast, Object o) {
        System.out.println("FunctionHead");
        return null;
    }

    @Override
    public Object visitFunctionEnd(FunctionEnd ast, Object o) {
        System.out.println("FunctionEnd");
        return null;
    }

//_____________________________________________________________________________
//                                                                  Statements

    @Override
    public Object visitEmptyStatement(EmptyStatement ast, Object o) {
        System.out.println("EmptyStatement");
        return null;
    }

    @Override
    public Object visitNullStmt(NullStmt ast, Object o) {
        System.out.println("NullStmt");
        return null;
    }

    @Override
    public Object visitStatementSeq(StatementSeq ast, Object o) {

        ast.stmtSeq.visit(this, null);
        ast.stmt.visit(this, null);

        return null;
    }

    @Override
    public Object visitVariableList(VariableList ast, Object o) {
        System.out.println("      VariableList");
        ast.vnameSeq.visit(this, null);
        ast.vname.visit(this, null);

        return null;
    }

    @Override
    public Object visitAssignmentStmt(AssignmentStmt ast, Object o) {
        System.out.println("      AssignmentStmt");

        ast.variableList.visit(this, null);
        ast.expression.visit(this, null);

        return null;
    }

    @Override
    public Object visitProcedureCallStmt(ProcedureCallStmt ast, Object o) {
        System.out.println("ProcedureCallStmt");
        return null;
    }

    @Override
    public Object visitCall(Call ast, Object o) {
        System.out.println("Call");
        return null;
    }

    @Override
    public Object visitExpressionList(ExpressionList ast, Object o) {
        System.out.println("ExpressionList");
        return null;
    }

    @Override
    public Object visitCallWithParams(CallWithParams ast, Object o) {
        System.out.println("CallWithParams");
        return null;
    }

    @Override
    public Object visitReturn(Return ast, Object o) {
        System.out.println("Return");
        return null;
    }

    @Override
    public Object visitReturnWithExpression(ReturnWithExpression ast, Object o) {
        System.out.println("ReturnWithExpression");
        return null;
    }

    @Override
    public Object visitExitStmt(ExitStmt ast, Object o) {
        System.out.println("ExitStmt");
        return null;
    }

    @Override
    public Object visitIfStmt(IfStmt ast, Object o) {
        System.out.println("IfStmt");

        ast.conditionalClause.visit(this, null);
        ast.trueBranch.visit(this, null);

        return null;
    }

    @Override
    public Object visitIfElseStmt(IfElseStmt ast, Object o) {
        System.out.println("IfElseStmt");

        ast.conditionalClause.visit(this, null);

        ast.trueBranch.visit(this, null);
        ast.falseBranch.visit(this, null);

        return null;
    }

    @Override
    public Object visitConditionalClause(ConditionalClause ast, Object o) {
        System.out.println("ConditionalClause");

        TypeDenoter eType = (TypeDenoter) ast.expression.visit(this, null);

        // 'if' expression should be boolean type
        if (!eType.equals(StdEnvironment.booleanType)) {
            reporter.reportError("Boolean expression expected here", "", ast.expression.position);
        }

        return null;
    }

    @Override
    public Object visitTrueBranch(TrueBranch ast, Object o) {
        ast.segment.visit(this, null);
        return null;
    }

    @Override
    public Object visitFalseBranch(FalseBranch ast, Object o) {
        System.out.println("FalseBranch");

        ast.segment.visit(this, null);

        return null;
    }

    @Override
    public Object visitSimpleCompoundEnd(SimpleCompoundEnd ast, Object o) {
        System.out.println("SimpleCompoundEnd");
        return null;
    }

    @Override
    public Object visitCompoundEndWithName(CompoundEndWithName ast, Object o) {
        ast.name.visit(this, null);
        return null;
    }

    @Override
    public Object visitCompoundStmt(CompoundStmt ast, Object o) {
        System.out.println("      CompoundStmt");

        indTable.openScope();

        ast.segment.visit(this, null);
        ast.compoundEnd.visit(this, null);

        indTable.closeScope();

        return null;
    }

    @Override
    public Object visitForLoopStmt(ForLoopStmt ast, Object o) {
        System.out.println("ForLoopStmt");
        return null;
    }

    @Override
    public Object visitForHead(ForHead ast, Object o) {
        System.out.println("ForHead");
        return null;
    }

    @Override
    public Object visitWhile(While ast, Object o) {
        System.out.println("While");
        return null;
    }

    @Override
    public Object visitStepperWhile(StepperWhile ast, Object o) {
        System.out.println("StepperWhile");
        return null;
    }

    @Override
    public Object visitStepper(Stepper ast, Object o) {
        System.out.println("Stepper");
        return null;
    }

    @Override
    public Object visitStep(Step ast, Object o) {
        System.out.println("Step");
        return null;
    }

    @Override
    public Object visitExpressionStep(ExpressionStep ast, Object o) {
        System.out.println("ExpressionStep");
        return null;
    }

    @Override
    public Object visitLimit(Limit ast, Object o) {
        System.out.println("Limit");
        return null;
    }

    @Override
    public Object visitExpressionStepLimit(ExpressionStepLimit ast, Object o) {
        System.out.println("ExpressionStepLimit");
        return null;
    }

    @Override
    public Object visitExpressionLimit(ExpressionLimit ast, Object o) {
        System.out.println("ExpressionLimit");
        return null;
    }

    @Override
    public Object visitSimpleForEnd(SimpleForEnd ast, Object o) {
        System.out.println("SimpleForEnd");
        return null;
    }

    @Override
    public Object visitForEndWithName(ForEndWithName ast, Object o) {
        System.out.println("ForEndWithName");
        return null;
    }

    @Override
    public Object visitSelectionStmt(SelectionStmt ast, Object o) {
        System.out.println("SelectionStmt");
        return null;
    }

    @Override
    public Object visitSelectionHead(SelectionHead ast, Object o) {
        System.out.println("SelectionHead");
        return null;
    }

    @Override
    public Object visitSelectBody(SelectBody ast, Object o) {
        System.out.println("SelectBody");
        return null;
    }

    @Override
    public Object visitSelectBodyWithEscape(SelectBodyWithEscape ast, Object o) {
        System.out.println("SelectBodyWithEscape");
        return null;
    }

    @Override
    public Object visitSelectionEnd(SelectEnd ast, Object o) {
        System.out.println("SelectEnd");
        return null;
    }

    @Override
    public Object visitSelectEndWithName(SelectEndWithName ast, Object o) {
        System.out.println("SelectEndWithName");
        return null;
    }

    @Override
    public Object visitCaseSeq(CaseSeq ast, Object o) {
        System.out.println("CaseSeq");
        return null;
    }

    @Override
    public Object visitCaseHead(CaseHead ast, Object o) {
        System.out.println("CaseHead");
        return null;
    }

    @Override
    public Object visitCaseList(CaseList ast, Object o) {
        System.out.println("CaseList");
        return null;
    }

    @Override
    public Object visitSelector(Selector ast, Object o) {
        System.out.println("Selector");
        return null;
    }

    @Override
    public Object visitEscapeCase(EscapeCase ast, Object o) {
        System.out.println("EscapeCase");
        return null;
    }

    @Override
    public Object visitRepeat(Repeat ast, Object o) {
        System.out.println("Repeat");
        return null;
    }

    @Override
    public Object visitRepent(Repent ast, Object o) {
        System.out.println("Repent");
        return null;
    }

    @Override
    public Object visitInput(Input ast, Object o) {
        System.out.println("         Input");
        ast.inputList.visit(this, null);

        return null;
    }

    @Override
    public Object visitInputList(InputList ast, Object o) {
        System.out.println("           InputList");
        ast.varList.visit(this, null);
        return null;
    }

    @Override
    public Object visitOutput(Output ast, Object o) {
        System.out.println("         Output");
        ast.outputList.visit(this, null);
        return null;
    }

    @Override
    public Object visitOutputList(OutputList ast, Object o) {
        System.out.println("            OutputList");

        TypeDenoter binding = (TypeDenoter) ast.expr.visit(this, null);

        if (!binding.equals(StdEnvironment.charType)) {
            reporter.reportError("Character expression expected here", "", ast.expr.position);
        }

        return null;
    }

//_____________________________________________________________________________
//                                                        Standard Environment

    @Override
    public Object visitUnaryOperatorDefinition(UnaryOperatorDefinition ast, Object o) {
        System.out.println("UnaryOperatorDefinition");
        return null;
    }

    @Override
    public Object visitBinaryOperatorDefinition(BinaryOperatorDefinition ast, Object o) {
        return null;
    }

    @Override
    public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {
        System.out.println("AnyTypeDenoter");
        return null;
    }

    @Override
    public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) {
        System.out.println("ErrorTypeDenoter");
        return null;
    }

    @Override
    public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {
        System.out.println("BoolTypeDenoter");
        return null;
    }

    @Override
    public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) {
        System.out.println("IntTypeDenoter");
        return null;
    }

    @Override
    public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) {
        System.out.println("CharTypeDenoter");
        return null;
    }

    @Override
    public Object visitFloatTypeDenoter(FloatTypeDenoter ast, Object o) {
        System.out.println("FloatTypeDenoter");
        return null;
    }

    @Override
    public Object visitRealTypeDenoter(RealTypeDenoter ast, Object o) {
        System.out.println("RealTypeDenoter");
        return null;
    }

//_____________________________________________________________________________
//

    private void reportUndeclared(Terminal leaf) {
        reporter.reportError("\"%\" is not declared", leaf.spelling, leaf.position);
    }

    private TypeDefinition declareStdType(String id, TypeDenoter typedenoter) {

        TypeDefinition binding;

        binding = new TypeDefinition(dummyPos, new Identifier(dummyPos, id), typedenoter);
        indTable.enter(id, binding);

        return binding;
    }

    private Declaration declareStdBoolean(String id, TypeDenoter constType) {

        Declaration binding;
        Identifier identifier = new Identifier(dummyPos, id);

        binding = new Declaration(dummyPos, new SingleDeclaredName(dummyPos, identifier), constType);

        indTable.enter(id, binding);

        return binding;
    }

    private FunctionDefinition declareStdFunc(String id, Parameter fps, TypeDenoter resultType) {

        FunctionDefinition binding;

        Identifier name = new Identifier(dummyPos, id);
        BlockCodeName blockCodeName = new ProcedureNameWithParams(dummyPos, name, fps);

        FunctionHead funcHead = new FunctionHead(dummyPos, blockCodeName, resultType);
        Segment segment = new Segment(dummyPos, new EmptyDefinition(dummyPos), new EmptyStatement(dummyPos));
        FunctionEnd funcEnd = new FunctionEnd(dummyPos, name);

        binding = new FunctionDefinition(dummyPos, funcHead, segment, funcEnd);
        indTable.enter(id, binding);

        return binding;
    }

    private UnaryOperatorDefinition declareStdUnaryOp(String op, TypeDenoter argType, TypeDenoter resultType) {

        UnaryOperatorDefinition binding;

        binding = new UnaryOperatorDefinition(dummyPos, new Operator(dummyPos, op), argType, resultType);
        indTable.enter(op, binding);

        return binding;
    }

    private BinaryOperatorDefinition declareStdBinaryOp(String op, TypeDenoter arg1Type, TypeDenoter arg2type,
                                                        TypeDenoter resultType) {

        BinaryOperatorDefinition binding;

        binding = new BinaryOperatorDefinition(dummyPos, new Operator(dummyPos, op), arg1Type, arg2type, resultType);
        indTable.enter(op, binding);

        return binding;
    }

    private void establishStdEnvironment() {

        StdEnvironment.booleanType = new BoolTypeDenoter(dummyPos);
        StdEnvironment.charType = new CharTypeDenoter(dummyPos);
        StdEnvironment.integerType = new IntTypeDenoter(dummyPos);
        StdEnvironment.floatType = new FloatTypeDenoter(dummyPos);
        StdEnvironment.realType = new RealTypeDenoter(dummyPos);

        StdEnvironment.anyType = new AnyTypeDenoter(dummyPos);
        StdEnvironment.errorType = new ErrorTypeDenoter(dummyPos);

        StdEnvironment.booleanDecl = declareStdType("BOOLEAN", StdEnvironment.booleanType);
        StdEnvironment.trueDecl = declareStdBoolean("TRUE", StdEnvironment.booleanType);
        StdEnvironment.falseDecl = declareStdBoolean("FALSE", StdEnvironment.booleanType);
        StdEnvironment.notDecl = declareStdUnaryOp("NOT", StdEnvironment.booleanType, StdEnvironment.booleanType);
        StdEnvironment.andDecl = declareStdBinaryOp("AND", StdEnvironment.booleanType, StdEnvironment.booleanType, StdEnvironment.booleanType);
        StdEnvironment.orDecl = declareStdBinaryOp("OR", StdEnvironment.booleanType, StdEnvironment.booleanType, StdEnvironment.booleanType);

        StdEnvironment.integerDecl = declareStdType("INTEGER", StdEnvironment.integerType);
        StdEnvironment.moduloDecl = declareStdBinaryOp("MOD", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
        StdEnvironment.lessDecl = declareStdBinaryOp("<", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
        StdEnvironment.greaterDecl = declareStdBinaryOp(">", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
        StdEnvironment.notlessDecl = declareStdBinaryOp(">=", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
        StdEnvironment.notgreaterDecl = declareStdBinaryOp("<=", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);

        StdEnvironment.realDecl = declareStdType("REAL", StdEnvironment.realType);

        StdEnvironment.floatDecl = declareStdType("FLOAT", StdEnvironment.floatType);
        StdEnvironment.addDecl = declareStdBinaryOp("+", StdEnvironment.floatType, StdEnvironment.floatType, StdEnvironment.floatType);
        StdEnvironment.subtractDecl = declareStdBinaryOp("-", StdEnvironment.floatType, StdEnvironment.floatType, StdEnvironment.floatType);
        StdEnvironment.multiplyDecl = declareStdBinaryOp("*", StdEnvironment.floatType, StdEnvironment.floatType, StdEnvironment.floatType);
        StdEnvironment.divideDecl = declareStdBinaryOp("/", StdEnvironment.floatType, StdEnvironment.floatType, StdEnvironment.floatType);

        StdEnvironment.charDecl = declareStdType("STRING", StdEnvironment.charType);

        StdEnvironment.substrDecl = declareStdFunc("SUBSTR",
                new ParameterList(dummyPos,
                        new ParameterList(dummyPos,
                                new ParameterByValue(dummyPos, dummyI, StdEnvironment.charType),
                                new ParameterByValue(dummyPos, dummyI, StdEnvironment.integerType)),
                        new ParameterByValue(dummyPos, dummyI, StdEnvironment.integerType)), StdEnvironment.charType);

        StdEnvironment.lengthDecl = declareStdFunc("LENGTH", new ParameterByValue(dummyPos, dummyI, StdEnvironment.integerType), StdEnvironment.charType);
        StdEnvironment.concatDecl = declareStdBinaryOp("||", StdEnvironment.charType, StdEnvironment.charType, StdEnvironment.charType);

        StdEnvironment.equalDecl = declareStdBinaryOp("=", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.booleanType);
        StdEnvironment.unequalDecl = declareStdBinaryOp("<>", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.booleanType);
    }
}
