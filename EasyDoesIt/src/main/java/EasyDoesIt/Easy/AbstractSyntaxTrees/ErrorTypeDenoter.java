package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ErrorTypeDenoter extends TypeDenoter {

    public ErrorTypeDenoter(SourcePosition thePosition) {
        super(thePosition);
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitErrorTypeDenoter(this, o);
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
