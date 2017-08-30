package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ReturnWithExpression extends Statement {

    public Expression expression;

    public ReturnWithExpression(SourcePosition srcPos, Expression expression) {
        super(srcPos);
        this.expression = expression;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitReturnWithExpression(this, o);
    }
}
