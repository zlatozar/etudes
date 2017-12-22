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
        a                       | b                                 | result
        toWires([true, false])  | toWires([true, false]) | true
        toWires([false, false]) | toWires([true, false]) | false
        toWires([false, true])  | toWires([true, false]) | false
        toWires([true, false])  | toWires([false, false])| false
        toWires([false, false]) | toWires([false, false])| true
    }

    @Unroll
    def "INVERTER: #a and #ctrl is equal='#result'"() {

        expect:
        circuits.INVERTER(a, ctrl).collect({ Wire wire -> wire.getSignal() }) == result

        where:
        a                       | ctrl            | result
        toWires([true, false])  | new Wire(true)  | [false, true]
        toWires([false, false]) | new Wire(true)  | [true, true]
        toWires([false, true])  | new Wire(true)  | [true, false]
        toWires([true, false])  | new Wire(false) | [true, false]
        toWires([false, false]) | new Wire(false) | [false, false]
    }

    @Unroll
    def "MUX_16to1: #selector selects '#result'"() {

        expect:
        circuits.MUX_16to1(word, selector).getSignal() == result

        where:
        word                                 | selector                              | result
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([false, false, false, false]) | false
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([false, false, false, true])  | true
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([false, false, true, false])  | false
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([false, false, true, true])   | false
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([false, true, false, false])  | false
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([false, true, false, true])   | false
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([false, true, true, false])   | true
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([false, true, true, true])    | true

        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([true, false, false, false])  | false
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([true, false, false, true])   | false
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([true, false, true, false])   | false
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([true, false, true, true])    | false
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([true, true, false, false])   | false
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([true, true, false, true])    | false
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([true, true, true, false])    | true
        toWires([false, true, false, false,
                 false, false, true, true,
                 false, false, false, false,
                 false, false, true, false]) | toWires([true, true, true, true])     | false
    }

    // Helper methods

    private static List<Wire> toWires(List<Boolean> signals) {
        return signals.collect({ signal -> new Wire(signal)})
    }
}