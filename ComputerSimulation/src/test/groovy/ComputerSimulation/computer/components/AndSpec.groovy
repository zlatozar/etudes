package ComputerSimulation.computer.components

import spock.lang.Specification

class AndSpec extends Specification {

    Circuits circuits = new Circuits()

    def "AND gate"() {

        given: "Wires to AND gate"
        Wire in1 = new Wire()
        Wire in2 = new Wire()

        when: "Signals are set"

        then: "Result should be available"
    }

}