package ComputerSimulation.computer.components

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class FlFpNORSpec extends Specification {

    private Circuits circuits = new Circuits()

    @Shared
    private static Wire Q = new Wire()

    @Shared
    private static Wire Q_prim = new Wire()

    @Unroll
    def "FlFpNOR: #s and #r is S='#q', R='#not_q'"() {

        expect:
        circuits.oFLIP_FLOP_00(new Wire(s), new Wire(r), Q, Q_prim)

        Q.getSignal() == q
        Q_prim.getSignal() == not_q
        q == !not_q

        where:
        s     | r     | q      | not_q
        true  | false | true   | false   // Latch SET
        false | false | true   | false   // After s=1 and r=0 (no change)

        false | true  | false  | true    // Latch RESET
        false | false | false  | true    // After s=0 and r=1 (no change)

//      true  | true  | false  | false   // invalid
    }

}