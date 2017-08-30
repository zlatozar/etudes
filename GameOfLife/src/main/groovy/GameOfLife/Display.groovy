package GameOfLife

/**
 * Visualise grid containing values(0s and 1s) in ASCII format
 */
class Display {

    private static final String CELL = " o"
    private static final String NOTHING = " ."

    static void display(Grid grid) {

        for (int row = 0; row < grid.getRowSize(); row++) {
            for (int column = 0; column < grid.getColumnSize(row); column++) {

                int xy = grid.getPoint(row, column)
                xy == 1 ? print(CELL) : print(NOTHING)

            }
            println()
        }
    }
}
