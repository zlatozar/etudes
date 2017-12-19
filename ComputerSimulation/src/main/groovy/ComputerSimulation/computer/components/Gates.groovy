package ComputerSimulation.computer.components

import groovy.transform.CompileStatic

@CompileStatic
abstract class Gates extends Simulation implements Delays {

    // AND and AND based

    Wire AND(Wire in1, Wire in2) {
        final Wire out = new Wire()

        def andAction = {
            boolean in1Sig = in1.getSignal()
            boolean in2Sig = in2.getSignal()

            afterDelay(AND_delay) {
                out.setSignal(in1Sig & in2Sig)
            }
        }

        in1.addAction(andAction)
        in2.addAction(andAction)

        propagateSignal()

        return out
    }

    Wire mAND(List<Wire> input) {
        final Wire out = new Wire()

        def andAction = {
            afterDelay(AND_delay) {
                out.setSignal(input.collect {wire -> wire.getSignal()}.inject({ in1, in2 -> in1 & in2 }))
            }
        }

        input.stream().parallel().any({ Wire it -> it.addAction(andAction) })
        propagateSignal()

        return out
    }

    void oAND(Wire in1, Wire in2, Wire out) {

        def andAction = {
            boolean in1Sig = in1.getSignal()
            boolean in2Sig = in2.getSignal()

            afterDelay(AND_delay) {
                out.setSignal(in1Sig & in2Sig)
            }
        }

        in1.addAction(andAction)
        in2.addAction(andAction)

        propagateSignal()
    }

    Wire NAND(Wire in1, Wire in2) {
        return NOT(AND(in1, in2))
    }

    Wire mNAND(List<Wire> input) {
        return NOT(mAND(input))
    }

    void oNAND(Wire in1, Wire in2, Wire out) {
        Wire andOut = AND(in1, in2)
        oNOT(andOut, out)
    }

    // OR and OR based

    Wire OR(Wire in1, Wire in2) {
        final Wire out = new Wire()

        def orAction = {
            boolean in1Sig = in1.getSignal()
            boolean in2Sig = in2.getSignal()

            afterDelay(OR_delay) {
                out.setSignal(in1Sig | in2Sig)
            }
        }

        in1.addAction(orAction)
        in2.addAction(orAction)

        propagateSignal()

        return out
    }

    Wire mOR(List<Wire> input) {
        final Wire out = new Wire()

        def andAction = {
            afterDelay(AND_delay) {
                out.setSignal(input.collect({wire -> wire.getSignal()}).inject { in1, in2 -> in1 | in2 })
            }
        }

        input.stream().parallel().any({ Wire it -> it.addAction(andAction) })
        propagateSignal()

        return out
    }

    void oOR(Wire in1, Wire in2, Wire out) {

        def orAction = {
            boolean in1Sig = in1.getSignal()
            boolean in2Sig = in2.getSignal()

            afterDelay(OR_delay) {
                out.setSignal(in1Sig | in2Sig)
            }
        }

        in1.addAction(orAction)
        in2.addAction(orAction)

        propagateSignal()
    }

    Wire NOR(Wire in1, Wire in2) {
        return NOT(OR(in1, in2))
    }

    Wire mNOR(List<Wire> input) {
        return NOT(mOR(input))
    }

    void oNOR(Wire in1, Wire in2, Wire out) {
        Wire orOut = OR(in1, in2)
        oNOT(orOut, out)
    }

    Wire XOR(Wire in1, Wire in2) {
        final Wire nand1_out = new Wire()

        oNAND(in1, in2, nand1_out)
        Wire nand1_1 = NAND(in1, nand1_out)
        Wire nand1_2 = NAND(nand1_out, in2)

        return NAND(nand1_1, nand1_2)
    }

    void oXOR(Wire in1, Wire in2, Wire out) {
        final Wire nand1_out = new Wire()

        oNAND(in1, in2, nand1_out)

        Wire nand1_1 = NAND(in1, nand1_out)
        Wire nand1_2 = NAND(nand1_out, in2)

        oNAND(nand1_1, nand1_2, out)
    }

    Wire NOT(Wire input) {
        final Wire out = new Wire()

        def notAction = {
            boolean inputSig = input.getSignal()

            afterDelay(NOT_delay) {
                out.setSignal(!inputSig)
            }
        }

        input.addAction(notAction)
        propagateSignal()

        return out
    }

    void oNOT(Wire input, Wire out) {

        def notAction = {
            boolean inputSig = input.getSignal()

            afterDelay(NOT_delay) {
                out.setSignal(!inputSig)
            }
        }

        input.addAction(notAction)
        propagateSignal()
    }

    // BRANCH

    Wire BRANCH(Wire input) {
        final Wire branch = new Wire()
        branch.setSignal(input.getSignal())

        return branch
    }
}
