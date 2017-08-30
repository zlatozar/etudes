package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class IntTypeDenoter extends TypeDenoter {

    public IntTypeDenoter(SourcePosition thePosition) {
        super(thePosition);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitIntTypeDenoter(this, o);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof ErrorTypeDenoter) {
            return true;

        } else {
            return (obj != null && obj instanceof IntTypeDenoter);
        }
    }
}
