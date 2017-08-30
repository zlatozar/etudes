package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class InternalProcedure extends Definition {

    public Definition blockCode;

    public InternalProcedure(SourcePosition srcPos, Definition blockCode) {
        super(srcPos);
        this.blockCode = blockCode;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitInternalProcedure(this, o);
    }
}
