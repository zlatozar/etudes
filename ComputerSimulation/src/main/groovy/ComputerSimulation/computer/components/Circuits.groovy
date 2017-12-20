package ComputerSimulation.computer.components

import groovy.transform.CompileStatic

/**
 * Digital components based on defined gates
 */
@CompileStatic
class Circuits extends Gates {

    /**
     * Half Adder
     */
    Map<String, Wire> HA(Wire in1, Wire in2){
        return ['S' : XOR(in1, in2), 'C' : AND(in1, in2)]
    }

    /**
     * Full Adder using Half Adder
     */
    Map<String, Wire> FA(Wire in1, Wire in2, Wire cin) {
        Map<String, Wire> firstHA = HA(in1, in2)
        Map<String, Wire> secondHA = HA(firstHA.S, cin)

        return ['S' : secondHA.S, 'C' : OR(firstHA.C, secondHA.C)]
    }

    /**
     * Set and reset signal can't be zeros at the same time
     */
    void FlFpNAND(Wire set, Wire reset, Wire Q, Wire Q_prim) {

        stopIfAllEqualTo(0, [set, reset], 'FlFpNAND is in invalid state.')

        oNAND(set, Q_prim, Q)
        oNAND(reset, Q, Q_prim)
    }

    /**
     * Set and reset signal can't be ones at the same time
     */
    void FlFpNOR(Wire set, Wire reset, Wire Q, Wire Q_prim) {

        stopIfAllEqualTo(1, [set, reset], 'FlFpNOR is in invalid state.')

        oNOR(reset, Q_prim, Q)
        oNOR(set, Q, Q_prim)
    }

    // Helper methods

    /**
     * Checks if the signal is the same on all wires
     * and if this signal is the same as the forbidden one
     *
     * @param bit forbidden value
     * @param wires all wires
     * @param message message to be printed in case of error
     */
    private static void stopIfAllEqualTo(int bit, List<Wire> wires, String message) {

        if (wires.isEmpty()) {
            throw new IllegalArgumentException('There is no wires')
        }

        boolean bitValue = bit >= 1

        // all are equal
        if (wires.collect({wire -> wire.getSignal()}).inject { in1, in2 ->  in1 == in2 }) {

            // then check first one if is equal to the bit
            if (wires[0].getSignal() == bitValue) {
                println("Forbidden input: $wires")
                throw new IllegalArgumentException(message)
            }
        }
    }

}
