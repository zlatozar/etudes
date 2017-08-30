package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class CharTypeDenoter extends TypeDenoter {

    public CharTypeDenoter(SourcePosition thePosition) {
        super(thePosition);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCharTypeDenoter(this, o);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof ErrorTypeDenoter) {
            return true;

        } else {
            return (obj != null && obj instanceof CharTypeDenoter);
        }
    }
}
