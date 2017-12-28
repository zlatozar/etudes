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
     * Binary Adder
     */
    Map<String, Object> BA16(List<Wire> A, List<Wire> B) {
        wordSizeCheck(A, 16)
        wordSizeCheck(B, 16)

        Map<String, Wire> HA1 = HA(A[15], B[15])          // LSB
        Map<String, Wire> FA1 = FA(A[14], B[14], HA1.C)
        Map<String, Wire> FA2 = FA(A[13], B[13], FA1.C)
        Map<String, Wire> FA3 = FA(A[12], B[12], FA2.C)
        Map<String, Wire> FA4 = FA(A[11], B[11], FA3.C)
        Map<String, Wire> FA5 = FA(A[10], B[10], FA4.C)
        Map<String, Wire> FA6 = FA(A[9], B[9], FA5.C)
        Map<String, Wire> FA7 = FA(A[8], B[8], FA6.C)

        Map<String, Wire> FA8 = FA(A[7], B[7], FA7.C)
        Map<String, Wire> FA9 = FA(A[6], B[6], FA8.C)
        Map<String, Wire> FA10 = FA(A[5], B[5], FA9.C)
        Map<String, Wire> FA11 = FA(A[4], B[4], FA10.C)
        Map<String, Wire> FA12 = FA(A[3], B[3], FA11.C)
        Map<String, Wire> FA13 = FA(A[2], B[2], FA12.C)
        Map<String, Wire> FA14 = FA(A[1], B[1], FA13.C)
        Map<String, Wire> FA15 = FA(A[0], B[0], FA14.C)   // MSB

        final List<Wire> out = [
                FA15.S,
                FA14.S,
                FA13.S,
                FA12.S,
                FA11.S,
                FA10.S,
                FA9.S,
                FA8.S,
                FA7.S,
                FA6.S,
                FA5.S,
                FA4.S,
                FA3.S,
                FA2.S,
                FA1.S,
                HA1.S
        ]

        return ['S' : out, 'C' : FA15.C]
    }

    /**
     * Adder-Subtracter
     */
    List<Wire> AS16(List<Wire> A, List<Wire> B, Wire sub) {
        wordSizeCheck(A, 16)
        wordSizeCheck(B, 16)

        Map<String, Wire> FA0 = FA(A[15], XOR(B[15], sub) , sub)   // LSB
        Map<String, Wire> FA1 = FA(A[14], XOR(B[14], sub), FA0.C)
        Map<String, Wire> FA2 = FA(A[13], XOR(B[13], sub), FA1.C)
        Map<String, Wire> FA3 = FA(A[12], XOR(B[12], sub), FA2.C)
        Map<String, Wire> FA4 = FA(A[11], XOR(B[11], sub), FA3.C)
        Map<String, Wire> FA5 = FA(A[10], XOR(B[10], sub), FA4.C)
        Map<String, Wire> FA6 = FA(A[9], XOR(B[9], sub), FA5.C)
        Map<String, Wire> FA7 = FA(A[8], XOR(B[8], sub), FA6.C)

        Map<String, Wire> FA8 = FA(A[7], XOR(B[7], sub), FA7.C)
        Map<String, Wire> FA9 = FA(A[6], XOR(B[6], sub), FA8.C)
        Map<String, Wire> FA10 = FA(A[5], XOR(B[5], sub), FA9.C)
        Map<String, Wire> FA11 = FA(A[4], XOR(B[4], sub), FA10.C)
        Map<String, Wire> FA12 = FA(A[3], XOR(B[3], sub), FA11.C)
        Map<String, Wire> FA13 = FA(A[2], XOR(B[2], sub), FA12.C)
        Map<String, Wire> FA14 = FA(A[1], XOR(B[1], sub), FA13.C)
        Map<String, Wire> FA15 = FA(A[0], XOR(B[0], sub), FA14.C)   // MSB

        final List<Wire> out = [
                FA15.S,
                FA14.S,
                FA13.S,
                FA12.S,
                FA11.S,
                FA10.S,
                FA9.S,
                FA8.S,
                FA7.S,
                FA6.S,
                FA5.S,
                FA4.S,
                FA3.S,
                FA2.S,
                FA1.S,
                FA0.S
        ]

        return out
    }

    /**
     * NAND implementation. Set and reset signal can't be zeros at the same time
     */
    protected void oSR_Latch_11(Wire set, Wire reset, Wire Q, Wire Q_prim) {

        stopIfAllEqualTo(0, [set, reset], 'Race condition. oSR_Latch_11 is in invalid state.')

        oNAND(set, Q_prim, Q)
        oNAND(reset, Q, Q_prim)
    }

    /**
     * NOR implementation. Set and reset signal can't be ones at the same time
     */
    protected void oSR_Latch_00(Wire set, Wire reset, Wire Q, Wire Q_prim) {

        stopIfAllEqualTo(1, [set, reset], 'Race condition. oSR_Latch_00 is in invalid state.')

        oNOR(reset, Q_prim, Q)
        oNOR(set, Q, Q_prim)
    }

    /**
     * D latch with no illegal inputs
     */
    protected void oD_Latch(Wire d, Wire clk, Wire Q, Wire Q_prim) {
        oSR_Latch_11(NAND(d, clk), NAND(NOT(d), clk), Q, Q_prim)
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

        Wire D = selector[0] // MSB
        Wire C = selector[1]
        Wire B = selector[2]
        Wire A = selector[3] // LSB

        return mOR([
                mAND([NOT(D), NOT(C), NOT(B), NOT(A), word[0]]),
                mAND([NOT(D), NOT(C), NOT(B), A, word[1]]),
                mAND([NOT(D), NOT(C), B, NOT(A), word[2]]),
                mAND([NOT(D), NOT(C), B, A, word[3]]),
                mAND([NOT(D), C, NOT(B), NOT(A), word[4]]),
                mAND([NOT(D), C, NOT(B), A, word[5]]),
                mAND([NOT(D), C, B, NOT(A), word[6]]),
                mAND([NOT(D), C, B, A, word[7]]),

                mAND([D, NOT(C), NOT(B), NOT(A), word[8]]),
                mAND([D, NOT(C), NOT(B), A, word[9]]),
                mAND([D, NOT(C), B, NOT(A), word[10]]),
                mAND([D, NOT(C), B, A, word[11]]),
                mAND([D, C, NOT(B), NOT(A), word[12]]),
                mAND([D, C, NOT(B), A, word[13]]),
                mAND([D, C, B, NOT(A), word[14]]),
                mAND([D, C, B, A, word[15]])
        ])
    }

    List<Wire> DEMUX_1to16(Wire data, List<Wire> selector) {
        wordSizeCheck(selector, 4)

        Wire D = selector[0] // MSB
        Wire C = selector[1]
        Wire B = selector[2]
        Wire A = selector[3] // LSB

        final List<Wire> out = [
                mAND([NOT(D), NOT(C), NOT(B), NOT(A), data]),
                mAND([NOT(D), NOT(C), NOT(B), A, data]),
                mAND([NOT(D), NOT(C), B, NOT(A), data]),
                mAND([NOT(D), NOT(C), B, A, data]),
                mAND([NOT(D), C, NOT(B), NOT(A), data]),
                mAND([NOT(D), C, NOT(B), A, data]),
                mAND([NOT(D), C, B, NOT(A), data]),
                mAND([NOT(D), C, B, A, data]),

                mAND([D, NOT(C), NOT(B), NOT(A), data]),
                mAND([D, NOT(C), NOT(B), A, data]),
                mAND([D, NOT(C), B, NOT(A), data]),
                mAND([D, NOT(C), B, A, data]),
                mAND([D, C, NOT(B), NOT(A), data]),
                mAND([D, C, NOT(B), A, data]),
                mAND([D, C, B, NOT(A), data]),
                mAND([D, C, B, A, data])
        ]

        return out
    }

    void CLK(Wire input, int count = -1) {
        boolean sig = false

        def setSignal = {
            input.setSignal(sig)

            sleep(CLOCK)
            sig = !sig
            return
        }

        executeCLK(count, setSignal)
    }

    void CLK(List<Wire> bus, int count = -1) {
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
