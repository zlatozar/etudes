package GameOfLife

class Grid {

    private final Point point

    private final int ROW_LENGTH
    private final int COLUMN_LENGTH

    private List<List<Integer>> grid

    Grid(row_length, column_length) {

        if (row_length < 5 || column_length < 5) {
            throw new IllegalArgumentException('Grid dimension is too small')
        }

        this.ROW_LENGTH = row_length
        this.COLUMN_LENGTH = column_length

        this.point = new Point(ROW_LENGTH, COLUMN_LENGTH)
    }

    Grid withGlider() {
        def glider = [[0, 1, 0], [0, 0, 1], [1, 1, 1]]
        this.grid = formGrid(glider)
        return this
    }

    Grid withSemaphore() {
        def semaphore = [[0, 0, 0, 0], [0, 1, 1, 1], [0, 0, 0, 0]]
        this.grid = formGrid(semaphore)
        return this
    }

    Grid withBlock() {
        def block = [[1, 1, 0], [1, 1, 0], [0, 0, 0]]
        this.grid = formGrid(block)
        return this
    }

    int getRowSize() {
        return grid.size()
    }

    int getColumnSize(int row) {
        return grid[row].size()
    }

    int getPoint(int row, int column) {
        return grid[row][column]
    }

    int setPoint(int row, int column, int value) {
        grid[row][column] = value
    }

    int getLiveNeighbors(int row, int column) {
        int liveNeighbors = 0
        Tuple2[] neighbours = getNeighborsCoordinates(row, column)

        for (Tuple2 xy : neighbours) {
            liveNeighbors += getPoint(xy.first, xy.second)
        }

        return liveNeighbors
    }

    void swipeGrid() {
        for (int row = 0; row < getRowSize(); row++) {
            for (int column = 0; column < getColumnSize(row); column++) {
                grid[row][column] = 0
            }
        }
    }

    @Override
    String toString() {
        StringBuilder sb = new StringBuilder()

        for (int row = 0; row < grid.size(); row++) {
            for (int column = 0; column < grid[row].size(); column++) {
                sb.append(" ${getPoint(row, column)}")
            }
            sb.append('\n')
        }
        return sb.toString()
    }

    // Helper functions

    private Tuple2[] getNeighborsCoordinates(int row, int column) {
        return point.getNeighborsCoordinates(row, column)
    }

    private List<List<Integer>> formGrid(List<List<Integer>> figureDefinition) {

        for (int row = 0; row < figureDefinition.size(); row++) {
            for (int column = figureDefinition[row].size(); column < ROW_LENGTH; column++) {
                figureDefinition[row].add(column, 0)
            }
        }

        for (int i = figureDefinition.size(); i < COLUMN_LENGTH; i++) {

            def row = []
            for (int j = 0; j < ROW_LENGTH; j++) {
                row.add(0)
            }

            figureDefinition.add(i, row)
        }

        return figureDefinition
    }
}
