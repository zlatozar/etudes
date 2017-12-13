package ComputerSimulation.components

import groovy.transform.CompileStatic

@CompileStatic
class Wire {

    private boolean signal = false

    private List<Closure> actions = new LinkedList<>()

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
        return signal
    }
}
