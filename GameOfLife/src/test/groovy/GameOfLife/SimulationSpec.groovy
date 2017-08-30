package GameOfLife

import spock.lang.Specification
import spock.lang.Title

@Title('Checks how evaluation works')
class SimulationSpec extends Specification {

    def 'Game of live rules (part 1)'() {

        given: 'Rules for live cells'

        when: "Pass live cell and it's live neighbours"

        then: "Rules of live should work correctly"
        Simulate simulate = new Simulate(null)

        println('Any live cell with fewer than two live neighbors dies, as if caused by underpopulation')
        assert simulate.rulesOfLive(1, 0) == 0
        assert simulate.rulesOfLive(1, 1) == 0

        println('Any live cell with two or three live neighbors lives on to the next generation')
        assert simulate.rulesOfLive(1, 2) == 1
        assert simulate.rulesOfLive(1, 3) == 1

        println('Any live cell with more than three live neighbors dies, as if by overcrowding')
        assert simulate.rulesOfLive(1, 4) == 0
        assert simulate.rulesOfLive(1, 5) == 0
        assert simulate.rulesOfLive(1, 6) == 0
        assert simulate.rulesOfLive(1, 7) == 0
        assert simulate.rulesOfLive(1, 8) == 0
    }

    def 'Game of live rules (part 2)'() {

        given: 'Rules of death cells'

        when: "Pass dead cell and it's live neighbours"

        then: "Death rules should work correctly"
        Simulate simulate = new Simulate(null)

        println('Any dead cell with exactly three live neighbors becomes a live cell')
        assert simulate.rulesOfLive(0, 0) == 0
        assert simulate.rulesOfLive(0, 1) == 0
        assert simulate.rulesOfLive(0, 2) == 0
        assert simulate.rulesOfLive(0, 3) == 1
        assert simulate.rulesOfLive(0, 4) == 0
        assert simulate.rulesOfLive(0, 5) == 0
        assert simulate.rulesOfLive(0, 6) == 0
        assert simulate.rulesOfLive(0, 7) == 0
        assert simulate.rulesOfLive(0, 7) == 0
    }

    def 'Displays how next generation looks like'() {

        given: 'Grid with semaphore'
        Grid grid = new Grid(5, 5).withSemaphore()
        println('\nGrid with semaphore')
        println(grid)

        when: 'Next generation is born'
        Simulate simulate = new Simulate(grid)

        then: 'Semaphore should blink'
        println('Semaphore should blink')
        println(simulate.evolution())
        println(simulate.evolution())
    }
}