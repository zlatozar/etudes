package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class IntegerLiteral extends Terminal {

    public IntegerLiteral(SourcePosition thePosition, String theSpelling) {
        super(thePosition, theSpelling);
    }

    public Object visit(Visitor v, Object o) {
        return v.visitIntegerLiteral(this, o);
    }

}
