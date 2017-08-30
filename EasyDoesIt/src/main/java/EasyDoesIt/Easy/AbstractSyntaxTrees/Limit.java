package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Limit extends AST {

    public Expression limit;

    public Limit(SourcePosition srcPos, Expression limit) {
        super(srcPos);
        this.limit = limit;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitLimit(this, o);
    }
}
