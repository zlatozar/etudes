package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SingleFieldTypeDenoter extends FieldTypeDenoter {

    public Identifier I;
    public TypeDenoter typeDenoter;

    public SingleFieldTypeDenoter(SourcePosition srcPos, Identifier i, TypeDenoter typeDenoter) {
        super(srcPos);
        I = i;
        this.typeDenoter = typeDenoter;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSingleFieldTypeDenoter(this, o);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof ErrorTypeDenoter) {
            return true;

        } else if (obj != null && obj instanceof SingleFieldTypeDenoter) {
            return
                    this.I.spelling.compareTo(((SingleFieldTypeDenoter) obj).I.spelling) == 0
                            && this.typeDenoter.equals(((SingleFieldTypeDenoter) obj).typeDenoter);

        } else {
            return false;
        }
    }
}
