package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SubscriptVname extends Vname {

    public Expression E;
    public Vname V;

    public SubscriptVname(SourcePosition srcPos, Vname vAST, Expression eAST) {
        super(srcPos);

        this.V = vAST;
        this.E = eAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSubscriptVname(this, o);
    }
}
