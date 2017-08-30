package EasyDoesIt.Easy.ContextualAnalizer;

import EasyDoesIt.Easy.AbstractSyntaxTrees.Definition;

public class IdEntry {

    protected String id;
    protected Definition typeInfo;

    protected int level;
    protected IdEntry previous;

    IdEntry(String id, Definition typeInfo, int level, IdEntry previous) {
        this.id = id;
        this.typeInfo = typeInfo;
        this.level = level;
        this.previous = previous;
    }

}
