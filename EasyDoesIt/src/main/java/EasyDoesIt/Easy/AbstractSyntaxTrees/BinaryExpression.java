package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class BinaryExpression extends Expression {

    // left and right operands
    public Expression E1;
    public Expression E2;

    // binary operator symbol
    public Operator O;

    public BinaryExpression(SourcePosition thePosition, Expression e1AST, Operator oAST, Expression e2AST) {
        super(thePosition);

        this.O = oAST;
        this.E1 = e1AST;
        this.E2 = e2AST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitBinaryExpression(this, o);
    }
}
