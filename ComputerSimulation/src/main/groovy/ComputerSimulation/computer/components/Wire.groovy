package ComputerSimulation.computer.components

import groovy.transform.CompileStatic

import java.util.concurrent.CopyOnWriteArrayList

@CompileStatic
class Wire {

    // default signal
    private boolean signal = false

    private final List<Closure> actions = new CopyOnWriteArrayList<>()

    private String name = ''

    Wire() {}

    Wire(boolean sig) {
        setSignal(sig)
    }

    Wire(String name) {
        this.name = name
    }

    /**
     * @return Returns the current value of the signal
     * transported by the wire
     */
    boolean getSignal() {
        return signal
    }

    /**
     * Modifies the value of the signal transported by the wire
     *
     * When we connect wire we set signal so all actions that are
     * in wire should be executed.
     * @param sig
     */
    void setSignal(boolean sig) {

        // on change only
        if (sig != signal) {
            signal = sig

            actions.each {
                it.call()
            }

            actions.clear()
        }
    }

    /**
     * Attaches the specified procedure to the actions of the wire.
     * All the attached actions are executed at the each change of
     * the transported signal.
     *
     * Sets the signal in output wire and store action. In this way
     * when new signal is set wire will 'react'
     *
     * @param action
     */
    void addAction(Closure action) {

        // prefix the actions with the new one
        actions.add(0, action)
        action.call()
    }

    @Override
    String toString() {
        StringBuilder sb = new StringBuilder()
        sb.append('Wire ')
        sb.append(name)
        sb.append('(')
        sb.append(signal)

        if (actions) {
            sb.append(', actions:')
            sb.append(actions.size())
        }

        sb.append(')')

        return sb.toString()
    }
}
