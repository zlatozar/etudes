package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ArrayType extends TypeDenoter {

    public ArrayBounds arrayBounds;
    public TypeDenoter type;

    public ArrayType(SourcePosition srcPos, ArrayBounds arrayBounds, TypeDenoter type) {
        super(srcPos);
        this.arrayBounds = arrayBounds;
        this.type = type;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitArrayType(this, o);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof ErrorTypeDenoter) {
            return true;

        } else if (obj != null && obj instanceof ArrayType) {
            return this.type.equals(((ArrayType) obj).type);

        } else {
            return false;
        }
    }
}
