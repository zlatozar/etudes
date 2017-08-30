package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class ProgramBody extends AST {

    public Identifier prgName;
    public Segment prgBody;
    public Identifier prgNameEnd;

    public ProgramBody(SourcePosition srcPos, Identifier prgName, Segment prgBody, Identifier prgNameEnd) {

        super(srcPos);

        this.prgName = prgName;
        this.prgBody = prgBody;
        this.prgNameEnd = prgNameEnd;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitProgramBody(this, o);
    }
}
