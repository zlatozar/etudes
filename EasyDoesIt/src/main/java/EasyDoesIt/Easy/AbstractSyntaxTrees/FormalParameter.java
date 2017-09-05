package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class FormalParameter extends Definition {

    public FormalParameter(SourcePosition srcPos) {
        super(srcPos);
    }

    @Override
    public abstract boolean equals(Object fpAST);
}
