package ComputerSimulation.components

class Wire {

    private boolean signal = false

    private List<Closure> actions = new ArrayList<>()

    /**
     * @return Returns the current value of the signal
     * transported by the wire
     */
    boolean getSignal() {
        return signal
    }

    /**
     * Modifies the value of the signal transported by the wire
     * @param sig
     */
    void setSignal(boolean sig) {

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
     * @param action
     */
    void addAction(Closure action) {

        // prefix the actions with the new one
        actions.add(0, action)
        action.call()
    }
}
