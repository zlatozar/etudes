package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Selector extends AST {

    public Expression expression;

    public Selector(SourcePosition srcPos, Expression expression) {
        super(srcPos);
        this.expression = expression;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSelector(this, o);
    }
}
