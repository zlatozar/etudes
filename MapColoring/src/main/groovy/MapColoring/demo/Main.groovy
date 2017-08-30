package MapColoring.demo

import MapColoring.Country
import MapColoring.DefineCountries
import MapColoring.GeographicMaps
import MapColoring.Paint

class Main {

    public static void main(String[] args) {
        println('\n     - First we took America states map')
        def boundaries = new GeographicMaps.America().borders()

        println("\n     - Then we crate states with its properties ('color' in our case)")

        println("\n     - Our hope is that, by traversing the tree in a 'good' order, we will find the solution early enough.")
        println("       That's why we start painting from the state with most neighbors")
        Set<Country> countryAmerica = new DefineCountries(boundaries).withMostNeighborsOrder().build()

        println('\nHere is how it looks like when we use 4 colors:\n\n')
        Paint paint = new Paint(countryAmerica).withNumberOfColors(4)
        println(paint.start())
    }
}
