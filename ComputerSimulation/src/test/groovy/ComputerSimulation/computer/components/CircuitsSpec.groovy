package ComputerSimulation.computer.components

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class CircuitsSpec extends Specification {

    private Circuits circuits = new Circuits()

    @Shared
    private static Wire Q = new Wire()

    @Shared
    private static Wire Q_prim = new Wire()

    @Unroll
    def "HA: #a and #b is S='#sum', C='#cout'"() {

        expect:
        Map<String, Wire> out = circuits.HA(new Wire(a), new Wire(b))
        out.S.getSignal() == sum
        out.C.getSignal() == cout

        where:
        a     | b     | sum   | cout
        false | false | false | false
        false | true  | true  | false
        true  | false | true  | false
        true  | true  | false | true
    }

    @Unroll
    def "FA: #a and #b is S='#sum', C='#cout'"() {

        expect:
        Map<String, Wire> out = circuits.FA(new Wire(a), new Wire(b), new Wire(cin))
        out.S.getSignal() == sum
        out.C.getSignal() == cout

        where:
        a     | b     | cin    | sum   | cout
        false | false | false  | false | false
        false | false | true   | true  | false
        false | true  | false  | true  | false
        false | true  | true   | false | true
        true  | false | false  | true  | false
        true  | false | true   | false | true
        true  | true  | false  | false | true
        true  | true  | true   | true  | true
    }

    @Unroll
    def "FlFpNAND: #s and #r is S='#q', R='#not_q'"() {

        expect:
        circuits.FlFpNAND(new Wire(s), new Wire(r), Q, Q_prim)

        Q.getSignal() == q
        Q_prim.getSignal() == not_q

        where:
        s     | r     | q      | not_q
        true  | false | false  | true    // Latch SET
        true  | true  | false  | true    // After s=1 and r=0 (no change)

        false | true  | true   | false   // Latch RESET
        true  | true  | true   | false   // After s=0 and r=1 (no change)

//      false | false | true   | true    // invalid
    }

    @Unroll
    def "FlFpNOR: #s and #r is S='#q', R='#not_q'"() {

        expect:
        circuits.FlFpNOR(new Wire(s), new Wire(r), Q, Q_prim)

        Q.getSignal() == q
        Q_prim.getSignal() == not_q

        where:
        s     | r     | q      | not_q
        true  | false | true   | false   // Latch SET
        false | false | true   | false   // After s=1 and r=0 (no change)

        false | true  | false  | true    // Latch RESET
        false | false | false  | true    // After s=0 and r=1 (no change)

//      true  | true  | false  | false   // invalid
    }

}