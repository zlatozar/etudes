package MapColoring

/**
 * Try to paint with given numbers of colors defined geographic region
 */
class Paint {

    private final static int MAX_NUMBER_OF_COLORS = 5
    private final static Set<String> DEFINED_COLORS = ['green', 'blue', 'yellow', 'orange', 'brown']

    private final Set<Country> countries

    private int numberOfColors = 4
    private Set<String> colors = []

    Paint(Set<Country> countries) {
        this.countries = countries
    }

    Set<Country> start() {
        def coloredCountries = colorGraph([] as Set)

        if (!coloredCountries) {
            throw new RuntimeException("It is impossible to paint map using only $numberOfColors colors")
        }

        return coloredCountries
    }

    Paint withNumberOfColors(int numberOfColors) {
        if (numberOfColors > MAX_NUMBER_OF_COLORS || numberOfColors > DEFINED_COLORS.size()) {
            throw new IllegalArgumentException("Number of colors must be less than $MAX_NUMBER_OF_COLORS")
        }

        this.numberOfColors = numberOfColors
        for (int i = 0; i < numberOfColors; i++) {
            colors.add(DEFINED_COLORS[i])
        }

        println("Following colors $colors will be used\n")
        return this
    }

    int getNumberOfColors() {
        return numberOfColors
    }

    // Helper methods

    private Set<Country> colorGraph(Set<Country> accum) {

        // indicate success
        if (accum.size() == countries.size()) {
            return accum

        } else {
            for (Country country : countries) {

                if (!accum.contains(country)) {

                    for (String color : colors) {

                        if (isSafe(country, color)) {
                            country.setColor(color)
                            accum.add(country)

                            return colorGraph(accum)
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if country color conflicts with the colors of its neighbors
     */
    private static boolean isSafe(Country country, String color) {

        boolean isPossible = true

        for (Country neighbor : country.getNeighbors()) {

            if (!neighbor.getColor()) {
                isPossible &= true
            }

            if (neighbor.getColor() == color) {
                isPossible &= false
            }
        }

        return isPossible
    }
}
