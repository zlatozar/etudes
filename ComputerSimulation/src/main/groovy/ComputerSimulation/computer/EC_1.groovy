package ComputerSimulation.computer

import ComputerSimulation.computer.components.Elements
import ComputerSimulation.computer.components.InputSignal
import ComputerSimulation.computer.components.Wire

class EC_1 extends Elements {

    static void simpleTest() {

        Wire A = new Wire('A')
        Wire B = new Wire('B')
        Wire C = new Wire()

        // truth table
        InputSignal.emit([ (A) : [0, 0, 1, 1], (B) : [0, 1, 0, 1], (C) : [0, 0, 0, 0] ])

        // logic
        println '\nSimple test result:'

        SR_flip_flop(A, B).Q.getSignal().subscribe({ it -> print("$it ")})

        println '\n\nDone'
    }

    public static void main(String[] args) {
        simpleTest()
    }
}
