package ComputerSimulation.computer.components

class Circuits extends Gates {

    void halfAdder(Wire a, Wire b, Wire s, Wire c) {
        Wire d = new Wire()
        Wire e = new Wire()

        OR(a, b, d)
        AND(a, b, c)
        NOT(c, e)
        AND(d, e, s)
    }

    void fullAdder(Wire a, Wire b, Wire cin, Wire sum, Wire cout) {
        Wire s = new Wire()
        Wire c1 = new Wire()
        Wire c2 = new Wire()

        halfAdder(a, cin, s, c1)
        halfAdder(b, s, sum, c2)
        OR(c1, c2, cout)
    }

    void flip_flop_NAND(Wire set, Wire reset, Wire Q, Wire Q_prim) {

        if (!set.getSignal()) {
            if (!reset.getSignal()) {
                throw new IllegalArgumentException("Forbidden input. Flip-Flop is in invalid state")
            }
        }

        NAND(set, Q_prim, Q)
        NAND(reset, Q, Q_prim)
    }

    void flip_flop_NOR(Wire reset, Wire set, Wire Q, Wire Q_prim) {

        if (reset.getSignal() && set.getSignal()) {
            throw new IllegalArgumentException("Forbidden input. Flip-Flop is in invalid state")
        }

        NOR(reset, Q_prim, Q)
        NOR(set, Q, Q_prim)
    }

}
