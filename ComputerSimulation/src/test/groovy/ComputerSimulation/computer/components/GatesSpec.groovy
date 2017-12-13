package ComputerSimulation.computer.components

import spock.lang.Specification

class GatesSpec extends Specification {

    Circuits circuits = new Circuits()

    def "Half adder"() {

        given: "Wires to attach half adder"
        Wire in1 = new Wire()
        Wire in2 = new Wire()
        Wire sum = new Wire()
        Wire carry = new Wire()

        circuits.halfAdder(in1, in2, sum, carry)
        println("sum=$sum")
        println("carry=$carry")

        when: "Signals are set"

        in1.setSignal(true)
        circuits.propagateSignal()

        in2.setSignal(true)
        circuits.propagateSignal()

        in1.setSignal(false)
        circuits.propagateSignal()

        then: "Result should be available"
    }

}