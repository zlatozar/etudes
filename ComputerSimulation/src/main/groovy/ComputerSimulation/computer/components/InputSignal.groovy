package ComputerSimulation.computer.components

import io.reactivex.Flowable
import io.reactivex.Observable

final class InputSignal {

    static void emit(HashMap<Wire, List<Integer>> rowSignals) {

        final HashMap<Wire, Observable<Boolean>> wiresWithSignals = new HashMap<>()

        rowSignals.each {
            it -> wiresWithSignals.put(it.getKey(), convert(it.getValue()))
        }

        // try to send signal at once
        wiresWithSignals.entrySet().parallelStream().any { it -> it.getKey().setSignal(it.getValue()) }
    }

    // Helper methods

    private static Observable<Boolean> convert(List<Integer> signalSequence) {

        if (signalSequence.isEmpty()) {
            throw new IllegalStateException('Signals missing')
        }

        return Observable.fromIterable(signalSequence.collect( {it == 0 ? false : true} ))
    }
}
