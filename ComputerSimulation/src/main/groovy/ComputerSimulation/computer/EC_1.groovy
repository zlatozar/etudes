package ComputerSimulation.computer

import ComputerSimulation.computer.components.Gates
import ComputerSimulation.computer.components.InputSignal
import ComputerSimulation.computer.components.Wire

class EC_1 extends Gates {

    static void simpleTest() {

        Wire A = new Wire('A')
        Wire B = new Wire('B')

        // truth table
        InputSignal.emit([ (A) : [0, 0, 1, 1], (B) : [0, 1, 0, 1]])

        // logic
        XOR(A, B).getSignal().subscribe({it -> print("$it ")})
    }

    public static void main(String[] args) {
        EC_1.simpleTest()
    }
}
