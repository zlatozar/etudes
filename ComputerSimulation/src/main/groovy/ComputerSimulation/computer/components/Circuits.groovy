package ComputerSimulation.computer.components

import groovy.transform.CompileStatic

@CompileStatic
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

        if (!reset.getSignal() && !set.getSignal()) {
            throw new IllegalArgumentException("Flip-Flop invalid state")
        }

        NAND(set, Q_prim, Q)
        NAND(reset, Q, Q_prim)
    }

    void flip_flop_NOR(Wire reset, Wire set, Wire Q, Wire Q_prim) {

        if (reset.getSignal() && set.getSignal()) {
            throw new IllegalArgumentException("Flip-Flop invalid state")
        }

        NOR(reset, Q_prim, Q)
        NOR(set, Q, Q_prim)
    }

    void simple_test() {

        Wire SET = new Wire()
        Wire RESET = new Wire()

        Wire Q = new Wire()
        Wire Q_prim = new Wire()

        // Simulation

        SET.setSignal(false)
        RESET.setSignal(false)

        println("\nSET=$SET, RESET=$RESET (init)\n")

        flip_flop_NOR(RESET, SET, Q, Q_prim)
        propagateSignal()

        println("Store: $Q")
        println("Reset=$Q_prim")

        SET.setSignal(false)
        RESET.setSignal(true)
        println("\nSET=$SET, RESET=$RESET (reset state)\n")

        flip_flop_NOR(RESET, SET, Q, Q_prim)
        propagateSignal()

        println("Store: $Q")
        println("Reset=$Q_prim")

        SET.setSignal(true)
        RESET.setSignal(false)
        println("\nSET=$SET, RESET=$RESET\n")

        flip_flop_NOR(RESET, SET, Q, Q_prim)
        propagateSignal()

        println("Store: $Q")
        println("Reset=$Q_prim")

        SET.setSignal(false)
        RESET.setSignal(false)

        println("\nSET=$SET, RESET=$RESET (again)\n")

        flip_flop_NOR(RESET, SET, Q, Q_prim)
        propagateSignal()

        println("Store: $Q")
        println("Reset=$Q_prim")

        SET.setSignal(true)
        RESET.setSignal(false)

        println("\nSET=$SET, RESET=$RESET (no effect)\n")

        flip_flop_NOR(RESET, SET, Q, Q_prim)
        propagateSignal()

        println("Store: $Q")
        println("Reset=$Q_prim")

        SET.setSignal(true)
        RESET.setSignal(true)

        println("\nSET=$SET, RESET=$RESET (invalid state)\n")

        flip_flop_NOR(RESET, SET, Q, Q_prim)
        propagateSignal()

    }

    // simple test run
    public static void main(String[] args) {
        Circuits circuits = new Circuits()

        circuits.simple_test()
    }

}
