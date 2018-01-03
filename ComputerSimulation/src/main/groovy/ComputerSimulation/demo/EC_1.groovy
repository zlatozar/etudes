package ComputerSimulation.demo

import ComputerSimulation.computer.components.Circuits
import ComputerSimulation.computer.components.Wire

class EC_1 extends Circuits {

    Wire A = new Wire('A')
    Wire B = new Wire('B')

    // Entry point

    public static void main(String[] args) {
        EC_1 ec1 = new EC_1()

        ec1.emulate()
    }

    /**
     * Start computer emulation
     */
    void emulate() {
        emit([// A, B
              [0, 0],
              [0, 1],
              [1, 0],
              [1, 1]
        ], this.&mAND, [A, B])

        println '\n\nWork in progress...'
    }

    // Helper methods

    /**
     * Starts emitting signals with defined clock speed
     *
     * @param input Signal set. Every position should have corresponding wire
     * @param circuit The digital component definition
     * @param inputWires Input wires. There number should be equal to the signal set length.
     * @param dump Enable/disable log messages
     */
    private void emit(List<List<Integer>> input, Closure circuit, List<Wire> inputWires) {

        checkSignalSets(input, inputWires.size())

        for (List<Integer> pair : input) {

            for (List<List> bite : [pair, inputWires].transpose()) {
                bite.stream().parallel().any {
                    it -> ((Wire) bite[1]).setSignal(bite[0] >= 1)
                }

                clearSignal()
            }

            println circuit.call(inputWires)

            // when output wires are passed
//            circuit.call(inputWires, outputWires)
//            println outputWires

            sleep(CLOCK)
        }
    }

    /**
     * The size of the passed word should be equal of the number
     * of input wires
     *
     * @param input input words set
     * @param numberOfWires number of input wires
     */
    private static void checkSignalSets(List<List<Integer>> input, int numberOfWires) {

        int size = numberOfWires
        for (List<Integer> signalSet : input) {

            if (size != signalSet.size()) {
                throw new IllegalArgumentException('Signal set should contain sets with equal length...')
            }

        }
    }
}
