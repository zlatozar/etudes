package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class FormalParameterList extends FormalParameterSequence {

    public FormalParameter param;
    public FormalParameterSequence paramSeq;

    public FormalParameterList(SourcePosition srcPos, FormalParameter param, FormalParameterSequence paramSeq) {
        super(srcPos);
        this.paramSeq = paramSeq;
        this.param = param;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitFormalParameterList(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormalParameterList that = (FormalParameterList) o;

        if (!paramSeq.equals(that.paramSeq)) return false;
        return param.equals(that.param);

    }
}
