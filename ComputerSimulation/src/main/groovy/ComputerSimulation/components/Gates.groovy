package ComputerSimulation.components

import groovy.transform.CompileStatic

@CompileStatic
abstract class Gates extends Simulation implements ElementDelays {

    // AND and AND based

    Wire AND(Wire in1, Wire in2) {
        Wire out = new Wire()

        def andAction = {
            boolean in1Sig = in1.getSignal()
            boolean in2Sig = in2.getSignal()

            afterDelay(AND_delay) {
                out.setSignal(in1Sig & in2Sig)
            }
        }

        in1.addAction andAction
        in2.addAction andAction

        propagateSignal()

        return out
    }

    void AND(Wire in1, Wire in2, Wire out) {

        def andAction = {
            boolean in1Sig = in1.getSignal()
            boolean in2Sig = in2.getSignal()

            afterDelay(AND_delay) {
                out.setSignal(in1Sig & in2Sig)
            }
        }

        in1.addAction andAction
        in2.addAction andAction
    }

    Wire NAND(Wire in1, Wire in2) {
        return NOT(AND(in1, in2))
    }

    void NAND(Wire in1, Wire in2, Wire out) {
        Wire andOut = AND(in1, in2)
        NOT(andOut, out)
    }

    // OR and OR based

    Wire OR(Wire in1, Wire in2) {
        Wire out = new Wire()

        def orAction = {
            boolean in1Sig = in1.getSignal()
            boolean in2Sig = in2.getSignal()

            afterDelay(OR_delay) {
                out.setSignal(in1Sig | in2Sig)
            }
        }

        in1.addAction orAction
        in2.addAction orAction

        propagateSignal()

        return out
    }

    void OR(Wire in1, Wire in2, Wire out) {

        def orAction = {
            boolean in1Sig = in1.getSignal()
            boolean in2Sig = in2.getSignal()

            afterDelay(OR_delay) {
                out.setSignal(in1Sig | in2Sig)
            }
        }

        in1.addAction orAction
        in2.addAction orAction
    }

    Wire NOR(Wire in1, Wire in2) {
        return NOT(OR(in1, in2))
    }

    void NOR(Wire in1, Wire in2, Wire out) {
        Wire orOut = OR(in1, in2)
        NOT(orOut, out)
    }

    Wire NOT(Wire input) {

        Wire out = new Wire()

        def notAction = {
            boolean inputSig = input.getSignal()

            afterDelay(NOT_delay) {
                out.setSignal(!inputSig)
            }
        }

        input.addAction notAction

        propagateSignal()

        return out
    }

    void NOT(Wire input, Wire out) {

        def notAction = {
            boolean inputSig = input.getSignal()

            afterDelay(NOT_delay) {
                out.setSignal(!inputSig)
            }
        }

        input.addAction notAction
    }

}
