package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class AnyTypeDenoter extends TypeDenoter {

    public AnyTypeDenoter(SourcePosition thePosition) {
        super(thePosition);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitAnyTypeDenoter(this, o);
    }

    public boolean equals(Object obj) {
        return false;
    }
}
