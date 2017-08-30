package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SimpleVname extends Vname {

    public Identifier I;

    public SimpleVname(SourcePosition srcPos, Identifier iAST) {
        super(srcPos);

        this.I = iAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSimpleVname(this, o);
    }
}
