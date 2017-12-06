package ComputerSimulation.computer.components

import rx.Observable

class InputSignal implements DelaysDefinition {

    static void emit(HashMap<Wire, List<Integer>> rowSignals) {

        HashMap<Wire, Observable<Boolean>> wiresWithSignals = new HashMap<>()

        rowSignals.each {
            it -> wiresWithSignals.put(it.getKey(), convert(it.getValue()))
        }

        wiresWithSignals.entrySet().parallelStream().any{ it -> it.getKey().setSignal(it.getValue()) }
    }

    // Helper methods

    private static Observable<Boolean> convert(List<Integer> signalSequence) {

        if (signalSequence.isEmpty()) {
            throw new IllegalStateException('Signals missing')
        }

        return Observable.from(signalSequence.collect( {it == 0 ? false : true} ))
    }
}
