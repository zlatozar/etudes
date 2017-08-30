package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class StepperWhile extends LoopControl {

    public StepExpression stepExpression;
    public While aWhileExpression;

    public StepperWhile(SourcePosition srcPos, StepExpression stepExpression, While aWhileExpression) {
        super(srcPos);
        this.stepExpression = stepExpression;
        this.aWhileExpression = aWhileExpression;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitStepperWhile(this, o);
    }
}
