package EasyDoesIt.Easy.AbstractSyntaxTrees;

public interface Visitor {

//_____________________________________________________________________________
//                                                          Value or variables

    Object visitIdentifier(Identifier ast, Object o);

    Object visitDotVname(DotVname ast, Object o);
    Object visitSimpleVname(SimpleVname ast, Object o);
    Object visitSubscriptVname(SubscriptVname ast, Object o);

    Object visitVnameExpression(VnameExpression ast, Object o);
    Object visitConstantExpression(ConstantExpression ast, Object o);


//_____________________________________________________________________________
//                                                                  Expression

    Object visitBinaryExpression(BinaryExpression ast, Object o);
    Object visitOperator(Operator ast, Object o);
    Object visitCharacterLiteral(CharacterLiteral ast, Object o);
    Object visitIntegerLiteral(IntegerLiteral ast, Object o);
    Object visitRealLiteral(RealLiteral ast, Object o);
    Object visitUnaryExpression(UnaryExpression ast, Object o);
    Object visitIntegerExpression(IntegerExpression ast, Object o);
    Object visitRealExpression(RealExpression ast, Object o);
    Object visitFunctionCall(FunctionCall ast, Object o);
    Object visitCharacterExpression(CharacterExpression ast, Object o);


//_____________________________________________________________________________
//                                                                     Program

    Object visitProgram(Program ast, Object o);
    Object visitProgramBody(ProgramBody ast, Object o);
    Object visitSegment(Segment ast, Object o);

//_____________________________________________________________________________
//                                                                 Definitions

    Object visitConstDefinition(ConstDefinition ast, Object o);

    Object visitDefinitionSeq(DefinitionSeq ast, Object o);
    Object visitEmptyDefinition(EmptyDefinition ast, Object o);

    Object visitTypeDefinition(TypeDefinition ast, Object o);
    Object visitIdentifierTypeDenoter(IdentifierTypeDenoter ast, Object o);
    Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o);
    Object visitSingleArrayBounds(SingleArrayBounds ast, Object o);
    Object visitSegmentedArrayBounds(SegmentedArrayBounds ast, Object o);
    Object visitStructuredTypeDenoter(StructuredTypeDenoter ast, Object o);
    Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o);
    Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o);

    Object visitDeclaration(Declaration ast, Object o);
    Object visitSingleDeclaredName(SingleDeclaredName ast, Object o);
    Object visitMultipleDeclaredNames(MultipleDeclaredNames ast, Object o);
    Object visitEmptyDeclaredName(EmptyDeclaredName ast, Object o);

    Object visitInternalProcedure(InternalProcedure ast, Object o);
    Object visitProcedureDefinition(ProcedureDefinition ast, Object o);
    Object visitFunctionDefinition(FunctionDefinition ast, Object o);
    Object visitProcedureHead(ProcedureHead ast, Object o);
    Object visitProcedureEnd(ProcedureEnd ast, Object o);
    Object visitCallActualParameter(CallActualParameter ast, Object o);
    Object visitFormalParameterByValue(FormalParameterByValue ast, Object o);
    Object visitFormalParameterByName(FormalParameterByName ast, Object o);
    Object visitFunctionHead(FunctionHead ast, Object o);
    Object visitFunctionEnd(FunctionEnd ast, Object o);

    Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o);
    Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o);
    Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o);
    Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o);
    Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object o);
    Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o);

//_____________________________________________________________________________
//                                                                  Statements

    Object visitEmptyStatement(EmptyStatement ast, Object o);
    Object visitNullStmt(NullStmt ast, Object o);
    Object visitStatementSeq(StatementSeq ast, Object o);

    Object visitVariableList(VariableList ast, Object o);
    Object visitAssignmentStmt(AssignmentStmt ast, Object o);

    Object visitProcedureCallStmt(ProcedureCallStmt ast, Object o);
    Object visitCall(Call ast, Object o);
    Object visitExpressionList(ExpressionList ast, Object o);
    Object visitCallWithParams(CallWithParams ast, Object o);

    Object visitReturn(Return ast, Object o);
    Object visitReturnWithExpression(ReturnWithExpression ast, Object o);

    Object visitExitStmt(ExitStmt ast, Object o);

    Object visitIfStmt(IfStmt ast, Object o);
    Object visitIfElseStmt(IfElseStmt ast, Object o);

    Object visitConditionalClause(ConditionalClause ast, Object o);
    Object visitTrueBranch(TrueBranch ast, Object o);
    Object visitFalseBranch(FalseBranch ast, Object o);

    Object visitSimpleCompoundEnd(SimpleCompoundEnd ast, Object o);
    Object visitCompoundEndWithName(CompoundEndWithName ast, Object o);
    Object visitCompoundStmt(CompoundStmt ast, Object o);

    Object visitForLoopStmt(ForLoopStmt ast, Object o);
    Object visitForHead(ForHead ast, Object o);
    Object visitWhile(While ast, Object o);
    Object visitStepperWhile(StepperWhile ast, Object o);
    Object visitStepper(Stepper ast, Object o);
    Object visitStep(Step ast, Object o);
    Object visitExpressionStep(ExpressionStep ast, Object o);
    Object visitLimit(Limit ast, Object o);
    Object visitExpressionStepLimit(ExpressionStepLimit ast, Object o);
    Object visitExpressionLimit(ExpressionLimit ast, Object o);
    Object visitSimpleForEnd(SimpleForEnd ast, Object o);
    Object visitForEndWithName(ForEndWithName ast, Object o);

    Object visitSelectionStmt(SelectionStmt ast, Object o);
    Object visitSelectionHead(SelectionHead ast, Object o);
    Object visitSelectBody(SelectBody ast, Object o);
    Object visitSelectBodyWithEscape(SelectBodyWithEscape ast, Object o);
    Object visitSelectionEnd(SelectEnd ast, Object o);
    Object visitSelectEndWithName(SelectEndWithName ast, Object o);
    Object visitCaseSeq(CaseSeq ast, Object o);
    Object visitCaseHead(CaseHead ast, Object o);
    Object visitCaseList(CaseList ast, Object o);
    Object visitSelector(Selector ast, Object o);
    Object visitEscapeCase(EscapeCase ast, Object o);

    Object visitRepeat(Repeat ast, Object o);

    Object visitRepent(Repent ast, Object o);

    Object visitInput(Input ast, Object o);
    Object visitInputList(InputList ast, Object o);

    Object visitOutput(Output ast, Object o);
    Object visitOutputList(OutputList ast, Object o);

//_____________________________________________________________________________
//                                                        Standard Environment

    Object visitUnaryOperatorDefinition(UnaryOperatorDefinition ast, Object o);
    Object visitBinaryOperatorDefinition(BinaryOperatorDefinition ast, Object o);
    Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o);
    Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o);
    Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o);
    Object visitIntTypeDenoter(IntTypeDenoter ast, Object o);
    Object visitCharTypeDenoter(CharTypeDenoter ast, Object o);
    Object visitFloatTypeDenoter(FloatTypeDenoter ast, Object o);
    Object visitRealTypeDenoter(RealTypeDenoter ast, Object o);
}
