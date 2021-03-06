package ComputerSimulation.computer.components

import groovy.transform.CompileStatic

@CompileStatic
class Event {

    private final int time
    private final Closure action

    Event(int time, Closure action) {
        this.time = time
        this.action = action
    }

    int getTime() {
        return time
    }

    Closure getAction() {
        return action
    }
}
