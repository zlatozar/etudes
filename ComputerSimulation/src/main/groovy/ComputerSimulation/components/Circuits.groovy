package ComputerSimulation.components

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

    void or_test() {
        Wire a = new Wire()
        Wire b = new Wire()
        Wire out = new Wire()

        AND(a, b, out)
        probe("out", out)

        // Simulation

        a.setSignal(true)
        run()
    }

    public static void main(String[] args) {
        Circuits circuits = new Circuits()

        circuits.or_test()
    }

}
