package ComputerSimulation.computer.components

import rx.Observable
import rx.functions.FuncN

class Gates {

    static final Wire AND(final Wire in1, final Wire in2) {

        final Wire out = new Wire()
        out.setSignal(Observable.zip(in1.getSignal(), in2.getSignal(), { a, b -> a && b }))

        return out
    }

    static final void AND(final Wire in1, final Wire in2, final Wire out) {
        out.setSignal(Observable.zip(in1.getSignal(), in2.getSignal(), { a, b -> a && b }))
    }

    static final Wire mAND(final Wire... wires) {

        final List<Observable<Boolean>> allWires = new ArrayList<>()

        if (wires.size() < 2) {
            throw new IllegalStateException("mAND gate requires 2 inputs at least")
        }

        wires.each {
            it -> allWires.add(((Wire) it).getSignal())
        }

        final Wire out = new Wire()

        out.setSignal(Observable.zip(allWires, new FuncN<Boolean>() {
            @Override
            Boolean call(Object... args) {
                return Arrays.asList(args).inject { a, b -> a && b }
            }
        }))

        return out
    }

    static final Wire OR(final Wire in1, final Wire in2) {

        final Wire out = new Wire()
        out.setSignal(Observable.zip(in1.getSignal(), in2.getSignal(), { a, b -> a || b }))

        return out
    }

    static final Wire mOR(final Wire... wires) {

        final List<Observable<Boolean>> allWires = new ArrayList<>()

        if (wires.size() < 2) {
            throw new IllegalStateException("mOR gate requires 2 inputs at least")
        }

        wires.each {
            it -> allWires.add(((Wire) it).getSignal())
        }

        final Wire out = new Wire()

        out.setSignal(Observable.zip(allWires, new FuncN<Boolean>() {
            @Override
            Boolean call(Object... args) {
                return Arrays.asList(args).inject { a, b -> a || b }
            }
        }))

        return out
    }

    static final Wire NOT(final Wire input) {

        final Wire out = new Wire()
        out.setSignal(input.getSignal().map({it -> !it}))

        return out
    }

    static final void NOT(final Wire input, final Wire out) {
        out.setSignal(input.getSignal().map({it -> !it}))
    }

    // Additional

    static final Wire NAND(final Wire in1, final Wire in2) {
        return NOT(AND(in1, in2))
    }

    static final void NAND(final Wire in1, final Wire in2, final Wire out) {
        out.setSignal(NOT(AND(in1, in2)).getSignal())
    }

    static final Wire mNAND(final Wire... wires) {
        return NOT(mAND(wires))
    }

    static final Wire NOR(final Wire in1, final Wire in2) {
        return NOT(OR(in1, in2))
    }

    static final Wire mNOR(final Wire... wires) {
        return NOT(mOR(wires))
    }

    static final Wire XOR(final Wire in1, final Wire in2) {
        return AND(NAND(in1, in2), OR(in2, in1))
    }

}
