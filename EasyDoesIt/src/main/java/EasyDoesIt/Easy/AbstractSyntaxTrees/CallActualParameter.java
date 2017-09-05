package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class CallActualParameter extends ActualParameter {

    public Expression expression;

    public CallActualParameter(SourcePosition thePosition, Expression iAST) {
        super(thePosition);
        this.expression = iAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCallActualParameter(this, o);
    }

}
