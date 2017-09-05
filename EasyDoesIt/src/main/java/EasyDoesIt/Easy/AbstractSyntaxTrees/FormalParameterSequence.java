package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class FormalParameterSequence extends AST {

    public FormalParameterSequence(SourcePosition srcPos) {
        super(srcPos);
    }

    @Override
    public abstract boolean equals(Object fpsAST);
}
