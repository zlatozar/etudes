package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class CharacterExpression extends Expression {

    public CharacterLiteral CL;

    public CharacterExpression(SourcePosition thePosition, CharacterLiteral clAST) {
        super(thePosition);

        this.CL = clAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitCharacterExpression(this, o);
    }
}
