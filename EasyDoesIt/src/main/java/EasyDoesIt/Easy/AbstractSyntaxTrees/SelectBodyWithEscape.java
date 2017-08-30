package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SelectBodyWithEscape extends SelectionBody {

    public Case caseList;
    public EscapeCase escapeCase;

    public SelectBodyWithEscape(SourcePosition srcPos, Case caseList, EscapeCase escapeCase) {
        super(srcPos);
        this.caseList = caseList;
        this.escapeCase = escapeCase;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSelectBodyWithEscape(this, o);
    }
}
