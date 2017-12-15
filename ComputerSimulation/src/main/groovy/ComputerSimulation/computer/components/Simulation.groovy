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

    /**
     * Start execution plan based on the agenda.
     * NOTE: Should be called every time when signal is set.
     */
    void propagateSignal() {
        loop()
    }

    /**
     * Clear agenda to prepare digital logic scheme for the next
     * clock tick (next portion of data)
     */
    static void clearSignal() {
        agenda.clear()
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

        // event is bigger than everything else - add it at the end
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
