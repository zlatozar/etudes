package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

abstract public class Terminal extends AST {

    // token spelling from scanner
    public String spelling;

    public Terminal(SourcePosition srcPos, String spelling) {
        super(srcPos);

        this.spelling = spelling;
    }
}
