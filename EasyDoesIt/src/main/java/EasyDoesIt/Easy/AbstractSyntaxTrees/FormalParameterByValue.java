package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class FormalParameterByValue extends FormalParameter {

    public Identifier identifier;

    public FormalParameterByValue(SourcePosition srcPos, Identifier identifier, TypeDenoter typeDenoter) {
        super(srcPos);
        this.identifier = identifier;
        this.typeDenoter = typeDenoter;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitFormalParameterByValue(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormalParameterByValue that = (FormalParameterByValue) o;

        if (!identifier.equals(that.identifier)) return false;
        return typeDenoter.equals(that.typeDenoter);

    }
}
