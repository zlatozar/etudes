package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class FormalParameterByName extends FormalParameter {

    public Identifier identifier;

    public FormalParameterByName(SourcePosition srcPos, Identifier identifier, TypeDenoter typeDenoter) {
        super(srcPos);
        this.identifier = identifier;
        this.typeDenoter = typeDenoter;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitFormalParameterByName(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormalParameterByName that = (FormalParameterByName) o;

        if (!identifier.equals(that.identifier)) return false;
        return typeDenoter.equals(that.typeDenoter);

    }
}
