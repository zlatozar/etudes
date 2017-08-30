package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ParameterList extends Parameter {

    public Parameter paramSeq;
    public Parameter param;

    public ParameterList(SourcePosition srcPos, Parameter paramSeq, Parameter param) {
        super(srcPos);
        this.paramSeq = paramSeq;
        this.param = param;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitParameterList(this, o);
    }
}
