package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class TypeDenoter extends Definition {

    public TypeDenoter(SourcePosition srcPos) {
        super(srcPos);
    }

    @Override
    public abstract boolean equals(Object obj);

}
