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

    void FlFpPos(Wire set, Wire reset, Wire Q, Wire Q_prim) {

        if (!set.getSignal()) {
            if (!reset.getSignal()) {
                throw new IllegalArgumentException("Forbidden input. Positive flip-flop is in invalid state (two 1's are passed)")
            }
        }

        oNAND(set, Q_prim, Q)
        oNAND(reset, Q, Q_prim)
    }

    void FlFpNeg(Wire reset, Wire set, Wire Q, Wire Q_prim) {

        if (reset.getSignal() && set.getSignal()) {
            throw new IllegalArgumentException("Forbidden input. Negative flip-Flop is in invalid state (two 0's are passed)")
        }

        oNOR(reset, Q_prim, Q)
        oNOR(set, Q, Q_prim)
    }

}
