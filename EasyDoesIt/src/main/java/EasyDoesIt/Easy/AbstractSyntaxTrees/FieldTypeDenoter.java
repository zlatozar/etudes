package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class FieldTypeDenoter extends AST {

    public FieldTypeDenoter(SourcePosition srcPos) {
        super(srcPos);
    }

    @Override
    public abstract boolean equals(Object obj);
}
