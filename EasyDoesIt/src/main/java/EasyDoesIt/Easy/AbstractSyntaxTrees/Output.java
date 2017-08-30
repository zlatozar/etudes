package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Output extends Statement {

    public OutputList outputList;

    public Output(SourcePosition srcPos, OutputList outputList) {
        super(srcPos);
        this.outputList = outputList;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitOutput(this, o);
    }
}
