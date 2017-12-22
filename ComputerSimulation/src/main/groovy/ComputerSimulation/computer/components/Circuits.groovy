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
    Map<String, Wire> HA(Wire in1, Wire in2) {
        return ['S': XOR(in1, in2), 'C': AND(in1, in2)]
    }

    /**
     * Full Adder using two Half Adders
     */
    Map<String, Wire> FA(Wire in1, Wire in2, Wire cin) {
        Map<String, Wire> firstHA = HA(in1, in2)
        Map<String, Wire> secondHA = HA(firstHA.S, cin)

        return ['S': secondHA.S, 'C': OR(firstHA.C, secondHA.C)]
    }

    /**
     * Set and reset signal can't be zeros at the same time
     */
    void oFLIP_FLOP_11(Wire set, Wire reset, Wire Q, Wire Q_prim) {

        stopIfAllEqualTo(0, [set, reset], 'oFLIP_FLOP_11 is in invalid state.')

        oNAND(set, Q_prim, Q)
        oNAND(reset, Q, Q_prim)
    }

    /**
     * Set and reset signal can't be ones at the same time
     */
    void oFLIP_FLOP_00(Wire set, Wire reset, Wire Q, Wire Q_prim) {

        stopIfAllEqualTo(1, [set, reset], 'oFLIP_FLOP_00 is in invalid state.')

        oNOR(reset, Q_prim, Q)
        oNOR(set, Q, Q_prim)
    }

    /**
     * Check if given words are equal
     */
    Wire COMPARATOR(List<Wire> word1, List<Wire> word2) {

        if (word1.size() != word2.size()) {
            throw new IllegalArgumentException("Compared words differ in size")
        }

        return mAND(
                [word1, word2].transpose().collect({
                    pairOfWires ->
                        XNOR(
                                ((List<Wire>) pairOfWires)[0],
                                ((List<Wire>) pairOfWires)[1])
                }))
    }

    /**
     * Invert the signal in every bit in the given word if signal
     * in a control wire is positive
     *
     * @param word bytes that possible will be inverted
     * @param ctrl defines if word signal should be inverted
     * @return inverted or not signal
     */
    List<Wire> INVERTER(List<Wire> word, Wire ctrl) {
        return word.stream().parallel().collect({
            Wire wire -> XOR(wire, ctrl)
        })
    }

    Wire MUX_16to1(List<Wire> word, List<Wire> selector) {
        wordSizeCheck(word, 16)
        wordSizeCheck(selector, 4)

        Wire A = selector[0] // MSB
        Wire B = selector[1]
        Wire C = selector[2]
        Wire D = selector[3] // LSB

        return mOR([
                mAND([NOT(A), NOT(B), NOT(C), NOT(D), word[0]]),
                mAND([NOT(A), NOT(B), NOT(C), D, word[1]]),
                mAND([NOT(A), NOT(B), C, NOT(D), word[2]]),
                mAND([NOT(A), NOT(B), C, D, word[3]]),
                mAND([NOT(A), B, NOT(C), NOT(D), word[4]]),
                mAND([NOT(A), B, NOT(C), D, word[5]]),
                mAND([NOT(A), B, C, NOT(D), word[6]]),
                mAND([NOT(A), B, C, D, word[7]]),

                mAND([A, NOT(B), NOT(C), NOT(D), word[8]]),
                mAND([A, NOT(B), NOT(C), D, word[9]]),
                mAND([A, NOT(B), C, NOT(D), word[10]]),
                mAND([A, NOT(B), C, D, word[11]]),
                mAND([A, B, NOT(C), NOT(D), word[12]]),
                mAND([A, B, NOT(C), D, word[13]]),
                mAND([A, B, C, NOT(D), word[14]]),
                mAND([A, B, C, D, word[15]])
        ])
    }

    void CLK(Wire input, int count=-1) {
        boolean sig = false

        def setSignal = {
            input.setSignal(sig)

            sleep(CLOCK)
            sig = !sig
            return
        }

        executeCLK(count, setSignal)
    }

    void CLK(List<Wire> bus, int count=-1) {
        boolean sig = false

        def setSignal = {
            bus.stream().parallel().any {
                wire ->
                    ((Wire) wire).setSignal(sig)
            }

            sleep(CLOCK)
            sig = !sig
            return
        }

        executeCLK(count, setSignal)
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
        if (wires.collect({ wire -> wire.getSignal() }).inject { in1, in2 -> in1 == in2 }) {

            // then check first one if is equal to the bit
            if (wires[0].getSignal() == bitValue) {
                println("Forbidden input: $wires")
                throw new IllegalArgumentException(message)
            }
        }
    }

    private static void executeCLK(int count, Closure<Void> setSignal) {

        if (count == -1) {
            while (true) {
                setSignal.call()
            }

        } else {

            for (int i = 0; i < count; i++) {
                setSignal.call()
            }
        }
    }

    private static void wordSizeCheck(List<Wire> word, int size) {
        if (word.size() != size) {
            throw new IllegalArgumentException("Word size should be $size bits")
        }
    }
}
