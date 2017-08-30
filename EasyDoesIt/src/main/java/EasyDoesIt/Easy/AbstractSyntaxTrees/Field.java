package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class Field extends AST {

    public Field(SourcePosition srcPos) {
        super(srcPos);
    }

    @Override
    public abstract boolean equals(Object obj);
}
