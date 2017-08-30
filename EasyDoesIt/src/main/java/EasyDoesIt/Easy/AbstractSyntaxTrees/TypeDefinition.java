package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class TypeDefinition extends Definition {

    public Identifier I;
    public TypeDenoter T;

    public TypeDefinition(SourcePosition srcPos, Identifier iAST, TypeDenoter tAST) {
        super(srcPos);

        this.I = iAST;
        this.T = tAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitTypeDefinition(this, o);
    }
}
