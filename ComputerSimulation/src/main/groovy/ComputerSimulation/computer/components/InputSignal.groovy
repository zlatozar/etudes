package ComputerSimulation.computer.components

import rx.Observable

class InputSignal {

    static Observable<Boolean> emit(int signal) {
        boolean boolSignal = false

        if (signal){
            boolSignal = true
        }

        return Observable.just(boolSignal)
    }

    static Observable<Boolean> emit(List<Integer> signalSequence) {

        return Observable.from(
                signalSequence.collect {it == 0 ? false : true}
        )
    }
}
