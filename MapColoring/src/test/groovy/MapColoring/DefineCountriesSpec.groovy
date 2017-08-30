package MapColoring

import spock.lang.Specification
import spock.lang.Title

@Title('Geographic map ordering and countries creation')
class DefineCountriesSpec extends Specification {

    def 'Order by number of neighbors'() {

        given: 'Territory with all its neighbors'
        def borders = new GeographicMaps.America().borders()

        when: 'Use soring by number of neighbors'
        def ordered = new DefineCountries(borders).withMostNeighborsOrder().build()

        then: 'Country with most neighbors should be first'
        assert ordered[0].getNeighbors().size() == 8
        assert ordered[0].getName() == 'Missouri'

        assert ordered[1].getNeighbors().size() == 8
        assert ordered[2].getNeighbors().size() == 6
    }

    def 'Create countries'() {

        given: 'Territory with all its neighbors'
        def borders = new GeographicMaps.Australia().borders()

        when: 'Countries map is requested'
        Set<Country> countries = new DefineCountries(borders).build()

        then: 'Map should be built correctly'
        assert countries
        assert countries.size() == 7

        def tasmania = countries[0]
        assert tasmania.getName() == 'Tasmania'
        assert tasmania.getNeighbors().size() == 1

        def northwestTerritories = countries[3]
        assert northwestTerritories.getName() == 'NorthwestTerritories'
        assert northwestTerritories.getNeighbors().size() == 3
    }

    def 'References should exist'() {
        given: 'Territory with all its neighbors'
        def borders = new GeographicMaps.Australia().borders()

        when: 'Countries map is requested'
        Set<Country> countries = new DefineCountries(borders).build()

        then: 'Should be possible to walk through countries graph'
        assert countries[0].getNeighbors()[0].getName() == 'Victoria'
    }

}