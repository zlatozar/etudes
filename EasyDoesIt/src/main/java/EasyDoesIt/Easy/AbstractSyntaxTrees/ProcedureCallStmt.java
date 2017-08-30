package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ProcedureCallStmt extends Statement {

    public ProcedureRef prcRef;

    public ProcedureCallStmt(SourcePosition srcPos, ProcedureRef prcRef) {
        super(srcPos);
        this.prcRef = prcRef;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitProcedureCallStmt(this, o);
    }
}
