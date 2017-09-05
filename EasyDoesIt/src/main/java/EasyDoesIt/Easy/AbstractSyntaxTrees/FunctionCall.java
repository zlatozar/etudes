package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class FunctionCall extends Expression {

    public Identifier I;
    public ActualParameterSequence APS;

    public FunctionCall(SourcePosition srcPos, Identifier iAST, ActualParameterSequence APS) {
        super(srcPos);

        this.I = iAST;
        this.APS = APS;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitFunctionCall(this, o);
    }
}
