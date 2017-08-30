package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class CharacterLiteral extends Terminal {

    public CharacterLiteral(SourcePosition thePosition, String theSpelling) {
        super(thePosition, theSpelling);

    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCharacterLiteral(this, o);
    }

}
