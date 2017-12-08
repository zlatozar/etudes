package ComputerSimulation.computer.components

import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

class Gates {

    static final Wire DUMMY() {
        final Wire dummy = new Wire()
        dummy.setSignal(Observable.just(false))

        return dummy
    }

    static final Wire SOLDER(final Wire input) {
        final Wire dummy = new Wire()
        dummy.setSignal(Observable.just(false))

        final Wire out = new Wire()
        out.setSignal(Observable.merge(dummy.getSignal(), input.getSignal()))

        return out
    }

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

        out.setSignal(Observable.zip(allWires, new Function<List<Observable<Boolean>>, Boolean>() {

            @Override
            Boolean apply(@NonNull List<Observable<Boolean>> args) throws Exception {
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

        out.setSignal(Observable.zip(allWires, new Function<List<Observable<Boolean>>, Boolean>() {

            @Override
            Boolean apply(@NonNull List<Observable<Boolean>> args) throws Exception {
                return Arrays.asList(args).inject { a, b -> a  b }
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
