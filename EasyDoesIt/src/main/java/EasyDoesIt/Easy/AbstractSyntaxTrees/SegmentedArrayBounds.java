package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SegmentedArrayBounds extends ArrayBounds {

    public Expression from;
    public Expression to;

    public SegmentedArrayBounds(SourcePosition srcPos, Expression from, Expression to) {
        super(srcPos);
        this.from = from;
        this.to = to;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSegmentedArrayBounds(this, o);
    }
}
