package ComputerSimulation.computer

import spock.lang.Specification

class MemorySpec extends Specification {

    private final Memory memory = new Memory()

    def 'Memory capacity'() {

        given: 'EC-1 computer memory'

        when: 'Memory sizes are requested'
        def size = memory.size()
        def maxPC = memory.getMaxPC()

        then: 'Parameters should be respected'
        assert size == 65535
        assert maxPC == 16383
    }

    def 'Memory initialization'() {

        given: 'EC-1 computer memory'

        when: 'Memory instantiated'

        then: 'Memory should be empty'
        while(memory.hasNext()) {
            assert memory.next() == Memory.EMPTY_WORD
        }
    }

}