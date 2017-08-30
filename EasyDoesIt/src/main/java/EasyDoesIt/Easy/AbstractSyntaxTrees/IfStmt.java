package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class IfStmt extends Statement {

    public ConditionalClause conditionalClause;
    public TrueBranch trueBranch;

    public IfStmt(SourcePosition srcPos, ConditionalClause conditionalClause, TrueBranch trueBranch) {
        super(srcPos);
        this.conditionalClause = conditionalClause;
        this.trueBranch = trueBranch;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitIfStmt(this, o);
    }
}
