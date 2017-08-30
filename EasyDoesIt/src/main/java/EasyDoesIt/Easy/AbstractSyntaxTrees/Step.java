package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Step extends AST {

    public Expression step;

    public Step(SourcePosition srcPos, Expression step) {
        super(srcPos);
        this.step = step;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitStep(this, o);
    }
}
