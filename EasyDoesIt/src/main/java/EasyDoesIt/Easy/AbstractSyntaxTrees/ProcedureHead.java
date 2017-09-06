package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ProcedureHead extends AST {

    public Identifier identifier;
    public FormalParameterSequence FPS;

    public ProcedureHead(SourcePosition srcPos, Identifier identifier, FormalParameterSequence FPS) {
        super(srcPos);
        this.identifier = identifier;
        this.FPS = FPS;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitProcedureHead(this, o);
    }
}
