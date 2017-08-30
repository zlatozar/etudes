package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ForHead extends AST {

    public Vname var;
    public LoopControl loopControl;

    public ForHead(SourcePosition srcPos, Vname var, LoopControl loopControl) {
        super(srcPos);
        this.var = var;
        this.loopControl = loopControl;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitForHead(this, o);
    }
}
