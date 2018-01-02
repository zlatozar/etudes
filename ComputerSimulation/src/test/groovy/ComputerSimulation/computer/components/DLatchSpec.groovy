package ComputerSimulation.computer.components

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class DLatchSpec extends Specification {

    private Circuits circuits = new Circuits()

    @Shared
    private static Wire Q = new Wire()

    @Shared
    private static Wire Q_prim = new Wire()

    @Unroll
    def "oD_Latch: #d and CLK=#clk is Q='#q'"() {

        def dLatch = circuits.oD_Latch(Q, Q_prim)

        expect:
        dLatch(new Wire(d), new Wire(clk))
        Q.getSignal() == q

        where:
        d     | clk   | q
        false | false | true
        true  | true  | true   // When clk is high q == d (transparent)
        true  | false | true   // When clk is low its holding it's state

        false | true  | false  // transparent
        true  | false | false  // hold previous state
        false | true  | false
        false | false | false

        true  | true  | true   // q == clk
        false | false | true   // hold previous state

        true  | true  | true   // q == clk
        false | false | true   // hold previous state
    }
}