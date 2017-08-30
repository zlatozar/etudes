package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Repent extends Statement {

    public Identifier identifier;

    public Repent(SourcePosition srcPos, Identifier identifier) {
        super(srcPos);
        this.identifier = identifier;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitRepent(this, o);
    }
}
