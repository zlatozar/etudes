package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class While extends AST {

    public Expression expression;

    public While(SourcePosition srcPos, Expression expression) {
        super(srcPos);
        this.expression = expression;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitWhile(this, o);
    }
}
