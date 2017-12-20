package ComputerSimulation.computer.components

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class FlFpNANDSpec extends Specification {

    private Circuits circuits = new Circuits()

    @Shared
    private static Wire Q = new Wire()

    @Shared
    private static Wire Q_prim = new Wire()

    @Unroll
    def "FlFpNAND: #s and #r is S='#q', R='#not_q'"() {

        expect:
        circuits.FlFpNAND(new Wire(s), new Wire(r), Q, Q_prim)

        Q.getSignal() == q
        Q_prim.getSignal() == not_q
        q == !not_q

        where:
        s     | r     | q      | not_q
        true  | false | false  | true    // Latch SET
        true  | true  | false  | true    // After s=1 and r=0 (no change)

        false | true  | true   | false   // Latch RESET
        true  | true  | true   | false   // After s=0 and r=1 (no change)

//      false | false | true   | true    // invalid
    }
}