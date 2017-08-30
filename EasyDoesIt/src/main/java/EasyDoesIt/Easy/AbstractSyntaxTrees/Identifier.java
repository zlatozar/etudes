package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Identifier extends Terminal {

    public TypeDenoter type;

    // Somewhere it is declared so 'decl' could be
    // either a Declaration or a FieldTypeDenoter
    public AST decl;

    public Identifier(SourcePosition srcPos, String theSpelling) {
        super(srcPos, theSpelling);

        this.type = null;
        this.decl = null;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitIdentifier(this, o);
    }
}
