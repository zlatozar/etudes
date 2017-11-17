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

    // not tested
    public Object visitDotVname(DotVname ast, Object o) {
        System.out.println("DotVname");

        ast.type = null;

        TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);

        ast.variable = ast.V.variable;

        if (!(vType instanceof StructuredTypeDenoter)) {
            reporter.reportError("record expected here", "", ast.V.position);

        } else {
            ast.type = checkFieldIdentifier(((StructuredTypeDenoter) vType).fieldTypeDenoterDenoter, ast.I);

            if (ast.type == StdEnvironment.errorType) {
                reporter.reportError("no field \"%\" in this struct type", ast.I.spelling, ast.I.position);
            }
        }

        return ast.type;
    }

    // note tested
    private static TypeDenoter checkFieldIdentifier(FieldTypeDenoter fieldTypeDenoter, Identifier I) {

        if (fieldTypeDenoter instanceof MultipleFieldTypeDenoter) {

            MultipleFieldTypeDenoter ft = (MultipleFieldTypeDenoter) fieldTypeDenoter;

            if (ft.I.spelling.compareTo(I.spelling) == 0) {
                I.decl = fieldTypeDenoter;
                return ft.typeDenoter;

            } else {
                return checkFieldIdentifier(ft.fieldTypeDenoter, I);
            }

        } else if (fieldTypeDenoter instanceof SingleFieldTypeDenoter) {

            SingleFieldTypeDenoter ft = (SingleFieldTypeDenoter) fieldTypeDenoter;

            if (ft.I.spelling.compareTo(I.spelling) == 0) {
                I.decl = fieldTypeDenoter;
                return ft.typeDenoter;
            }
        }

        return StdEnvironment.errorType;
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
            ast.variable = false;

        } else if (binding instanceof RealTypeDenoter) {
            ast.type = ((RealTypeDenoter) binding);
            ast.variable = false;

        } else if (binding instanceof ConstDefinition) {
            ast.type = ((ConstDefinition) binding).E.type;
            ast.variable = false;

        } else if (binding instanceof ArrayTypeDenoter) {
            ast.type = ((ArrayTypeDenoter) binding);
            ast.variable = false;
        }

        return ast.type;
    }

    @Override
    public Object visitSubscriptVname(SubscriptVname ast, Object o) {
        System.out.println("SubscriptVname");

        TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
        ast.variable = ast.V.variable;

        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);

        if (vType != StdEnvironment.errorType) {

            if (!(vType instanceof ArrayTypeDenoter)) {
                reporter.reportError("array expected here", "", ast.V.position);

            } else {

                if (!eType.equals(StdEnvironment.integerType)) {
                    reporter.reportError("Integer expression expected here", "", ast.E.position);
                }

                ast.type = ((ArrayTypeDenoter) vType).type;
            }
        }

        return ast.type;
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

                // "||" concatenation is an exception
                String concatenation = StdEnvironment.concatDecl.O.spelling;

                if (!e1Type.equals(e2Type) && !ast.O.spelling.equals(concatenation)) {
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
    public Object visitRealLiteral(RealLiteral ast, Object o) {
        return StdEnvironment.realType;
    }

    @Override
    public Object visitUnaryExpression(UnaryExpression ast, Object o) {
        System.out.println("UnaryExpression");

        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);

        Definition binding = (Definition) ast.O.visit(this, null);

        if (binding == null) {
            reportUndeclared(ast.O);
            ast.type = StdEnvironment.errorType;

        } else if (!(binding instanceof UnaryOperatorDefinition)) {
            reporter.reportError("\"%\" is not a unary operator", ast.O.spelling, ast.O.position);

        } else {
            UnaryOperatorDefinition ubinding = (UnaryOperatorDefinition) binding;

            if (!eType.equals(ubinding.ARG)) {
                reporter.reportError("wrong argument type for \"%\"", ast.O.spelling, ast.O.position);
            }

            ast.type = ubinding.RES;
        }
        return ast.type;
    }

    @Override
    public Object visitIntegerExpression(IntegerExpression ast, Object o) {
        ast.type = StdEnvironment.integerType;

        return ast.type;
    }

    @Override
    public Object visitRealExpression(RealExpression ast, Object o) {
        ast.type = StdEnvironment.realType;

        return ast.type;
    }

    @Override
    public Object visitFunctionCall(FunctionCall ast, Object o) {
        System.out.println("FunctionCall");

        Definition binding = (Definition) ast.I.visit(this, null);

        if (binding == null) {
            reportUndeclared(ast.I);
        }

        if (!(binding instanceof FunctionDefinition)) {
            reporter.reportError("\"%\" is not a function identifier", ast.I.spelling, ast.I.position);
        }

        ast.APS.visit(this, ((FunctionDefinition) binding).funcHead.FPS);

        return ((FunctionDefinition) binding).funcHead.typeDenoter;
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

    @Override
    public Object visitConstDefinition(ConstDefinition ast, Object o) {
        System.out.println("ConstDefinition");
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
        System.out.println("TypeDefinition");

        ast.T = (TypeDenoter) ast.T.visit(this, null);
        indTable.enter(ast.I.spelling, ast);

        if (ast.duplicated) {
            reporter.reportError("identifier \"%\" already declared", ast.I.spelling, ast.position);
        }

        return null;
    }

    @Override
    public Object visitIdentifierTypeDenoter(IdentifierTypeDenoter ast, Object o) {
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
    public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o) {
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
    public Object visitStructuredTypeDenoter(StructuredTypeDenoter ast, Object o) {
        System.out.println("      StructureType");
        ast.fieldTypeDenoterDenoter = (FieldTypeDenoter) ast.fieldTypeDenoterDenoter.visit(this, null);

        return ast;
    }

    @Override
    public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o) {
        System.out.println("MultipleFieldTypeDenoter");

        ast.typeDenoter = (TypeDenoter) ast.typeDenoter.visit(this, null);
        ast.fieldTypeDenoter.visit(this, null);

        return ast;
    }

    @Override
    public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o) {
        System.out.println("SingleFieldTypeDenoter");

        ast.typeDenoter = (TypeDenoter) ast.typeDenoter.visit(this, null);
        return ast;
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

        ast.blockCode.visit(this, null);

        return null;
    }

    @Override
    public Object visitProcedureDefinition(ProcedureDefinition ast, Object o) {
        System.out.println("ProcedureDefinition");

        String procName = (String) ast.procHead.visit(this, ast);

        indTable.enter(procName, ast);

        indTable.openScope();

        ast.segment.visit(this, null);
        ast.procEnd.visit(this, procName);

        indTable.closeScope();

        return null;
    }

    @Override
    public Object visitProcedureHead(ProcedureHead ast, Object o) {
        System.out.println("ProcedureHead");

        ast.identifier.visit(this, o);
        ast.FPS.visit(this, o);

        return ast.identifier.spelling;
    }

    @Override
    public Object visitFormalParameterList(FormalParameterList ast, Object o) {
        System.out.println("FormalParameterList");

        ast.paramSeq.visit(this, null);
        ast.param.visit(this, null);

        return null;
    }

    @Override
    public Object visitCallActualParameter(CallActualParameter ast, Object o) {
        System.out.println("CallActualParameter");

        TypeDenoter apType = (TypeDenoter) ast.expression.visit(this, null);

        if (o instanceof FormalParameter) {
            FormalParameter fp = (FormalParameter) o;

            if (!(fp.typeDenoter.equals(apType))) {
                reporter.reportError("wrong type for expression \"%\"", ast.expression.toString(), ast.expression.position);
            }

        } else {
            reporter.reportError("wrong signature for expression \"%\"",  ast.expression.toString(), ast.expression.position);
        }

        return null;
    }

    @Override
    public Object visitProcedureEnd(ProcedureEnd ast, Object o) {
        System.out.println("ProcedureEnd");

        ast.procName.visit(this, null);

        String procNameHead = (String) o;
        String procNameEnd = ast.procName.spelling;

        if (!procNameHead.equals(procNameEnd)) {
            reporter.reportError("procedure should end with \"%\" identifier", procNameHead, ast.position);
        }

        return null;
    }

    @Override
    public Object visitFormalParameterByValue(FormalParameterByValue ast, Object o) {
        System.out.println("ParameterByValue");

        // eliminate type identifiers
        ast.typeDenoter = (TypeDenoter) ast.typeDenoter.visit(this, null);

        ast.identifier.visit(this, ast.typeDenoter);

        indTable.enter(ast.identifier.spelling, ast.typeDenoter);

        if (ast.typeDenoter.duplicated) {
            reporter.reportError("parameter \"%\" already declared", ast.identifier.spelling, ast.position);
        }

        return null;
    }

    @Override
    public Object visitFormalParameterByName(FormalParameterByName ast, Object o) {
        System.out.println("ParameterByName");

        ast.typeDenoter = (TypeDenoter) ast.typeDenoter.visit(this, null);
        ast.identifier.visit(this, ast.typeDenoter);

        indTable.enter(ast.identifier.spelling, ast.typeDenoter);

        if (ast.typeDenoter.duplicated) {
            reporter.reportError("parameter \"%\" already declared", ast.identifier.spelling, ast.position);
        }

        return null;
    }

    @Override
    public Object visitFunctionDefinition(FunctionDefinition ast, Object o) {
        System.out.println("FunctionDefinition");

        String procName = (String) ast.funcHead.visit(this, ast);

        indTable.enter(procName, ast);

        if (ast.duplicated) {
           reporter.reportError("identifier \"%\" already declared", ast.funcHead.identifier.spelling, ast.position);
        }

        indTable.openScope();

        // TODO: Check return type with declared one
        ast.segment.visit(this, null);

        ast.funcEnd.visit(this, procName);

        indTable.closeScope();

        return null;
    }

    @Override
    public Object visitFunctionHead(FunctionHead ast, Object o) {
        System.out.println("FunctionHead");

        ast.identifier.visit(this, o);
        ast.FPS.visit(this, o);

        // eliminate type identifiers
        ast.typeDenoter = (TypeDenoter) ast.typeDenoter.visit(this, null);

        return ast.identifier.spelling;
    }

    @Override
    public Object visitFunctionEnd(FunctionEnd ast, Object o) {
        System.out.println("FunctionEnd");

        ast.identifier.visit(this, null);

        String funcNameHead = (String) o;
        String funcNameEnd = ast.identifier.spelling;

        if (!funcNameHead.equals(funcNameEnd)) {
            reporter.reportError("function should end with \"%\" identifier", funcNameHead, ast.position);
        }

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

        ast.prcRef.visit(this, null);

        return null;
    }

    @Override
    public Object visitCall(Call ast, Object o) {
        System.out.println("Call");

        Definition binding = (Definition) ast.identifier.visit(this, null);

        if (binding == null) {
            reportUndeclared(ast.identifier);
        }

        if (!(binding instanceof ProcedureDefinition)) {
            reporter.reportError("\"%\" is not a procedure identifier", ast.identifier.spelling, ast.identifier.position);
        }

        return null;
    }

    @Override
    public Object visitCallWithParams(CallWithParams ast, Object o) {
        System.out.println("CallWithParams");

        Definition binding = (Definition) ast.identifier.visit(this, null);

        if (binding == null) {
            reportUndeclared(ast.identifier);
        }

        if (!(binding instanceof ProcedureDefinition)) {
            reporter.reportError("\"%\" is not a procedure identifier", ast.identifier.spelling, ast.identifier.position);
        }

        ast.params.visit(this, ((ProcedureDefinition) binding).procHead.FPS);

        return null;
    }

    @Override
    public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o) {
        System.out.println("SingleActualParameterSequence");

        // use passed FormalParameterSequence because it should be compared to ActualParameterSequence
        FormalParameterSequence fps = (FormalParameterSequence) o;

        if (!(fps instanceof SingleFormalParameterSequence)) {
            reporter.reportError("incorrect number of actual parameters", "", ast.position);

        } else {
            ast.AP.visit(this, ((SingleFormalParameterSequence) fps).FP);
        }

        return null;
    }

    @Override
    public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o) {
        System.out.println("MultipleActualParameterSequence");

        // use passed FormalParameterSequence
        FormalParameterSequence fps = (FormalParameterSequence) o;

        if (!(fps instanceof FormalParameterList)) {
            reporter.reportError("too many actual parameters", "", ast.position);

        } else {
            ast.AP.visit(this, ((FormalParameterList) fps).param);
            ast.APS.visit(this, ((FormalParameterList) fps).paramSeq);
        }

        return null;
    }

    @Override
    public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o) {
        System.out.println("EmptyActualParameterSequence");
        return null;
    }

    @Override
    public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o) {
        System.out.println("SingleFormalParameterSequence");

        ast.FP.visit(this, null);

        return null;
    }

    @Override
    public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o) {
        System.out.println("EmptyFormalParameterSequence");
        return null;
    }

    @Override
    public Object visitExpressionList(ExpressionList ast, Object o) {
        System.out.println("ExpressionList");

        ast.exprSeq.visit(this, null);
        ast.expr.visit(this, null);

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

//        if (o == null) {
//            reporter.reportError("RETURN should be in function", "", ast.expression.position);
//        }

        // TODO: should be the same as function return type
        TypeDenoter rType = (TypeDenoter) ast.expression.visit(this, null);

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

        return eType;
    }

    @Override
    public Object visitTrueBranch(TrueBranch ast, Object o) {

        indTable.openScope();

        ast.segment.visit(this, null);

        indTable.closeScope();

        return null;
    }

    @Override
    public Object visitFalseBranch(FalseBranch ast, Object o) {
        System.out.println("FalseBranch");

        indTable.openScope();

        ast.segment.visit(this, null);

        indTable.closeScope();

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

        ast.forHead.visit(this, null);

        indTable.openScope();
        ast.segment.visit(this, null);
        indTable.closeScope();

        ast.forEnd.visit(this, null);

        return null;
    }

    @Override
    public Object visitForHead(ForHead ast, Object o) {
        System.out.println("ForHead");

        TypeDenoter varType = (TypeDenoter) ast.var.visit(this, null);
        ast.loopControl.visit(this, varType);

        return null;
    }

    @Override
    public Object visitWhile(While ast, Object o) {
        System.out.println("While");

        TypeDenoter binding = (TypeDenoter) ast.expression.visit(this, null);

        if (!binding.equals(StdEnvironment.booleanType)) {
            reporter.reportError("expression in a WHILE should have boolean type", "", ast.expression.position);
        }

        return null;
    }

    @Override
    public Object visitStepperWhile(StepperWhile ast, Object o) {
        System.out.println("StepperWhile");

        ast.stepExpression.visit(this, o);
        ast.aWhileExpression.visit(this, null);

        return null;
    }

    @Override
    public Object visitStepper(Stepper ast, Object o) {
        System.out.println("Stepper");

        TypeDenoter loopExpression = (TypeDenoter) o;
        TypeDenoter binding = (TypeDenoter) ast.stepExpression.visit(this, null);

        if (!(loopExpression.equals(binding))) {
            reporter.reportError("types of FOR and STEP expressions differ", "", ast.stepExpression.position);
        }

        return binding;
    }

    @Override
    public Object visitStep(Step ast, Object o) {
        System.out.println("Step");

        TypeDenoter loopType = (TypeDenoter) o;
        TypeDenoter binding = (TypeDenoter) ast.step.visit(this, null);

        if (!(loopType.equals(binding))) {
            reporter.reportError("types of FOR and BY expressions differ", "", ast.step.position);
        }

        return binding;
    }

    @Override
    public Object visitExpressionStep(ExpressionStep ast, Object o) {
        System.out.println("ExpressionStep");

        TypeDenoter binding = (TypeDenoter) ast.expression.visit(this, null);
        ast.step.visit(this, binding);

        return binding;
    }

    @Override
    public Object visitLimit(Limit ast, Object o) {
        System.out.println("Limit");

        TypeDenoter binding = (TypeDenoter) ast.limit.visit(this, null);

        return binding;
    }

    @Override
    public Object visitExpressionStepLimit(ExpressionStepLimit ast, Object o) {
        System.out.println("ExpressionStepLimit");

        TypeDenoter forClauseType = (TypeDenoter) ast.expression.visit(this, null);
        ast.limit.visit(this, null);
        TypeDenoter stepType = (TypeDenoter) ast.step.visit(this, forClauseType);

        return stepType;
    }

    @Override
    public Object visitExpressionLimit(ExpressionLimit ast, Object o) {
        System.out.println("ExpressionLimit");

        TypeDenoter exprType = (TypeDenoter) ast.expression.visit(this, null);
        TypeDenoter limitType = (TypeDenoter) ast.limit.visit(this, null);

        if (!(exprType.equals(limitType))) {
            reporter.reportError("expression type and LIMIT type differ", "", ast.limit.position);
        }

        return exprType;
    }

    @Override
    public Object visitSimpleForEnd(SimpleForEnd ast, Object o) {
        System.out.println("SimpleForEnd");
        return null;
    }

    @Override
    public Object visitForEndWithName(ForEndWithName ast, Object o) {
        System.out.println("ForEndWithName");

        ast.identifier.visit(this, null);

        return null;
    }

    @Override
    public Object visitSelectionStmt(SelectionStmt ast, Object o) {
        System.out.println("SelectionStmt");

        TypeDenoter binding = (TypeDenoter) ast.selectionHead.visit(this, null);
        ast.selectionBody.visit(this, binding);
        ast.selectionEnd.visit(this, null);

        return null;
    }

    @Override
    public Object visitSelectionHead(SelectionHead ast, Object o) {
        System.out.println("SelectionHead");

        TypeDenoter selectType = (TypeDenoter) ast.expression.visit(this, null);

        return selectType;
    }

    @Override
    public Object visitSelectBody(SelectBody ast, Object o) {
        System.out.println("SelectBody");

        ast.caseList.visit(this, o);

        return null;
    }

    @Override
    public Object visitSelectBodyWithEscape(SelectBodyWithEscape ast, Object o) {
        System.out.println("SelectBodyWithEscape");

        ast.caseList.visit(this, o);
        ast.escapeCase.visit(this, null);

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

        ast.identifier.visit(this, null);

        return null;
    }

    @Override
    public Object visitCaseSeq(CaseSeq ast, Object o) {
        System.out.println("CaseSeq");

        ast.caseSeq.visit(this, o);
        ast.aCase.visit(this, o);

        return null;
    }

    @Override
    public Object visitCaseHead(CaseHead ast, Object o) {
        System.out.println("CaseHead");

        ast.selector.visit(this, o);

        return null;
    }

    @Override
    public Object visitCaseList(CaseList ast, Object o) {
        System.out.println("CaseList");

        ast.caseHead.visit(this, o);

        indTable.openScope();

        ast.segment.visit(this, null);

        indTable.closeScope();

        return null;
    }

    @Override
    public Object visitSelector(Selector ast, Object o) {
        System.out.println("Selector");

        TypeDenoter selHeadType = (TypeDenoter) o;
        TypeDenoter caseType = (TypeDenoter) ast.expression.visit(this, null);

        if (!(selHeadType.equals(caseType))) {
            reporter.reportError("SELECT expression and CASE expression types differ", "", ast.expression.position);
        }

        return null;
    }

    @Override
    public Object visitEscapeCase(EscapeCase ast, Object o) {
        System.out.println("EscapeCase");

        indTable.openScope();

        ast.segment.visit(this, null);

        indTable.closeScope();

        return null;
    }

    @Override
    public Object visitRepeat(Repeat ast, Object o) {
        System.out.println("Repeat");

        ast.identifier.visit(this, null);

        return null;
    }

    @Override
    public Object visitRepent(Repent ast, Object o) {
        System.out.println("Repent");

        ast.identifier.visit(this, null);

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
        return null;
    }

    @Override
    public Object visitBinaryOperatorDefinition(BinaryOperatorDefinition ast, Object o) {
        return null;
    }

    @Override
    public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {
        return StdEnvironment.anyType;
    }

    @Override
    public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) {
        return StdEnvironment.errorType;
    }

    @Override
    public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {
        return StdEnvironment.booleanType;
    }

    @Override
    public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) {
        return StdEnvironment.integerType;
    }

    @Override
    public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) {
        return StdEnvironment.charType;
    }

    @Override
    public Object visitFloatTypeDenoter(FloatTypeDenoter ast, Object o) {
        return StdEnvironment.floatType;
    }

    @Override
    public Object visitRealTypeDenoter(RealTypeDenoter ast, Object o) {
        return StdEnvironment.realType;
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

    private Definition declareStdBoolean(String id, TypeDenoter constType) {

        IntegerExpression constExpr;
        ConstDefinition binding;

        // constExpr used only as a placeholder for constType
        constExpr = new IntegerExpression(dummyPos, null);
        constExpr.type = constType;

        binding = new ConstDefinition(dummyPos, new Identifier(dummyPos, id), constExpr);

        indTable.enter(id, binding);

        return binding;
    }

    private FunctionDefinition declareStdFunc(String id, FormalParameterSequence fps, TypeDenoter resultType) {

        FunctionDefinition binding;

        Identifier name = new Identifier(dummyPos, id);

        FunctionHead funcHead = new FunctionHead(dummyPos, name, fps, resultType);
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

        StdEnvironment.trueDecl = declareStdBoolean("TRUE", StdEnvironment.booleanType);
        StdEnvironment.falseDecl = declareStdBoolean("FALSE", StdEnvironment.booleanType);

        StdEnvironment.booleanDecl = declareStdType("BOOLEAN", StdEnvironment.booleanType);
        StdEnvironment.charDecl = declareStdType("STRING", StdEnvironment.charType);
        StdEnvironment.integerDecl = declareStdType("INTEGER", StdEnvironment.integerType);
        StdEnvironment.realDecl = declareStdType("REAL", StdEnvironment.realType);

        // to avoid clash with the binary '-' add arity
        StdEnvironment.notDecl = declareStdUnaryOp("NOT/1", StdEnvironment.booleanType, StdEnvironment.booleanType);
        StdEnvironment.negativeDecl = declareStdUnaryOp("-/1", StdEnvironment.floatType, StdEnvironment.floatType);

        StdEnvironment.andDecl = declareStdBinaryOp("AND", StdEnvironment.booleanType, StdEnvironment.booleanType, StdEnvironment.booleanType);
        StdEnvironment.orDecl = declareStdBinaryOp("OR", StdEnvironment.booleanType, StdEnvironment.booleanType, StdEnvironment.booleanType);
        StdEnvironment.moduloDecl = declareStdBinaryOp("MOD", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
        StdEnvironment.lessDecl = declareStdBinaryOp("<", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
        StdEnvironment.greaterDecl = declareStdBinaryOp(">", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
        StdEnvironment.notlessDecl = declareStdBinaryOp(">=", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
        StdEnvironment.notgreaterDecl = declareStdBinaryOp("<=", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
        StdEnvironment.addDecl = declareStdBinaryOp("+", StdEnvironment.floatType, StdEnvironment.floatType, StdEnvironment.floatType);
        StdEnvironment.subtractDecl = declareStdBinaryOp("-", StdEnvironment.floatType, StdEnvironment.floatType, StdEnvironment.floatType);
        StdEnvironment.multiplyDecl = declareStdBinaryOp("*", StdEnvironment.floatType, StdEnvironment.floatType, StdEnvironment.floatType);
        StdEnvironment.divideDecl = declareStdBinaryOp("/", StdEnvironment.floatType, StdEnvironment.floatType, StdEnvironment.floatType);

        StdEnvironment.concatDecl = declareStdBinaryOp("||", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.charType);
        StdEnvironment.equalDecl = declareStdBinaryOp("=", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.booleanType);
        StdEnvironment.unequalDecl = declareStdBinaryOp("<>", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.booleanType);

        StdEnvironment.substrDecl = declareStdFunc("SUBSTR",
                new FormalParameterList(dummyPos, new FormalParameterByValue(dummyPos, dummyI, StdEnvironment.charType),
                        new FormalParameterList(dummyPos, new FormalParameterByValue(dummyPos, dummyI, StdEnvironment.charType),
                        new SingleFormalParameterSequence(dummyPos, new FormalParameterByValue(dummyPos, dummyI, StdEnvironment.charType)))), StdEnvironment.charType);

        StdEnvironment.lengthDecl = declareStdFunc("LENGTH", new SingleFormalParameterSequence(dummyPos,
                new FormalParameterByValue(dummyPos, dummyI, StdEnvironment.integerType)), StdEnvironment.charType);

        StdEnvironment.floatDecl = declareStdFunc("FLOAT", new SingleFormalParameterSequence(dummyPos,
                new FormalParameterByValue(dummyPos, dummyI, StdEnvironment.realType)), StdEnvironment.realType);

        StdEnvironment.fixDecl = declareStdFunc("FIX", new SingleFormalParameterSequence(dummyPos,
                new FormalParameterByValue(dummyPos, dummyI, StdEnvironment.realType)), StdEnvironment.integerType);
    }
}
