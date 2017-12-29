package ComputerSimulation.computer.components

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class JKLatchSpec extends Specification {

    private Circuits circuits = new Circuits()

    @Shared
    private static Wire Q = new Wire()

    @Shared
    private static Wire Q_prim = new Wire()

    @Unroll
    def "oJK_Latch: #j, #k and CLK=#clk is Q='#q'"() {

        expect:
        circuits.oJK_Latch(new Wire(j), new Wire(k), new Wire(clk), Q, Q_prim)
        Q.getSignal() == q

        where:
        j     | k     | clk   | q
        true  | false | false | true
        false | true  | true  | false  // rising edge, K resets Q
        true  | false | false | false
        true  | false | true  | true   // rising edge, J set the Q

        true  | true  | false | true   // no change
        true  | true  | true  | false  // toggle on positive edge (from false to true)
    }

}