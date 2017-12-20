package ComputerSimulation.computer.components

import spock.lang.Specification
import spock.lang.Unroll

class GatesSpec extends Specification {

    private Circuits circuits = new Circuits()

    @Unroll
    def "AND: #a and #b is '#result'"() {

        expect:
        circuits.AND(new Wire(a), new Wire(b)).getSignal() == result

        where:
        a     | b      | result
        false | false  | false
        false | true   | false
        true  | false  | false
        true  | true   | true
    }

    @Unroll
    def "mAND: #a, #b and #c is '#result'"() {

        expect:
        circuits.mAND([new Wire(a), new Wire(b), new Wire(c)]).getSignal() == result

        where:
        a     | b      | c      | result
        false | false  | false  | false
        false | true   | true   | false
        false | false  | true   | false
        true  | true   | true   | true
        true  | true   | false  | false
        true  | false  | false  | false
        false | true   | false  | false
        true  | false  | true   | false
    }

    @Unroll
    def "oAND: #a and #b is '#result'"() {

        expect:
        Wire out = new Wire()

        circuits.oAND(new Wire(a), new Wire(b), out)
        out.getSignal() == result

        where:
        a     | b      | result
        false | false  | false
        false | true   | false
        true  | false  | false
        true  | true   | true
    }

    @Unroll
    def "NAND: #a and #b is '#result'"() {

        expect:
        circuits.NAND(new Wire(a), new Wire(b)).getSignal() == result

        where:
        a     | b      | result
        false | false  | true
        false | true   | true
        true  | false  | true
        true  | true   | false
    }

    @Unroll
    def "mNAND: #a, #b and #c is '#result'"() {

        expect:
        circuits.mNAND([new Wire(a), new Wire(b), new Wire(c)]).getSignal() == result

        where:
        a     | b      | c      | result
        false | false  | false  | true
        false | true   | true   | true
        false | false  | true   | true
        true  | true   | true   | false
        true  | true   | false  | true
        true  | false  | false  | true
        false | true   | false  | true
        true  | false  | true   | true
    }

    @Unroll
    def "oNAND: #a and #b is '#result'"() {

        expect:
        Wire out = new Wire()

        circuits.oNAND(new Wire(a), new Wire(b), out)
        out.getSignal() == result

        where:
        a     | b      | result
        false | false  | true
        false | true   | true
        true  | false  | true
        true  | true   | false
    }

    @Unroll
    def "OR: #a and #b is '#result'"() {

        expect:
        circuits.OR(new Wire(a), new Wire(b)).getSignal() == result

        where:
        a     | b      | result
        false | false  | false
        false | true   | true
        true  | false  | true
        true  | true   | true
    }

    @Unroll
    def "mOR: #a, #b and #c is '#result'"() {

        expect:
        circuits.mOR([new Wire(a), new Wire(b), new Wire(c)]).getSignal() == result

        where:
        a     | b      | c      | result
        false | false  | false  | false
        false | true   | true   | true
        false | false  | true   | true
        true  | true   | true   | true
        true  | true   | false  | true
        true  | false  | false  | true
        false | true   | false  | true
        true  | false  | true   | true
    }

    @Unroll
    def "oOR: #a and #b is '#result'"() {

        expect:
        Wire out = new Wire()

        circuits.oOR(new Wire(a), new Wire(b), out)
        out.getSignal() == result

        where:
        a     | b      | result
        false | false  | false
        false | true   | true
        true  | false  | true
        true  | true   | true
    }

    @Unroll
    def "NOR: #a and #b is '#result'"() {

        expect:
        circuits.NOR(new Wire(a), new Wire(b)).getSignal() == result

        where:
        a     | b      | result
        false | false  | true
        false | true   | false
        true  | false  | false
        true  | true   | false
    }

    @Unroll
    def "mNOR: #a, #b and #c is '#result'"() {

        expect:
        circuits.mNOR([new Wire(a), new Wire(b), new Wire(c)]).getSignal() == result

        where:
        a     | b      | c      | result
        false | false  | false  | true
        false | true   | true   | false
        false | false  | true   | false
        true  | true   | true   | false
        true  | true   | false  | false
        true  | false  | false  | false
        false | true   | false  | false
        true  | false  | true   | false
    }

    @Unroll
    def "oNOR: #a and #b is '#result'"() {

        expect:
        Wire out = new Wire()

        circuits.oNOR(new Wire(a), new Wire(b), out)
        out.getSignal() == result

        where:
        a     | b      | result
        false | false  | true
        false | true   | false
        true  | false  | false
        true  | true   | false
    }

    @Unroll
    def "XOR: #a and #b is '#result'"() {

        expect:
        circuits.XOR(new Wire(a), new Wire(b)).getSignal() == result

        where:
        a     | b      | result
        false | false  | false
        false | true   | true
        true  | false  | true
        true  | true   | false
    }

    @Unroll
    def "mXOR: #a, #b and #c is '#result'"() {

        expect:
        circuits.mXOR([new Wire(a), new Wire(b), new Wire(c)]).getSignal() == result

        where:
        a     | b     | c     | result
        false | false | false | false
        false | false | true  | true
        false | true  | false | true
        false | true  | true  | false
        true  | false | false | true
        true  | false | true  | false
        true  | true  | false | false
        true  | true  | true  | true
    }

    @Unroll
    def "oXOR: #a and #b is '#result'"() {

        expect:
        Wire out = new Wire()

        circuits.oXOR(new Wire(a), new Wire(b), out)
        out.getSignal() == result

        where:
        a     | b      | result
        false | false  | false
        false | true   | true
        true  | false  | true
        true  | true   | false
    }

    @Unroll
    def "XNOR: #a and #b is '#result'"() {

        expect:
        circuits.XNOR(new Wire(a), new Wire(b)).getSignal() == result

        where:
        a     | b      | result
        false | false  | true
        false | true   | false
        true  | false  | false
        true  | true   | true
    }

    @Unroll
    def "mXNOR: #a, #b and #c is '#result'"() {

        expect:
        circuits.mXNOR([new Wire(a), new Wire(b), new Wire(c)]).getSignal() == result

        where:
        a     | b     | c     | result
        false | false | false | true
        false | false | true  | false
        false | true  | false | false
        false | true  | true  | true
        true  | false | false | false
        true  | false | true  | true
        true  | true  | false | true
        true  | true  | true  | false
    }

    @Unroll
    def "oXNOR: #a and #b is '#result'"() {

        expect:
        Wire out = new Wire()

        circuits.oXNOR(new Wire(a), new Wire(b), out)
        out.getSignal() == result

        where:
        a     | b      | result
        false | false  | true
        false | true   | false
        true  | false  | false
        true  | true   | true
    }

    @Unroll
    def "NOT: #a is '#result'"() {

        expect:
        circuits.NOT(new Wire(a)).getSignal() == result

        where:
        a     | result
        false | true
        false | true
        true  | false
        true  | false
    }

    @Unroll
    def "oNOT: #a is '#result'"() {

        expect:
        Wire out = new Wire()

        circuits.oNOT(new Wire(a), out)
        out.getSignal() == result

        where:
        a     | result
        false | true
        false | true
        true  | false
        true  | false
    }

}