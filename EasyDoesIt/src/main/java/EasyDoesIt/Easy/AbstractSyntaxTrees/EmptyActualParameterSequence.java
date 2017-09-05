package EasyDoesIt.Easy.AbstractSyntaxTrees;


import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class EmptyActualParameterSequence extends ActualParameterSequence {

    public EmptyActualParameterSequence(SourcePosition thePosition) {
        super(thePosition);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitEmptyActualParameterSequence(this, o);
    }
}
