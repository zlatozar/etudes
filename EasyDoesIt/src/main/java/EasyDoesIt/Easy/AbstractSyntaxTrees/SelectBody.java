package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SelectBody extends SelectionBody {

    public Case caseList;

    public SelectBody(SourcePosition srcPos, Case caseList) {
        super(srcPos);
        this.caseList = caseList;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSelectBody(this, o);
    }
}
