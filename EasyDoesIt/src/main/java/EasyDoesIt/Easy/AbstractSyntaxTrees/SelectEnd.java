package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SelectEnd extends SelectionEnd {

    public SelectEnd(SourcePosition srcPos) {
        super(srcPos);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSelectionEnd(this, o);
    }
}
