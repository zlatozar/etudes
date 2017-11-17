package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class StructuredTypeDenoter extends TypeDenoter {

    public FieldTypeDenoter fieldTypeDenoterDenoter;

    public StructuredTypeDenoter(SourcePosition srcPos, FieldTypeDenoter fieldTypeDenoterDenoter) {
        super(srcPos);
        this.fieldTypeDenoterDenoter = fieldTypeDenoterDenoter;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitStructuredTypeDenoter(this, o);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ErrorTypeDenoter) {
            return true;

        } else if (obj != null && obj instanceof StructuredTypeDenoter) {
            return this.fieldTypeDenoterDenoter.equals(((StructuredTypeDenoter) obj).fieldTypeDenoterDenoter);

        } else {
            return false;
        }
    }
}
