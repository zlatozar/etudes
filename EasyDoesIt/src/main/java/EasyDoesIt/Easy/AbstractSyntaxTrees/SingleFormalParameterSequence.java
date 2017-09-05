package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SingleFormalParameterSequence extends FormalParameterSequence {

    public FormalParameter FP;

    public SingleFormalParameterSequence(SourcePosition thePosition, FormalParameter fpAST) {
        super(thePosition);

        this.FP = fpAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSingleFormalParameterSequence(this, o);
    }

    @Override
    public boolean equals(Object fpsAST) {

        if (fpsAST instanceof SingleFormalParameterSequence) {
            SingleFormalParameterSequence sfpsAST = (SingleFormalParameterSequence) fpsAST;

            return FP.equals(sfpsAST.FP);

        } else {
            return false;
        }
    }
}
