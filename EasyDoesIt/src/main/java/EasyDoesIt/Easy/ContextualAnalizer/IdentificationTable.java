package EasyDoesIt.Easy.ContextualAnalizer;

import EasyDoesIt.Easy.AbstractSyntaxTrees.Definition;

public final class IdentificationTable {

    private int level;
    private IdEntry latest;

    public IdentificationTable() {
        level = 0;
        latest = null;
    }

    public void openScope() {
        level++;
    }

    public void closeScope() {

        IdEntry entry;
        IdEntry local;

        // Presumably, idTable.level > 0.
        entry = this.latest;

        while (entry.level == this.level) {
            local = entry;
            entry = local.previous;
        }

        this.level--;
        this.latest = entry;
    }

    public void enter(String id, Definition attr) {

        // 'entry' points to latest
        IdEntry entry = this.latest;

        boolean present = false;
        boolean searching = true;

        // Check for duplicate entry ...
        while (searching) {

            if (entry == null || entry.level < this.level) {
                searching = false;

            } else if (entry.id.equals(id)) {
                present = true;
                searching = false;

            } else {
                entry = entry.previous;
            }
        }

        attr.duplicated = present;

        // Add new entry ...
        entry = new IdEntry(id, attr, this.level, this.latest);
        this.latest = entry;
    }

    public Definition retrieve(String id) {

        IdEntry entry;
        Definition attr = null;

        boolean searching = true;

        entry = this.latest;

        while (searching) {

            if (entry == null) {
                searching = false;

            } else if (entry.id.equals(id)) {
                searching = false;
                attr = entry.typeInfo;

            } else {
                entry = entry.previous;
            }
        }

        return attr;
    }
}
