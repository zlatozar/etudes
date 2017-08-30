package GameOfLife

import spock.lang.Specification
import spock.lang.Title

@Title('Illustrates grids operations')
class GridSpec extends Specification {

    def 'Visualize glider'() {

        given: 'Display ready to display grids'

        when: 'Give definition to the grid generator'
        Grid gridDefinition = new Grid(10, 10).withGlider()

        then: 'Glider should be on the left upper corner'
        println('\nHere is how glider looks like:\n')
        Display.display(gridDefinition)
    }

    def 'Visualize grid values'() {

        given: 'Grid with values'

        when: 'Initialize grid with particular figure'
        Grid grid = new Grid(5, 5).withSemaphore()

        then: 'See how gird looks like'
        println('\nDisplay row values for a semaphore:\n')
        println(grid.toString())
    }

    def 'Calculate live neighbors'() {

        given: 'Grid with values'

        when: 'Initialize grid with particular figure'
        Grid grid = new Grid(5, 5).withSemaphore()

        then: 'Should correctly calculate number of live neighbors'
        println('row: 1, column: 0, should be 1')
        assert grid.getLiveNeighbors(0, 0) == 1

        println('row: 1, column: 1, should be 1')
        assert grid.getLiveNeighbors(1, 1) == 1

        println('row: 1, column: 2, should be 2')
        assert grid.getLiveNeighbors(1, 2) == 2

        println('row: 0, column: 2, should be 3')
        assert grid.getLiveNeighbors(0, 2) == 3

        println('row: 1, column: 3, should be 1')
        assert grid.getLiveNeighbors(1, 3) == 1

        println('row: 2, column: 2, should be 3')
        assert grid.getLiveNeighbors(2, 2) == 3

    }

    def 'Set new generation on the grid'() {

        given: 'Grid with values'

        when: 'Initialize grid with particular figure'
        Grid grid = new Grid(5, 5).withSemaphore()

        then: 'Should correctly calculate number of live neighbors'
        int liveNeighbors = grid.getLiveNeighbors(0, 2)
        assert liveNeighbors == 3

        and: 'New born cell should be placed in the same point'
        grid.setPoint(0, 2, 1)
        assert grid.getPoint(0, 2) == 1

        println('\nHere is how it looks like')
        println(grid)
    }
}