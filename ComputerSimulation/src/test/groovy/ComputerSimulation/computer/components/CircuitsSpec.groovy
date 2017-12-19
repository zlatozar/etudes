package ComputerSimulation.computer.components

import spock.lang.Specification
import spock.lang.Unroll


class CircuitsSpec extends Specification {

    private Circuits circuits = new Circuits()

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
}