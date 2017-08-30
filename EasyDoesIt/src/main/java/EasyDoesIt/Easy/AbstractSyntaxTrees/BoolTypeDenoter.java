package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class BoolTypeDenoter extends TypeDenoter {

    public BoolTypeDenoter(SourcePosition thePosition) {
        super(thePosition);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitBoolTypeDenoter(this, o);
    }

    @Override
    public boolean equals(Object obj) {

        if ((obj != null) && (obj instanceof ErrorTypeDenoter)) {
            return true;

        } else {
            return ((obj != null) && (obj instanceof BoolTypeDenoter));
        }
    }
}
