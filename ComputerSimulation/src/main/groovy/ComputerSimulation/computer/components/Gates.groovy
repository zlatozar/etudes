package ComputerSimulation.computer.components

abstract class Gates extends Simulation implements Delays {

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

        in1.addAction(andAction)
        in2.addAction(andAction)

        propagateSignal()

        return out
    }

    Wire mAND(List<Wire> input) {
        Wire out = new Wire()

        def andAction = {
            afterDelay(AND_delay) {
                out.setSignal(input.inject { Wire in1, Wire in2 -> in1.getSignal() & in2.getSignal() })
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

    void oNAND(Wire in1, Wire in2, Wire out) {
        Wire andOut = AND(in1, in2)
        oNOT(andOut, out)
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

        in1.addAction(orAction)
        in2.addAction(orAction)

        propagateSignal()

        return out
    }

    Wire mOR(List<Wire> input) {
        Wire out = new Wire()

        def andAction = {
            afterDelay(AND_delay) {
                out.setSignal(input.inject { Wire in1, Wire in2 -> in1.getSignal() | in2.getSignal() })
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

    void oNOR(Wire in1, Wire in2, Wire out) {
        Wire orOut = OR(in1, in2)
        oNOT(orOut, out)
    }

    Wire NOT(Wire input) {

        Wire out = new Wire()

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

}
