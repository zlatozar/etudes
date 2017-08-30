package EasyDoesIt.Easy.AbstractSyntaxTrees;

import EasyDoesIt.Easy.SyntacticAnalizer.SourcePosition;

public class MultipleDeclaredNames extends Definition {

    public Definition declaredNames;
    public Definition declaredNamesSeq;

    public MultipleDeclaredNames(SourcePosition srcPos, Definition declaredNames, Definition declaredNamesSeq) {
        super(srcPos);
        this.declaredNames = declaredNames;
        this.declaredNamesSeq = declaredNamesSeq;
    }

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitMultipleDeclaredNames(this, o);
    }
}
