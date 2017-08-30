package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

// DECLARE ... END;
public class Declaration extends Definition {

    public Definition declaredNames;
    public TypeDenoter typeDenoter;

    public Declaration(SourcePosition srcPos, Definition declaredNames, TypeDenoter typeDenoter) {
        super(srcPos);
        this.declaredNames = declaredNames;
        this.typeDenoter = typeDenoter;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitDeclaration(this, o);
    }
}
