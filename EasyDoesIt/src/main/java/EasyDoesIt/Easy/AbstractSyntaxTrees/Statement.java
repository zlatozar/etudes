package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class Statement extends AST {

    public TypeDenoter type;

    public Statement(SourcePosition srcPos) {
        super(srcPos);

        this.type = null;
    }
}
