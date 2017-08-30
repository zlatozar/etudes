package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class UnaryExpression extends Expression {

    public Expression E;

    // unary operator symbol
    public Operator O;

    public UnaryExpression(SourcePosition thePosition, Operator oAST, Expression eAST) {
        super(thePosition);

        this.O = oAST;
        this.E = eAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitUnaryExpression(this, o);
    }
}
