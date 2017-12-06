package ComputerSimulation.computer

import ComputerSimulation.computer.components.Gates
import ComputerSimulation.computer.components.InputSignal
import ComputerSimulation.computer.components.Wire
import rx.Observable

class EC_1 extends Gates {

    void AND_test(Observable<Boolean> in1, Observable<Boolean> in2) {

        Wire A = new Wire('A')
        Wire B = new Wire()

        // pass signal
        A.setSignal(in1)
        B.setSignal(in2)

        // logic
        print("\nAND gate result: ")
        AND(A, B).subscribe({it -> print("$it ")})
    }

    public static void main(String[] args) {
        EC_1 comp = new EC_1()

        comp.AND_test(InputSignal.emit([1, 1, 0]), InputSignal.emit([1, 1, 0]))
    }
}
