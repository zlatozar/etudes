package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ArrayTypeDenoter extends TypeDenoter {

    public ArrayBounds arrayBounds;
    public TypeDenoter type;

    public ArrayTypeDenoter(SourcePosition srcPos, ArrayBounds arrayBounds, TypeDenoter type) {
        super(srcPos);
        this.arrayBounds = arrayBounds;
        this.type = type;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitArrayTypeDenoter(this, o);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof ErrorTypeDenoter) {
            return true;

        } else if (obj != null && obj instanceof ArrayTypeDenoter) {
            return this.type.equals(((ArrayTypeDenoter) obj).type);

        } else {
            return false;
        }
    }
}
