package ComputerSimulation.computer

class Maths {

    private final Convert convert = new Convert()

    List<String> plus(List<String> x, List<String> y) {

        int x1 = convert.toInteger(x)
        int y1 = convert.toInteger(y)

        return convert.toBinaryInteger(x1 + y1)
    }
}
