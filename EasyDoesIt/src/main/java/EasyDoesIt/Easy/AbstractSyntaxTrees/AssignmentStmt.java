package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class AssignmentStmt extends Statement {

    public Vname variableList;
    public Expression expression;

    public AssignmentStmt(SourcePosition srcPos, Vname variableList, Expression expression) {
        super(srcPos);
        this.variableList = variableList;
        this.expression = expression;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitAssignmentStmt(this, o);
    }
}
