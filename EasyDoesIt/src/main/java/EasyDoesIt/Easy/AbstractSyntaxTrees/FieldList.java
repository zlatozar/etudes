package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class FieldList extends Field {

    public Field fieldSeq;
    public Field field;

    public FieldList(SourcePosition srcPos, Field fieldSeq, Field field) {
        super(srcPos);
        this.fieldSeq = fieldSeq;
        this.field = field;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitFieldList(this, o);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof ErrorTypeDenoter) {
            return true;

        } else if (obj != null && obj instanceof FieldList) {
            return this.fieldSeq.equals(((FieldList) obj).fieldSeq) && this.field.equals(((FieldList) obj).field);

        } else {
            return false;
        }
    }
}
