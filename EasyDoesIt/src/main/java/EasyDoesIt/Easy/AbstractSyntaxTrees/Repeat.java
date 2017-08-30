package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Repeat extends Statement {

    public Identifier identifier;

    public Repeat(SourcePosition srcPos, Identifier identifier) {
        super(srcPos);
        this.identifier = identifier;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitRepeat(this, o);
    }
}
