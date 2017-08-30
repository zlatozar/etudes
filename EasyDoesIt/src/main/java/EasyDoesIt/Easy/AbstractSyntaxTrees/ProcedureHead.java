package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ProcedureHead extends AST {

    public BlockCodeName blockCodeName;

    public ProcedureHead(SourcePosition srcPos, BlockCodeName blockCodeName) {
        super(srcPos);
        this.blockCodeName = blockCodeName;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitProcedureHead(this, o);
    }
}
