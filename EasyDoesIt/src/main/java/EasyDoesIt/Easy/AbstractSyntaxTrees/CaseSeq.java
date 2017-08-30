package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class CaseSeq extends Case {

    public Case caseSeq;
    public Case aCase;

    public CaseSeq(SourcePosition srcPos, Case caseSeq, Case aCase) {
        super(srcPos);
        this.caseSeq = caseSeq;
        this.aCase = aCase;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCaseSeq(this, o);
    }
}
