package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SingleActualParameterSequence extends ActualParameterSequence {

    public ActualParameter AP;

    public SingleActualParameterSequence(SourcePosition thePosition, ActualParameter apAST) {
        super(thePosition);

        this.AP = apAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSingleActualParameterSequence(this, o);
    }
}
