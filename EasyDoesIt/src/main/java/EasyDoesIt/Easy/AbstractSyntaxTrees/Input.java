package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Input extends Statement {

    public InputList inputList;

    public Input(SourcePosition srcPos, InputList inputList) {
        super(srcPos);
        this.inputList = inputList;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitInput(this, o);
    }
}
