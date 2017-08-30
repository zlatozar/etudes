package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class FunctionHead extends AST {

    public BlockCodeName blockCodeName;
    public TypeDenoter typeDenoter;

    public FunctionHead(SourcePosition srcPos, BlockCodeName blockCodeName, TypeDenoter typeDenoter) {
        super(srcPos);
        this.blockCodeName = blockCodeName;
        this.typeDenoter = typeDenoter;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitFunctionHead(this, o);
    }
}

