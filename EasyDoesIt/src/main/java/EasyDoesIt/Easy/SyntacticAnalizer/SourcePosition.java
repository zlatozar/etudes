package EasyDoesIt.Easy.SyntacticAnalizer;

public class SourcePosition {

    public int start;
    public int finish;

    public SourcePosition() {
        this.start = 0;
        this.finish = 0;
    }

    public SourcePosition(int start, int finish) {
        this.start = start;
        this.finish = finish;
    }

    @Override
    public String toString() {
        return "(" + start + ", " + finish + ")";
    }
}
