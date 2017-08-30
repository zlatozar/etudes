package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ForLoopStmt extends Statement {

    public ForHead forHead;
    public Segment segment;
    public ForEnd forEnd;

    public ForLoopStmt(SourcePosition srcPos, ForHead forHead, Segment segment, ForEnd forEnd) {
        super(srcPos);
        this.forHead = forHead;
        this.segment = segment;
        this.forEnd = forEnd;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitForLoopStmt(this, o);
    }
}
