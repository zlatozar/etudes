package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class EmptyStatement extends Statement {

    public EmptyStatement(SourcePosition srcPos) {
        super(srcPos);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitEmptyStatement(this, o);
    }
}
