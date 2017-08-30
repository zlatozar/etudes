package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ProcedureName extends BlockCodeName {

    public Identifier I;

    public ProcedureName(SourcePosition srcPos, Identifier i) {
        super(srcPos);
        I = i;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitProcedureName(this, o);
    }
}
