package ComputerSimulation.components

class Event {

    int time
    Closure action

    Event(int time, Closure action) {
        this.time = time
        this.action = action
    }

}
