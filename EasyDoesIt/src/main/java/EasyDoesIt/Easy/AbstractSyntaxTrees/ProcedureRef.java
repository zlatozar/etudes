package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class ProcedureRef extends Expression {

    public ProcedureRef(SourcePosition srcPos) {
        super(srcPos);
    }
}
