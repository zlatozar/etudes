package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class Definition extends AST {

    public boolean duplicated;

    public Definition(SourcePosition srcPos) {
        super(srcPos);
        this.duplicated = false;
    }
}
