package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class StructureType extends TypeDenoter {

    public Field fieldDenoter;

    public StructureType(SourcePosition srcPos, Field fieldDenoter) {
        super(srcPos);
        this.fieldDenoter = fieldDenoter;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitStructureType(this, o);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ErrorTypeDenoter) {
            return true;

        } else if (obj != null && obj instanceof StructureType) {
            return this.fieldDenoter.equals(((StructureType) obj).fieldDenoter);

        } else {
            return false;
        }
    }
}
