package ComputerSimulation.computer.components

class Elements extends Gates {

    static final Map<String, Wire> Half_adder(Wire in1, Wire in2) {

        final Wire sum = XOR(in1, in2)
        final Wire cout = AND(in1, in2)

        return [ 'S' : sum, 'Cout' : cout ]
    }

    static final Map<String, Wire> Full_adder(Wire in1, Wire in2, Wire cin) {

        final Map<String, Wire> halfAdder1 = Half_adder(in1, in2)
        final Map<String, Wire> halfAdder2 = Half_adder(cin, halfAdder1['S'])

        return [ 'S' : halfAdder2['S'], 'Cout' : OR(halfAdder1['Cout'], halfAdder2['Cout']) ]
    }

    // FIXME: this approach doesn't work...
    static final Map<String, Wire> SR_flip_flop(Wire set, Wire reset) {
        Wire A_nand_out = new Wire()
        Wire B_nand_out = new Wire()

        NAND(set, B_nand_out, A_nand_out)
        NAND(reset, A_nand_out, B_nand_out)

        return  [ 'Q' : A_nand_out, 'notQ' : B_nand_out ]
    }
}
