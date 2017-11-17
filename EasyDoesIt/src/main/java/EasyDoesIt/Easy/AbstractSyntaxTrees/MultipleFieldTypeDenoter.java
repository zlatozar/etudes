package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class MultipleFieldTypeDenoter extends FieldTypeDenoter {

    // first (I, TD)
    public Identifier I;
    public TypeDenoter typeDenoter;

    // rest (I, TD)
    public FieldTypeDenoter fieldTypeDenoter;

    public MultipleFieldTypeDenoter(SourcePosition srcPos, Identifier identifier, TypeDenoter typeDenoter,
                                    FieldTypeDenoter fieldTypeDenoter) {
        super(srcPos);

        this.I = identifier;
        this.typeDenoter = typeDenoter;

        this. fieldTypeDenoter = fieldTypeDenoter;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitMultipleFieldTypeDenoter(this, o);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof MultipleFieldTypeDenoter) {
            MultipleFieldTypeDenoter ft = (MultipleFieldTypeDenoter) obj;

            return (this.I.spelling.compareTo(ft.I.spelling) == 0)
                    && this.typeDenoter.equals(ft.typeDenoter)
                    && this.fieldTypeDenoter.equals(ft.fieldTypeDenoter);

        } else {
            return false;
        }

    }
}
