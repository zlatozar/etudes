package ComputerSimulation.computer.components

import rx.Observable

class Wire {

    private String name
    private Observable<Boolean> inputSeq

    Wire(String name) {
        this.name = name
    }

    synchronized Observable<Boolean> getSignal() {
        if (inputSeq == null) {
            throw new IllegalArgumentException("Set signal first. There is no signal in wire $name")
        }

        return inputSeq
    }

    synchronized void setSignal(Observable<Boolean> inputSeq) {
        if (name) {
            printSignals(inputSeq)
        }
        this.inputSeq = inputSeq
    }

    // Helper methods

    private void printSignals(Observable<Boolean> inputSeq) {
        StringBuilder sb = new StringBuilder()

        sb.append("Wire: $name - [")
        inputSeq.subscribe({ it -> it ? sb.append('1 ') : sb.append('0 ') })
        sb.append(']')

        println(sb.toString())
    }

    @Override
    String toString() {
        return name ? name : super.toString()
    }
}
