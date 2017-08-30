package MapColoring

import java.util.stream.Collectors

class Country {

    String name
    String color

    Set<Country> neighbors = []

    @Override
    public String toString() {
        def neighborNames = neighbors.stream().map({it.getName()}).collect(Collectors.toList())

        return "Country{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", neighbors=" + neighborNames + '}';
    }
}
