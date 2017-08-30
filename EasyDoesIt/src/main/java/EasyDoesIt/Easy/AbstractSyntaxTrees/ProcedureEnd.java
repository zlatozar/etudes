package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ProcedureEnd extends AST {

    public Identifier procName;

    public ProcedureEnd(SourcePosition srcPos, Identifier procName) {
        super(srcPos);
        this.procName = procName;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitProcedureEnd(this, o);
    }
}
