package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ConstantExpression extends Expression {

    public Identifier name;

    public ConstantExpression(SourcePosition srcPos, Identifier name) {
        super(srcPos);
        this.name = name;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitConstantExpression(this, o);
    }
}
