package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ProcedureNameWithParams extends BlockCodeName {

    public Identifier procName;
    public Parameter params;

    public ProcedureNameWithParams(SourcePosition srcPos, Identifier procName, Parameter params) {
        super(srcPos);
        this.procName = procName;
        this.params = params;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitProcedureNameWithParams(this, o);
    }
}
