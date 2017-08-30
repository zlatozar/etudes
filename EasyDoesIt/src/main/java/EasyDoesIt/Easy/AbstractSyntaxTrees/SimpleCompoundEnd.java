package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SimpleCompoundEnd extends CompoundEnd {

    public SimpleCompoundEnd(SourcePosition srcPos) {
        super(srcPos);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSimpleCompoundEnd(this, o);
    }
}
