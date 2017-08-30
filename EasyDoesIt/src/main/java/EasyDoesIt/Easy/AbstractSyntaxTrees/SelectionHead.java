package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SelectionHead extends AST {

    public Expression expression;

    public SelectionHead(SourcePosition srcPos, Expression expression) {
        super(srcPos);
        this.expression = expression;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSelectionHead(this, o);
    }
}
