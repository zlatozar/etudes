package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class BinaryOperatorDefinition extends Definition {

    public Operator O;

    public TypeDenoter ARG1;
    public TypeDenoter ARG2;

    // result type
    public TypeDenoter RES;

    public BinaryOperatorDefinition(SourcePosition thePosition, Operator oAST, TypeDenoter arg1AST,
                                    TypeDenoter arg2AST, TypeDenoter resultAST) {
        super(thePosition);

        this.O = oAST;
        this.ARG1 = arg1AST;
        this.ARG2 = arg2AST;
        this.RES = resultAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitBinaryOperatorDefinition(this, o);
    }
}
