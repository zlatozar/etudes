package ComputerSimulation.computer.components

import groovy.transform.CompileStatic

@CompileStatic
class Simulation {

    private static List<Event> agenda = new LinkedList<>()

    // incremented by gates delay
    protected int currentTime = 0

    void afterDelay(int delay, Closure action) {
        Event item = new Event(currentTime + delay, action)
        insert(item)
    }

    void propagateSignal() {
        loop()
    }

    // Helper functions

    private static void insert(Event item) {

        boolean added = false

        if (agenda.isEmpty()) {
            agenda.add(item)
            return
        }

        // insert in the right place: shorter in time - before
        for (int i = 0; i < agenda.size(); i++) {
            if (agenda[i].getTime() >= item.getTime()) {

                agenda.add(i, item)

                added = true
                break;
            }
        }

        // so this is bigger than everything else - add it last
        if (!added) {
            agenda.add(item)
        }
    }

    /**
     * Remove successive elements from the agenda,
     * and performs the associated actions.
     */
    private void loop() {

        if (!agenda.isEmpty()) {

            Event next = agenda.remove(0)

            currentTime = next.getTime()
            next.getAction().call()

            loop()
        }
    }

}
