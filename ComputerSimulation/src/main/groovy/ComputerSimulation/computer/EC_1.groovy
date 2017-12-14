package ComputerSimulation.computer

import ComputerSimulation.computer.components.Circuits
import ComputerSimulation.computer.components.Wire

class EC_1 extends Circuits {

    Wire A = new Wire('A')
    Wire B = new Wire('B')

    public static void main(String[] args) {
        EC_1 ec1 = new EC_1()
        ec1.emulate()
    }

    // Entry point

    void emulate() {
        emit([// A, B
              [0, 0],
              [0, 1],
              [1, 1]
        ], this.&AND, [A, B])

        println '\n\nWork in progress...'
    }

    // Helper methods

    private void emit(List<List<Integer>> input, Closure circuit, List<Wire> wires, boolean dump=true) {

        checkSignalSets(input, wires.size())

        for (List<Integer> pair : input) {

            for (List<List> bite : [pair, wires].transpose()) {
                bite.stream().parallel().any {
                    it -> bite[1].setSignal(bite[0] >= 1)
                }
            }

            if (dump) {
                println circuit.call(A, B)

            } else {
                circuit.call(A, B)
            }

            sleep(CLOCK)
        }
    }

    private static void checkSignalSets(List<List<Integer>> input, int numberOfWires) {

        int size = numberOfWires
        for (List<Integer> signalSet : input) {

            if (size != signalSet.size()) {
                throw new IllegalArgumentException('Signal set are with different lengths...')
            }

        }
    }
}
