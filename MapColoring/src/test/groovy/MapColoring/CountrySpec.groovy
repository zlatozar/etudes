package MapColoring

import spock.lang.Specification
import spock.lang.Title

@Title('Country display')
class CountrySpec extends Specification {

    def 'Country definition display'() {

        given: 'Country definition'
        Country bulgaria = new Country()
        bulgaria.setName('Bulgaria')
        bulgaria.setColor('blue')

        Country macedonia = new Country()
        macedonia.setName('Macedonia')
        macedonia.setColor('green')

        macedonia.setNeighbors([bulgaria] as Set)
        bulgaria.setNeighbors([macedonia] as Set)

        when: 'Visualization is needed'

        then: 'Show nice representation'
        println(bulgaria)

    }

}