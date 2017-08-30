package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class EmptyDefinition extends Definition {

    public EmptyDefinition(SourcePosition srcPos) {
        super(srcPos);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitEmptyDefinition(this, o);
    }
}
