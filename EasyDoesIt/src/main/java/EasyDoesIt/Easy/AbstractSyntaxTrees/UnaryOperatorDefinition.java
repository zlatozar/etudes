package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class UnaryOperatorDefinition extends Definition {

    public Operator O;

    public TypeDenoter ARG;
    public TypeDenoter RES;

    public UnaryOperatorDefinition(SourcePosition thePosition, Operator oAST, TypeDenoter argAST, TypeDenoter resultAST) {
        super(thePosition);

        this.O = oAST;
        this.ARG = argAST;
        this.RES = resultAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitUnaryOperatorDefinition(this, o);
    }
}
