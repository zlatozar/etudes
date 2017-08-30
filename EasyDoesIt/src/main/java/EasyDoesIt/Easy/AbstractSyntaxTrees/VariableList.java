package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class VariableList extends Vname {

    public Vname vnameSeq;
    public Vname vname;

    public VariableList(SourcePosition srcPos, Vname vnameSeq, Vname vname) {
        super(srcPos);
        this.vnameSeq = vnameSeq;
        this.vname = vname;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitVariableList(this, o);
    }
}
