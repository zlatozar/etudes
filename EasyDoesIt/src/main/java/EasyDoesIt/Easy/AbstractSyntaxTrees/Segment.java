package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Segment extends AST {

    public Definition definition;
    public Statement statement;

    public Segment(SourcePosition srcPos, Definition definition, Statement statement) {
        super(srcPos);
        this.definition = definition;
        this.statement = statement;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCommand(this, o);
    }
}
