package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class Variable extends AST {

    public Variable(SourcePosition srcPos) {
        super(srcPos);
    }
}
