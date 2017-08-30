package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class AST {

    public SourcePosition position;

    public AST(SourcePosition srcPos) {
        this.position = srcPos;
    }

    public SourcePosition getPosition() {
        return position;
    }

    public abstract Object visit(Visitor v, Object o);
}
