package MapColoring

/**
 * If a backtracking solution is used, the order in which the regions are
 * chosen can have a tremendous effect on the speed.
 */
class DefineCountries {

    private final Map<String, Set<String>> borders
    private Closure orderFunction
    private Set<Country> countries = []


    DefineCountries(Map<String, Set<String>> borders) {
        this.borders = borders
    }

    Set<Country> build() {
        buildCountriesMap()

        if (!orderFunction) {
            return countries
        }

        return orderFunction(countries)
    }

    DefineCountries withMostNeighborsOrder() {
        orderFunction = { borders -> order(borders)}
        return this
    }

    // Helper functions

    protected static Set<Country> order(Set<Country> countries) {
        return countries.sort({ a, b -> b.getNeighbors().size() <=> a.getNeighbors().size() })
    }

    private void buildCountriesMap() {

        for(Map.Entry<String, Set<String>> countryEntry : borders) {

            String countryName = countryEntry.getKey()
            Set<String> countryNeighbors = countryEntry.getValue()

            Country country = countries.find({ it -> it.getName() == countryName })

            if (!country) {
                country = new Country()
                country.setName(countryName)
            }

            countries.add(country)
            createNeighbors(country, countryNeighbors)
        }
    }

    private Country createNeighbors(Country country, Set<String> neighbors) {

        Set<Country> allNeighbors = []
        for(String neighbor : neighbors) {

            Country existNeighbor = countries.find({ it -> it.getName() == neighbor})

            if (existNeighbor) {
                allNeighbors.add(existNeighbor)

            } else {
                Country countryNeighbor = new Country()
                countryNeighbor.setName(neighbor)

                countries.add(countryNeighbor)
                allNeighbors.add(countryNeighbor)
            }
        }

        country.setNeighbors(allNeighbors)

        return country
    }

    @Override
    String toString() {
        return countries
    }
}
