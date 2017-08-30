package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public abstract class StepExpression extends AST {

    public StepExpression(SourcePosition srcPos) {
        super(srcPos);
    }
}
