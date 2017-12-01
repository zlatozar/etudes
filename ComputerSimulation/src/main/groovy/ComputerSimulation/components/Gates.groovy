package ComputerSimulation.components

abstract class Gates extends Simulation implements IgnoreDelays {

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

    void NOT(Wire input, Wire out) {

        def notAction = {
            boolean inputSig = input.getSignal()

            afterDelay(AND_delay) {
                out.setSignal(! inputSig)
            }
        }

        input.addAction notAction
    }

    void probe(String name, Wire wire) {

        def probeAction = {
            println("$name currentTime=$currentTime new value=${wire.getSignal()}")
        }

        wire.addAction probeAction
    }
}
