package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class RealExpression extends Expression {

    public RealLiteral RL;

    public RealExpression(SourcePosition thePosition, RealLiteral rlAST) {
        super(thePosition);

        this.RL = rlAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitRealExpression(this, o);
    }

}
