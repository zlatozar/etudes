package ComputerSimulation.computer.components

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class DLatch extends Specification {

    private Circuits circuits = new Circuits()

    @Shared
    private static Wire Q = new Wire()

    @Shared
    private static Wire Q_prim = new Wire()

    @Unroll
    def "oD_Latch: #d and CLK=#clk is Q='#q'"() {

        expect:
        circuits.oD_Latch(new Wire(d), new Wire(clk), Q, Q_prim)
        Q.getSignal() == q

        where:
        d     | clk   | q
        true  | true  | true   // set
        false | false | true   // hold
        true  | false | true   // hold
        false | true  | false  // reset

        true  | true  | true   // set (again)
    }
}