package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ExpressionList extends Expression {

    public Expression exprSeq;
    public Expression expr;

    public ExpressionList(SourcePosition srcPos, Expression exprSeq, Expression expr) {
        super(srcPos);
        this.exprSeq = exprSeq;
        this.expr = expr;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitExpressionList(this, o);
    }
}
