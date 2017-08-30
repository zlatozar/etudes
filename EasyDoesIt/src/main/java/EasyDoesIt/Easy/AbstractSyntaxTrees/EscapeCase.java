package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class EscapeCase extends AST {

    public Segment segment;

    public EscapeCase(SourcePosition srcPos, Segment segment) {
        super(srcPos);
        this.segment = segment;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitEscapeCase(this, o);
    }
}
