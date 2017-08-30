package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Operator extends Terminal {

    public Definition decl;

    public Operator(SourcePosition srcPos, String spelling) {
        super(srcPos, spelling);

        this.decl = null;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitOperator(this, o);
    }
}
