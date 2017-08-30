package GameOfLife

/**
 * Defines the grid and start simulation
 */
class Simulate {

    private static final int LIVE_CELL = 1
    private static final int DEAD_CELL = 0

    private final Grid grid

    Simulate(Grid grid) {
        this.grid = grid
    }

    /**
     * Create grid and start grow up the new generation.
     *
     * NOTE: Do not apply rules of live immediately on the grid.
     * Analise every sell and then change everything in one pass
     */
    Grid evolution() {

        List<Tuple2> nextGenLiveCells = []

        for (int row = 0; row < grid.getRowSize(); row++) {
            for (int column = 0; column < grid.getColumnSize(row); column++) {

                int cell = grid.getPoint(row, column)
                int liveNeighbors = grid.getLiveNeighbors(row, column)

                int nextGenCell = rulesOfLive(cell, liveNeighbors)
                if (nextGenCell == LIVE_CELL) {
                    nextGenLiveCells.add(new Tuple2(row, column))
                }
            }
        }

        grid.swipeGrid()

        for (Tuple2 cell : nextGenLiveCells) {
            grid.setPoint(cell.first, cell.second, LIVE_CELL)
        }

        return grid
    }

    /**
     * Rules:
     *  - Any live cell with fewer than two live neighbors dies, as if caused by underpopulation
     *  - Any live cell with more than three live neighbors dies, as if by overcrowding
     *
     *  - Any live cell with two or three live neighbors lives on to the next generation
     *
     *  - Any dead cell with exactly three live neighbors becomes a live cell.
     *
     * @return digit that indicates:
     *              1 = new cell should became alive
     *              0 = nothing happens
     */
    int rulesOfLive(int cell, int liveNeighbors) {

        if (cell == 1) {

            if (liveNeighbors < 2) {
                return DEAD_CELL

            } else if (liveNeighbors > 3) {
                return DEAD_CELL

            } else if (liveNeighbors == 2 || liveNeighbors == 3) {
                return cell
            }
        }

        if (cell == 0 && liveNeighbors == 3) {
            return LIVE_CELL
        }

        // nothing happens
        return cell
    }
}
