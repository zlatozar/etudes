package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class StatementSeq extends Statement {

    public Statement stmtSeq;
    public Statement stmt;

    public StatementSeq(SourcePosition srcPos, Statement stmtSeq, Statement stmt) {
        super(srcPos);
        this.stmtSeq = stmtSeq;
        this.stmt = stmt;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitStatementSeq(this, o);
    }
}
