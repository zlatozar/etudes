package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class Vname extends AST {

    public boolean variable;
    public boolean indexed;

    public int offset;
    public TypeDenoter type;

    public Vname(SourcePosition srcPos) {
        super(srcPos);

        this.variable = false;
        this.type = null;
    }

}
