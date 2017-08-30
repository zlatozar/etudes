package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SingleArrayBounds extends ArrayBounds {

    public Expression expression;

    public SingleArrayBounds(SourcePosition srcPos, Expression expression) {
        super(srcPos);
        this.expression = expression;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSingleArrayBounds(this, o);
    }
}
