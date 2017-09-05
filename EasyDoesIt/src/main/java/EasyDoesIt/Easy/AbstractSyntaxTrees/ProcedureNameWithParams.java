package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ProcedureNameWithParams extends BlockCodeName {

    public Identifier procName;
    public FormalParameterSequence FPS;

    public ProcedureNameWithParams(SourcePosition srcPos, Identifier procName, FormalParameterSequence FPS) {
        super(srcPos);
        this.procName = procName;
        this.FPS = FPS;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitProcedureNameWithParams(this, o);
    }
}
