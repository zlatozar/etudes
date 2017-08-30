package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class OutputList extends AST {

    public Expression expr;

    public OutputList(SourcePosition srcPos, Expression expr) {
        super(srcPos);
        this.expr = expr;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitOutputList(this, o);
    }
}
