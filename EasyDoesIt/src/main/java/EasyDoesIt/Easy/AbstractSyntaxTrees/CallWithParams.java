package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class CallWithParams extends ProcedureRef {

    public Identifier identifier;
    public ActualParameterSequence params;

    public CallWithParams(SourcePosition srcPos, Identifier identifier, ActualParameterSequence params) {
        super(srcPos);
        this.identifier = identifier;
        this.params = params;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCallWithParams(this, o);
    }
}
