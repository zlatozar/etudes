package MapColoring

import spock.lang.Specification
import spock.lang.Title

@Title('Checks how map is serving')
class GeographicMapsSpec extends Specification {

    def 'Easy way to have country and its neighbors'() {

        given: 'Country'

        when: 'Country map is requested'

        then: 'Easy way to serve to the computer should exist'
        assert new GeographicMaps.Australia().borders().keySet()[1] == 'WesternAustralia'
        assert new GeographicMaps.America().borders().keySet()[20] == 'Pennsylvania'
    }

}