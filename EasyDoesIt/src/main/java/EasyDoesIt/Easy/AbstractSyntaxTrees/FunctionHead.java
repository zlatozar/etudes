package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class FunctionHead extends AST {

    public Identifier identifier;
    public FormalParameterSequence FPS;
    public TypeDenoter typeDenoter;

    public FunctionHead(SourcePosition srcPos, Identifier identifier, FormalParameterSequence FPS, TypeDenoter typeDenoter) {
        super(srcPos);
        this.identifier = identifier;
        this.FPS = FPS;
        this.typeDenoter = typeDenoter;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitFunctionHead(this, o);
    }
}

