package ComputerSimulation.components

abstract class Simulation {

    private static List<Event> agenda = new ArrayList<>()

    private int curtime = 0
    protected int currentTime = curtime

    void insert(Event item) {

        boolean added = false

        if (agenda.isEmpty()) {
            agenda.add(item)
            return
        }

        // insert in the right place:
        for (int i = 0; i < agenda.size(); i++) {
            if (agenda[i].time >= item.time) {

                agenda.add(i, item)

                added = true
                break;
            }
        }

        if (!added) {
            agenda.add(item)
        }
    }

    void afterDelay(int delay, Closure action) {
        Event item = new Event(currentTime + delay, action)
        insert(item)
    }

    /**
     * Executes the event loop after installing the initial message.
     * That signals the start of simulation.
     */
    void run() {

        afterDelay(0) {
            println("*** Simulation started, time=$currentTime ***")
        }

        loop()
    }

    /**
     * Remove successive elements from the agenda,
     * and performs the associated actions.
     */
    private void loop() {

        if (agenda.isEmpty()) {
            return

        } else {
            Event next = agenda.remove(0)

            currentTime = next.time
            next.action()

            loop()
        }
    }
}
