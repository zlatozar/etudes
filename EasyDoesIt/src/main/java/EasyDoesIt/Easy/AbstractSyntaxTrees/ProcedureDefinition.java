package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ProcedureDefinition extends Definition {

    public ProcedureHead procHead;
    public Segment segment;
    public ProcedureEnd procEnd;

    public ProcedureDefinition(SourcePosition srcPos, ProcedureHead procHead, Segment segment, ProcedureEnd procEnd) {
        super(srcPos);
        this.procHead = procHead;
        this.segment = segment;
        this.procEnd = procEnd;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitProcedureDefinition(this, o);
    }
}
