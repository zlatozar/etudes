package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class DefinitionSeq extends Definition {

    public Definition definitionSeq;
    public Definition definition;

    public DefinitionSeq(SourcePosition srcPos, Definition definitionSeq, Definition definition) {
        super(srcPos);

        this.definitionSeq = definitionSeq;
        this.definition = definition;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitDefinitionSeq(this, o);
    }
}
