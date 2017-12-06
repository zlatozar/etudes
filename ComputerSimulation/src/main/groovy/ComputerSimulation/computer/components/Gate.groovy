package ComputerSimulation.computer.components

import rx.Observable

abstract class Gate implements IgnoreDelays {

    abstract Observable<String> out();

}
