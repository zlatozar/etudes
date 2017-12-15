package ComputerSimulation.computer.components

/**
 * Digital components based on defined gates
 */
class Circuits extends Gates {

    void halfAdder(Wire a, Wire b, Wire s, Wire c) {
        Wire d = new Wire()
        Wire e = new Wire()

        oOR(a, b, d)
        oAND(a, b, c)
        oNOT(c, e)
        oAND(d, e, s)
    }

    void fullAdder(Wire a, Wire b, Wire cin, Wire sum, Wire cout) {
        Wire s = new Wire()
        Wire c1 = new Wire()
        Wire c2 = new Wire()

        halfAdder(a, cin, s, c1)
        halfAdder(b, s, sum, c2)
        oOR(c1, c2, cout)
    }

    void flipFlopPositive(Wire set, Wire reset, Wire Q, Wire Q_prim) {

        if (!set.getSignal()) {
            if (!reset.getSignal()) {
                throw new IllegalArgumentException("Forbidden input. Positive flip-flop is in invalid state (two 1's are passed)")
            }
        }

        oNAND(set, Q_prim, Q)
        oNAND(reset, Q, Q_prim)
    }

    void flipFlopNegative(Wire reset, Wire set, Wire Q, Wire Q_prim) {

        if (reset.getSignal() && set.getSignal()) {
            throw new IllegalArgumentException("Forbidden input. Negative flip-Flop is in invalid state (two 0's are passed)")
        }

        oNOR(reset, Q_prim, Q)
        oNOR(set, Q, Q_prim)
    }

}
