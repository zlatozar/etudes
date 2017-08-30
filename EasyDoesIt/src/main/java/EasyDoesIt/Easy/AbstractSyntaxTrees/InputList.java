package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class InputList extends Vname {

    public Vname varList;

    public InputList(SourcePosition srcPos, Vname varList) {
        super(srcPos);
        this.varList = varList;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitInputList(this, o);
    }
}
