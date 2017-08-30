package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ExpressionLimit extends StepExpression {

    public Expression expression;
    public Limit limit;

    public ExpressionLimit(SourcePosition srcPos, Expression expression, Limit limit) {
        super(srcPos);
        this.expression = expression;
        this.limit = limit;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitExpressionLimit(this, o);
    }
}
