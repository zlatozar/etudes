package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class MultipleFormalParameterSequence extends FormalParameterSequence {

    // first
    public FormalParameter FP;

    // rest
    public FormalParameterSequence FPS;

    public MultipleFormalParameterSequence(SourcePosition thePosition, FormalParameter fpAST, FormalParameterSequence fpsAST) {
        super(thePosition);

        this.FP = fpAST;
        this.FPS = fpsAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitMultipleFormalParameterSequence(this, o);
    }

    @Override
    public boolean equals(Object fpsAST) {

        if (fpsAST instanceof MultipleFormalParameterSequence) {

            MultipleFormalParameterSequence mfpsAST = (MultipleFormalParameterSequence) fpsAST;

            return FP.equals(mfpsAST.FP) && FPS.equals(mfpsAST.FPS);

        } else {
            return false;
        }
    }
}
