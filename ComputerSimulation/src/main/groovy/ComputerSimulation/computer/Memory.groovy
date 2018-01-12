package ComputerSimulation.computer

/**
 * Represents the memory in EC-1 computer
 */
class Memory {

    private static final int CAPACITY = Math.pow(2, 16) - 1

    private static final int NUMBER_OF_ADDRESSES = CAPACITY / 4

    // use TreeMap to guarantee display order
    private static final Map<Integer, List<String>> MEMORY = new TreeMap<>()

    protected static final List<String> EMPTY_WORD = ['0', '0', '0', '0', '0', '0', '0', '0',
                                                      '0', '0', '0', '0', '0', '0', '0', '0',
                                                      '0', '0', '0', '0', '0', '0', '0', '0',
                                                      '0', '0', '0', '0', '0', '0', '0', '0']
    private final PC pointer = new PC()

    Memory() {
        init()
    }

    List<String> next() {
        List<String> content = MEMORY.get(pointer.get())
        pointer.next()

        return content
    }

    boolean hasNext() {
        return pointer.get() == NUMBER_OF_ADDRESSES
    }

    // Helper methods

    private void init() {
        for (int i = 0; i < NUMBER_OF_ADDRESSES; i++) {
            MEMORY.put(pointer.get(), EMPTY_WORD)
            pointer.next()
        }
    }

    protected static int size() {
        return CAPACITY
    }

    protected static int getMaxPC() {
        return MEMORY.size()
    }

    /**
     * Programming counter
     */
    private static class PC {
        private int PC = 0

        synchronized void next() {
            PC = PC + 4
        }

        int get() {
            return PC
        }

        synchronized void set(int address) {
            this.PC = address
        }
    }

    @Override
    String toString() {
        StringBuilder sb = new StringBuilder()
        sb.append('{\n')
        sb.append("PC=${pointer.get()}")
        sb.append('\n')

        for (address in MEMORY) {
            if (address.value != EMPTY_WORD) {
                int key = address.key

                sb.append(Integer.toHexString(key))
                sb.append('(')
                sb.append(key)
                sb.append(')')
                sb.append(':')
                // TODO: every address value should be interpreted
                sb.append(address.value)
                sb.append('\n')
            }
        }

        sb.append('}\n')
    }
}
