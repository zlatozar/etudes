package ComputerSimulation.computer.components

import groovy.transform.CompileStatic
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.annotations.NonNull

@CompileStatic
final class Wire {

    private static final Boolean DEFAULT_SIGNAL = false

    private String name
    private Observable<Boolean> inputSeq = Observable.empty()

    Wire() {}

    // Named wire indicates signal trace
    Wire(final String name) {
        this.name = name
    }

    Observable<Boolean> getSignal() {

        return inputSeq.switchIfEmpty(Observable.create(new ObservableOnSubscribe<Boolean>() {

            @Override
            void subscribe(@NonNull ObservableEmitter<Boolean> observableEmitter) throws Exception {

                while (!observableEmitter.isDisposed()) {
                    observableEmitter.onNext(DEFAULT_SIGNAL)
                }
            }
        }))
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
