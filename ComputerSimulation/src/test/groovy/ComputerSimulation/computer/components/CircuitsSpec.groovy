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
    def "oFLIP_FLOP_11: #s and #r is S='#q', R='#not_q'"() {

        expect:
        circuits.oFLIP_FLOP_11(new Wire(s), new Wire(r), Q, Q_prim)

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

    @Unroll
    def "oFLIP_FLOP_00: #s and #r is S='#q', R='#not_q'"() {

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

    @Unroll
    def "COMPARATOR: #a and #b is equal='#result'"() {

        expect:
        circuits.COMPARATOR(a, b).getSignal() == result

        where:
        a                                  | b                                 | result
        [new Wire(true), new Wire(false)]  | [new Wire(true), new Wire(false)] | true
        [new Wire(false), new Wire(false)] | [new Wire(true), new Wire(false)] | false
        [new Wire(false), new Wire(true)]  | [new Wire(true), new Wire(false)] | false
        [new Wire(true), new Wire(false)]  | [new Wire(false), new Wire(false)]| false
        [new Wire(false), new Wire(false)] | [new Wire(false), new Wire(false)]| true
    }

    @Unroll
    def "INVERTER: #a and #ctrl is equal='#result'"() {

        expect:
        circuits.INVERTER(a, ctrl).collect({ Wire wire -> wire.getSignal() }) == result

        where:
        a                                  | ctrl            | result
        [new Wire(true), new Wire(false)]  | new Wire(true)  | [false, true]
        [new Wire(false), new Wire(false)] | new Wire(true)  | [true, true]
        [new Wire(false), new Wire(true)]  | new Wire(true)  | [true, false]
        [new Wire(true), new Wire(false)]  | new Wire(false) | [true, false]
        [new Wire(false), new Wire(false)] | new Wire(false) | [false, false]
    }

}