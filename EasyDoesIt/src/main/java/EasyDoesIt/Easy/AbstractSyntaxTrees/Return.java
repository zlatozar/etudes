package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Return extends Statement {

    public Return(SourcePosition srcPos) {
        super(srcPos);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitReturn(this, o);
    }
}
