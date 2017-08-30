package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class IfElseStmt extends Statement {

    public ConditionalClause conditionalClause;
    public TrueBranch trueBranch;
    public FalseBranch falseBranch;

    public IfElseStmt(SourcePosition srcPos, ConditionalClause conditionalClause, TrueBranch trueBranch, FalseBranch falseBranch) {
        super(srcPos);
        this.conditionalClause = conditionalClause;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitIfElseStmt(this, o);
    }
}
