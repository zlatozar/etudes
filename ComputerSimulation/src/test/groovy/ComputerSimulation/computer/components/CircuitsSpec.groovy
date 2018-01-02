package ComputerSimulation.computer.components

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class CircuitsSpec extends Specification {

    private Circuits circuits = new Circuits()

    @Unroll
    def "HA: #a and #b is S='#sum', C='#carry'"() {

        expect:
        Map<String, Wire> out = circuits.HA(new Wire(a), new Wire(b))
        out.S.getSignal() == sum
        out.C.getSignal() == carry

        where:
        a     | b     | sum   | carry
        false | false | false | false
        false | true  | true  | false
        true  | false | true  | false
        true  | true  | false | true
    }

    @Unroll
    def "FA: #a plus #b is S='#sum', C='#carry'"() {

        expect:
        Map<String, Wire> out = circuits.FA(new Wire(a), new Wire(b), new Wire(cin))
        out.S.getSignal() == sum
        out.C.getSignal() == carry

        where:
        a     | b     | cin    | sum   | carry
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
    def "BA: #a plus #b is S='#sum', C='#carry'"() {

        expect:
        def out = circuits.BA16(a, b)
        wiresToInts(out.S) == sum
        out.C.getSignal() == carry

        where:
        a                                                             | b                                                             | sum                                              | carry
        intsToWires([0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0]) | intsToWires([0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1]) | [0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1] | false
    }

    @Unroll
    def "AS16: #a, #b subtract='#sub' is result='#result'"() {

        expect:
        List<Wire> out = circuits.AS16(a, b, sub)
        wiresToInts(out) == result

        where:
        a                                                             | b                                                             | sub             | result
        intsToWires([0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0]) | intsToWires([0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1]) | new Wire(false) | [0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1]
        intsToWires([0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0]) | intsToWires([0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1]) | new Wire(true)  | [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1]
    }

    @Unroll
    def "dLatchComponent: #d and #clk is set='#setOut', reset='#resetOut'"() {

        expect:
        Wire set = new Wire()
        Wire reset = new Wire()

        def component = circuits.dLatchComponent(set, reset)
        component(new Wire(d), new Wire(clk))

        set.getSignal() == setOut
        reset.getSignal() == resetOut

        where:
        d     | clk   | resetOut | setOut
        false | false | true     | true
        false | true  | true     | false

        true  | false | true     | true
        true  | true  | false    | true
    }

    @Unroll
    def "COMPARATOR: #a and #b is equal='#result'"() {

        expect:
        circuits.COMPARATOR(a, b).getSignal() == result

        where:
        a                            | b                            | result
        boolsToWires([true, false])  | boolsToWires([true, false])  | true
        boolsToWires([false, false]) | boolsToWires([true, false])  | false
        boolsToWires([false, true])  | boolsToWires([true, false])  | false
        boolsToWires([true, false])  | boolsToWires([false, false]) | false
        boolsToWires([false, false]) | boolsToWires([false, false]) | true
    }

    @Unroll
    def "INVERTER: #a and #ctrl is equal='#result'"() {

        expect:
        circuits.INVERTER(a, ctrl).collect({ Wire wire -> wire.getSignal() }) == result

        where:
        a                            | ctrl            | result
        boolsToWires([true, false])  | new Wire(true)  | [false, true]
        boolsToWires([false, false]) | new Wire(true)  | [true, true]
        boolsToWires([false, true])  | new Wire(true)  | [true, false]
        boolsToWires([true, false])  | new Wire(false) | [true, false]
        boolsToWires([false, false]) | new Wire(false) | [false, false]
    }

    @Unroll
    def "MUX_16to1: #selector selects '#result'"() {

        expect:
        circuits.MUX_16to1(word, selector).getSignal() == result

        where:
        word                                 | selector                                        | result
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([false, false, false, false]) | false
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([false, false, false, true])  | true
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([false, false, true, false])  | false
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([false, false, true, true])   | false
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([false, true, false, false])  | false
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([false, true, false, true])   | false
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([false, true, true, false])   | true
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([false, true, true, true])    | true

        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([true, false, false, false])  | false
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([true, false, false, true])   | false
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([true, false, true, false])   | false
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([true, false, true, true])    | false
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([true, true, false, false])   | false
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([true, true, false, true])    | false
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([true, true, true, false])    | true
        boolsToWires([false, true, false, false,
                      false, false, true, true,
                      false, false, false, false,
                      false, false, true, false]) | boolsToWires([true, true, true, true])     | false
    }

    @Unroll
    def "DEMUX_1to16: #selector selects output '#index'"() {

        expect:
        circuits.DEMUX_1to16(word, selector)[index].getSignal() == result

        where:
        word           | selector                                   | index | result
        new Wire(true) | boolsToWires([false, false, false, false]) | 0     | true
        new Wire(true) | boolsToWires([false, false, false, true])  | 1     | true
        new Wire(true) | boolsToWires([false, false, true, false])  | 2     | true
        new Wire(true) | boolsToWires([false, false, true, true])   | 3     | true
        new Wire(true) | boolsToWires([false, true, false, false])  | 4     | true
        new Wire(true) | boolsToWires([false, true, false, true])   | 5     | true
        new Wire(true) | boolsToWires([false, true, true, false])   | 6     | true
        new Wire(true) | boolsToWires([false, true, true, true])    | 7     | true

        new Wire(true) | boolsToWires([true, false, false, false])  | 8     | true
        new Wire(true) | boolsToWires([true, false, false, true])   | 9     | true
        new Wire(true) | boolsToWires([true, false, true, false])   | 10    | true
        new Wire(true) | boolsToWires([true, false, true, true])    | 11    | true
        new Wire(true) | boolsToWires([true, true, false, false])   | 12    | true
        new Wire(true) | boolsToWires([true, true, false, true])    | 13    | true
        new Wire(true) | boolsToWires([true, true, true, false])    | 14    | true
        new Wire(true) | boolsToWires([true, true, true, true])     | 15    | true
    }

    // Helper methods

    private static List<Wire> boolsToWires(List<Boolean> signals) {
        return signals.collect({ signal -> new Wire(signal)})
    }

    private static List<Wire> intsToWires(List<Integer> signals) {
        return signals.collect({ signal -> new Wire(signal >= 1)})
    }

    private static List<Integer> wiresToInts(List<Wire> signals) {
        return signals.collect({ Wire wire -> wire.getSignal() ? 1 : 0 })
    }
}