package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Stepper extends LoopControl {

    public StepExpression stepExpression;

    public Stepper(SourcePosition srcPos, StepExpression stepExpression) {
        super(srcPos);
        this.stepExpression = stepExpression;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitStepper(this, o);
    }
}
