package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ParameterByValue extends Parameter {

    public Identifier identifier;
    public TypeDenoter typeDenoter;

    public ParameterByValue(SourcePosition srcPos, Identifier identifier, TypeDenoter typeDenoter) {
        super(srcPos);
        this.identifier = identifier;
        this.typeDenoter = typeDenoter;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitParameterByValue(this, o);
    }
}
