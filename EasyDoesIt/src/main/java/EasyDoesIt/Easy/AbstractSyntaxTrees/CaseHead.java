package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class CaseHead extends AST {

    public Selector selector;

    public CaseHead(SourcePosition srcPos, Selector selector) {
        super(srcPos);
        this.selector = selector;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCaseHead(this, o);
    }
}
