package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class FunctionDefinition extends Definition {

    public FunctionHead funcHead;
    public Segment segment;
    public FunctionEnd funcEnd;

    public FunctionDefinition(SourcePosition srcPos, FunctionHead funcHead, Segment segment, FunctionEnd funcEnd) {
        super(srcPos);
        this.funcHead = funcHead;
        this.segment = segment;
        this.funcEnd = funcEnd;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitFunctionDefinition(this, o);
    }
}
