package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class SelectionStmt extends Statement {

    public SelectionHead selectionHead;
    public SelectionBody selectionBody;
    public SelectionEnd selectionEnd;

    public SelectionStmt(SourcePosition srcPos, SelectionHead selectionHead, SelectionBody selectionBody,
                         SelectionEnd selectionEnd) {
        super(srcPos);

        this.selectionHead = selectionHead;
        this.selectionBody = selectionBody;
        this.selectionEnd = selectionEnd;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSelectionStmt(this, o);
    }
}
