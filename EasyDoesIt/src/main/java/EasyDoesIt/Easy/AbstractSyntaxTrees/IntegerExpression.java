package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class IntegerExpression extends Expression {

    public IntegerLiteral IL;

    public IntegerExpression(SourcePosition thePosition, IntegerLiteral ilAST) {
        super(thePosition);

        this.IL = ilAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitIntegerExpression(this, o);
    }
}
