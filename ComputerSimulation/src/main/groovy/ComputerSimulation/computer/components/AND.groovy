package ComputerSimulation.computer.components

import rx.Observable
import rx.functions.FuncN

import java.util.concurrent.TimeUnit

class AND extends Gate {

    private List<Observable<Boolean>> wires = new ArrayList<>()

    AND(Wire... wires) {

        if (wires.size() < 2) {
            throw new IllegalStateException("AND gate requires 2 inputs at least")
        }

        wires.each {
            it -> this.wires.add(it.getSignal())
        }
    }

    @Override
    Observable<Boolean> out() {
        return Observable.zip(wires, new FuncN<Boolean>() {

            @Override
            Boolean call(Object... args) {
                return getResult(args)
            }
        }).delay(AND_delay, TimeUnit.MILLISECONDS)
    }

    // Helper methods

    private Boolean getResult(Object... args) {
        return Arrays.asList(args).stream().reduce({ a, b -> a && b }).get()
    }
}
