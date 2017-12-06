package ComputerSimulation.computer.components

import rx.Observable

import java.util.concurrent.TimeUnit

class NOT extends Gate {

    private Wire wire

    NOT(Wire wire) {
        if (wire == null) {
            throw new IllegalStateException("NOT gate requires an input")
        }

        this.wire = wire
    }

    @Override
    Observable<Boolean> out() {
        return Observable.just(!wire.getSignal()).delay(NOT_delay, TimeUnit.MILLISECONDS)
    }
}
