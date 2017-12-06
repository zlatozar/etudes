package ComputerSimulation.computer.components

import rx.Observable
import rx.functions.FuncN

import java.util.concurrent.TimeUnit

class Gates implements IgnoreDelays {

    static Observable<Boolean> AND(Wire... wires) {

        List<Observable<Boolean>> allWires = new ArrayList<>()

        if (wires.size() < 2) {
            throw new IllegalStateException("AND gate requires 2 inputs at least")
        }

        wires.each {
            it -> allWires.add(it.getSignal())
        }

        return Observable.zip(allWires, new FuncN<Boolean>() {
            @Override
            Boolean call(Object... args) {
                return Arrays.asList(args).stream().reduce({ a, b -> a && b }).get()
            }
        }).delay(AND_delay, TimeUnit.MILLISECONDS)
    }

    static Observable<Boolean> OR(Wire... wires) {

        List<Observable<Boolean>> allWires = new ArrayList<>()

        if (wires.size() < 2) {
            throw new IllegalStateException("OR gate requires 2 inputs at least")
        }

        wires.each {
            it -> allWires.add(it.getSignal())
        }

        return Observable.zip(allWires, new FuncN<Boolean>() {
            @Override
            Boolean call(Object... args) {
                return Arrays.asList(args).stream().reduce({ a, b -> a || b }).get()
            }
        }).delay(OR_delay, TimeUnit.MILLISECONDS)
    }

    static Observable<Boolean> NOT(Wire wire) {

        if (wire == null) {
            throw new IllegalStateException("NOT gate requires an input")
        }

        return wire.getSignal().map({it -> !it})
    }

}
