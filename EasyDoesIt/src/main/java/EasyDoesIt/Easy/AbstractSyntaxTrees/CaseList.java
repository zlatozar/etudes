package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class CaseList extends Case {

    public CaseHead caseHead;
    public Segment segment;

    public CaseList(SourcePosition srcPos, CaseHead caseHead, Segment segment) {
        super(srcPos);
        this.caseHead = caseHead;
        this.segment = segment;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCaseList(this, o);
    }
}
