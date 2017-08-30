package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class FunctionCall extends Expression {

    public Identifier I;
    public Expression E;

    public FunctionCall(SourcePosition srcPos, Identifier iAST, Expression E) {
        super(srcPos);

        this.I = iAST;
        this.E = E;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitFunctionCall(this, o);
    }
}
