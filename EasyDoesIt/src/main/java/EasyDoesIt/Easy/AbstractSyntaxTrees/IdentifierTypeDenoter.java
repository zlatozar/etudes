package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class IdentifierTypeDenoter extends TypeDenoter {

    public Identifier identifier;

    public IdentifierTypeDenoter(SourcePosition srcPos, Identifier identifier) {
        super(srcPos);
        this.identifier = identifier;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitIdentifierTypeDenoter(this, o);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ErrorTypeDenoter) {
            return true;

        } else if (obj != null && obj instanceof IdentifierTypeDenoter) {
            return this.identifier.equals(((IdentifierTypeDenoter) obj).identifier);

        } else {
            return false;
        }
    }
}
