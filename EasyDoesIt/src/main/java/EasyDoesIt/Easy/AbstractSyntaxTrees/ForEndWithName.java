package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ForEndWithName extends ForEnd {

    public Identifier identifier;

    public ForEndWithName(SourcePosition srcPos, Identifier identifier) {
        super(srcPos);
        this.identifier = identifier;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitForEndWithName(this, o);
    }
}
