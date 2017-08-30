package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class Program extends AST {

    public ProgramBody program;

    public Program(SourcePosition srcPos, ProgramBody pbAST) {
        super(srcPos);

        this.program = pbAST;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitProgram(this, o);
    }
}
