package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class CompoundEndWithName extends CompoundEnd {

    public Identifier name;

    public CompoundEndWithName(SourcePosition srcPos, Identifier name) {
        super(srcPos);
        this.name = name;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCompoundEndWithName(this, o);
    }
}
