package ComputerSimulation.computer.components

import spock.lang.Specification
import spock.lang.Unroll

class GatesSpec extends Specification {

    Circuits circuits = new Circuits()

    @Unroll
    def "AND: #a and #b is '#c'"() {

        expect:
        circuits.AND(new Wire(a), new Wire(b)).getSignal() == c

        where:
        a     | b      | c
        false | false  | false
        false | true   | false
        true  | false  | false
        true  | true   | true
    }

    @Unroll
    def "OR: #a and #b is '#c'"() {

        expect:
        circuits.OR(new Wire(a), new Wire(b)).getSignal() == c

        where:
        a     | b      | c
        false | false  | false
        false | true   | true
        true  | false  | true
        true  | true   | true
    }

}