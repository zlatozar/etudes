package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class FalseBranch extends AST {

    public Segment segment;

    public FalseBranch(SourcePosition srcPos, Segment segment) {
        super(srcPos);
        this.segment = segment;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitFalseBranch(this, o);
    }
}
