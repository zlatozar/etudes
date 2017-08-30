package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class TrueBranch extends AST {

    public Segment segment;

    public TrueBranch(SourcePosition srcPos, Segment segment) {
        super(srcPos);
        this.segment = segment;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitTrueBranch(this, o);
    }
}
