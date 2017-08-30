package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ExpressionStep extends StepExpression {

    public Expression expression;
    public Step step;

    public ExpressionStep(SourcePosition srcPos, Expression expression, Step step) {
        super(srcPos);
        this.expression = expression;
        this.step = step;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitExpressionStep(this, o);
    }
}
