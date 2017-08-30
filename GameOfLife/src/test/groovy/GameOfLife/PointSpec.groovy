package GameOfLife

import spock.lang.Specification
import spock.lang.Title

@Title('Checks if point neighbours are calculated correctly')
class PointSpec extends Specification {

    def 'Calculate neighbours coordinates in edges'() {

        given: 'A grid with particular size'
        Point point = new Point(10, 10)

        when: 'Ask for neighbours coordinates'
        Tuple2[] neighbours00 = point.getNeighborsCoordinates(0, 0)
        Tuple2[] neighbours09 = point.getNeighborsCoordinates(0, 9)

        Tuple2[] neighbours99 = point.getNeighborsCoordinates(9, 9)
        Tuple2[] neighbours90 = point.getNeighborsCoordinates(9, 0)

        then: 'Should have three neighbours'
        assert neighbours00 == [[0, 1], [1, 0], [1, 1]]  // upper_left
        assert neighbours99 == [[8, 8], [8, 9], [9, 8]]  // down_right

        assert neighbours09 == [[0, 8], [1, 8], [1, 9]]   // upper_right
        assert neighbours90 == [[8, 0], [8, 1], [9, 1]]   // down_left
    }

    def 'Calculate neighbours coordinates in borders'() {

        given: 'A grid with particular size'
        Point point = new Point(10, 10)

        when: 'Ask for neighbours coordinates'
        Tuple2[] neighbours50 = point.getNeighborsCoordinates(0, 5)  // upper
        Tuple2[] neighbours95 = point.getNeighborsCoordinates(9, 5)  // down

        Tuple2[] neighbours05 = point.getNeighborsCoordinates(5, 0)  // left
        Tuple2[] neighbours59 = point.getNeighborsCoordinates(5, 9)  // right

        then: 'Should have five neighbours'
        neighbours50 == [[0, 4], [0, 6], [1, 4], [1, 5], [1, 6]]
        neighbours95 == [[8, 4], [8, 5], [8, 6], [9, 4], [9, 6]]

        neighbours05 == [[4, 0], [4, 1], [5, 1], [6, 0], [6, 1]]
        neighbours59 == [[4, 8], [4, 9], [5, 8], [6, 8], [6, 9]]
    }

    def 'Calculate neighbours coordinates in center'() {

        given: 'A grid with particular size'
        Point point = new Point(10, 10)

        when: 'Ask for neighbours coordinates'
        Tuple2[] neighbours11 = point.getNeighborsCoordinates(1, 1)
        Tuple2[] neighbours55 = point.getNeighborsCoordinates(5, 5)

        then: 'Should have eight neighbours'
        neighbours55 == [[4, 4], [4, 5], [4, 6], [5, 4], [5, 6], [6, 4], [6, 5], [6, 6]]
        neighbours11 == [[0, 0], [0, 1], [0, 2], [1, 0], [1, 2], [2, 0], [2, 1], [2, 2]]
    }
}