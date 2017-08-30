package GameOfLife

import spock.lang.Specification
import spock.lang.Title

@Title('Test who matrix is visualised in ASCII')
class DisplaySpec extends Specification {

    def 'Just print matrix'() {

        given: 'Matrix filled with 0s and 1s'
        int[][] glider = [[0, 1, 0], [0, 0, 1], [1, 1, 1]]

        when: 'Matrix is passed to the display'

        then: 'Print it in ASCII format'
        Display.display(glider)
    }

    def 'Animate matrix'() {

        given: "Tree matrix"
        int[][] gliderFirstFrame = [[0, 1, 0], [0, 0, 1], [1, 1, 1]]
        int[][] gliderSecondFrame = [[1, 0, 1], [0, 1, 1], [0, 1, 0]]
        int[][] gliderThirdFrame = [[0, 0, 1], [1, 0, 1], [0, 1, 1]]

        when: 'Display one after another'

        then: 'It should looks like an animation'
        Display.display(gliderFirstFrame)
        sleep(1000)
        Display.display(gliderSecondFrame)
        sleep(1000)
        Display.display(gliderThirdFrame)
    }

}