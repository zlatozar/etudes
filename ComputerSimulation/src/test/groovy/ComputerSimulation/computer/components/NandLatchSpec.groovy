package ComputerSimulation.computer.components

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class NandLatchSpec extends Specification {

    private Circuits circuits = new Circuits()

    @Shared
    private static Wire Q = new Wire()

    @Shared
    private static Wire Q_prim = new Wire()

    @Unroll
    def "oNAND_Latch: #s and #r is Q='#q', notQ='#not_q'"() {

        expect:
        circuits.oNAND_Latch(new Wire(s), new Wire(r), Q, Q_prim)

        Q.getSignal() == q
        Q_prim.getSignal() == not_q
        q == !not_q

        where:
        r     | s         | q      | not_q
        false | true      | true   | false   // Latch SET s=1
        false | true      | true   | false

        true  | true      | true   | false   // hold
        true  | true      | true   | false

        true  | false     | false  | true    // Latch RESET
        true  | false     | false  | true

        true  | true      | false  | true    // hold
        true  | true      | false  | true

        false | true      | true   | false   // Latch SET s=1 (again)

//      false | false | true   | true    // race
    }

}