package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class IdentifierType extends TypeDenoter {

    public Identifier identifier;

    public IdentifierType(SourcePosition srcPos, Identifier identifier) {
        super(srcPos);
        this.identifier = identifier;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitIdentifierType(this, o);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ErrorTypeDenoter) {
            return true;

        } else if (obj != null && obj instanceof IdentifierType) {
            return this.identifier.equals(((IdentifierType) obj).identifier);

        } else {
            return false;
        }
    }
}
