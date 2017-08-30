package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class DotVname extends Vname {

    public Identifier I;
    public Vname V;

    public DotVname(SourcePosition srcPos, Vname vAST, Identifier iAST) {
        super(srcPos);

        this.V = vAST;
        this.I = iAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitDotVname(this, o);
    }
}
