package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class MultipleActualParameterSequence extends ActualParameterSequence {

    // first
    public ActualParameter AP;

    // rest
    public ActualParameterSequence APS;

    public MultipleActualParameterSequence(SourcePosition thePosition, ActualParameter apAST,
                                           ActualParameterSequence apsAST) {

        super(thePosition);

        this.AP = apAST;
        this.APS = apsAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitMultipleActualParameterSequence(this, o);
    }
}
