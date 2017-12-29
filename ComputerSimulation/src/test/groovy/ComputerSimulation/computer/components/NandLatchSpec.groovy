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
        s     | r     | q      | not_q
        true  | false | true   | false   // Latch SET (Q is transparent to the S signal)
        true  | true  | true   | false   // After s=1 and r=0 (Q is the same)

        false | true  | false  | true    // Latch RESET (Q is transparent to the R signal)
        true  | true  | false  | true    // After s=0 and r=1 (Q is the same)

//      false | false | true   | true    // race
    }

}