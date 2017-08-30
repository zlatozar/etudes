package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class ArrayBounds extends AST {

    // the number of elements is a characteristic of the array
    public int elemCount;

    public ArrayBounds(SourcePosition srcPos) {
        super(srcPos);
        elemCount = 0;
    }
}
