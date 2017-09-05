package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class EmptyFormalParameterSequence extends FormalParameterSequence {

    public EmptyFormalParameterSequence(SourcePosition thePosition) {
        super(thePosition);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitEmptyFormalParameterSequence(this, o);
    }

    @Override
    public boolean equals(Object fpsAST) {
        return (fpsAST instanceof EmptyFormalParameterSequence);
    }
}
