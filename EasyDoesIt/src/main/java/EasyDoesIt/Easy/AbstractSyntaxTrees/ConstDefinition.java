package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ConstDefinition extends Definition {

    // const name
    public Identifier I;

    // const value
    public Expression E;

    public ConstDefinition(SourcePosition thePosition, Identifier iAST, Expression eAST) {
        super(thePosition);
        I = iAST;
        E = eAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitConstDefinition(this, o);
    }
}
