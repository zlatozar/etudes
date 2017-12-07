package ComputerSimulation.computer.components

import rx.Observable

final class Wire {

    private String name
    private Observable<Boolean> inputSeq

    Wire() {}

    Wire(final String name) {
        this.name = name
    }

    Observable<Boolean> getSignal() {
        if (inputSeq == null) {
            throw new IllegalArgumentException("Set signal first. There is no signal in wire $name")
        }

        return inputSeq
    }

    void setSignal(final Observable<Boolean> inputSeq) {
        if (name) {
            printSignals(name, inputSeq)
        }

        // because we could have many getSignal
        this.inputSeq = inputSeq.cache()
    }

    // Helper methods

    private static void printSignals(final String name, final Observable<Boolean> inputSeq) {
        final StringBuilder sb = new StringBuilder()

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
