package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ExpressionStepLimit extends StepExpression {

    public Expression expression;
    public Step step;
    public Limit limit;

    public ExpressionStepLimit(SourcePosition srcPos, Expression expression, Step step, Limit limit) {
        super(srcPos);
        this.expression = expression;
        this.step = step;
        this.limit = limit;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitExpressionStepLimit(this, o);
    }
}
