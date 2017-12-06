package ComputerSimulation.computer.components

import rx.Observable
import rx.functions.FuncN

class Gates {

    static Wire AND(Wire... wires) {

        List<Observable<Boolean>> allWires = new ArrayList<>()

        if (wires.size() < 2) {
            throw new IllegalStateException("AND gate requires 2 inputs at least")
        }

        wires.each {
            it -> allWires.add(it.getSignal())
        }

        Wire out = new Wire()

        out.setSignal(Observable.zip(allWires, new FuncN<Boolean>() {
            @Override
            Boolean call(Object... args) {
                return Arrays.asList(args).stream().reduce({ a, b -> a && b }).get()
            }
        }))

        return out
    }

    static Wire NAND(Wire... wires) {
        return NOT(AND(wires))
    }

    static Wire OR(Wire... wires) {

        List<Observable<Boolean>> allWires = new ArrayList<>()

        if (wires.size() < 2) {
            throw new IllegalStateException("OR gate requires 2 inputs at least")
        }

        wires.each {
            it -> allWires.add(it.getSignal())
        }

        Wire out = new Wire()

        out.setSignal(Observable.zip(allWires, new FuncN<Boolean>() {
            @Override
            Boolean call(Object... args) {
                return Arrays.asList(args).stream().reduce({ a, b -> a || b }).get()
            }
        }))

        return out
    }

    static Wire NOR(Wire... wires) {
        return NOT(OR(wires))
    }

    static Wire XOR(Wire... wires) {
        Wire nandOut= NAND(wires)
        Wire orOut = OR(wires)

        return AND(nandOut, orOut)
    }

    static Wire NOT(Wire wire) {
        if (wire == null) {
            throw new IllegalStateException("NOT gate requires an input")
        }

        Wire out = new Wire()
        out.setSignal(wire.getSignal().map({it -> !it}))

        return out
    }

}
