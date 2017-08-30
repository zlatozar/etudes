package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class CompoundStmt extends Statement {

    public Segment segment;
    public CompoundEnd compoundEnd;

    public CompoundStmt(SourcePosition srcPos, Segment segment, CompoundEnd compoundEnd) {
        super(srcPos);
        this.segment = segment;
        this.compoundEnd = compoundEnd;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCompoundStmt(this, o);
    }
}
