package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class Expression extends AST {

    public TypeDenoter type;

    public Expression(SourcePosition srcPos) {
        super(srcPos);

        this.type = null;
    }
}
