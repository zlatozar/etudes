package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class RealLiteral extends Terminal {

    public RealLiteral(SourcePosition thePosition, String theSpelling) {
        super(thePosition, theSpelling);
    }

    public Object visit(Visitor v, Object o) {
        return v.visitRealLiteral(this, o);
    }


}
