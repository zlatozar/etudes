package GameOfLife

class Point {

    private final int MAX_COLUMN
    private final int MAX_ROW

    private final int MIN_COLUMN = 0
    private final int MIN_ROW = 0

    /* To MAX - 1 because starts from 0 */
    Point(int maxROW, int maxColumn) {

        this.MAX_ROW = maxROW - 1
        this.MAX_COLUMN = maxColumn - 1
    }

    /**
     * Find neighbors and return how many are alive
     */
    Tuple2[] getNeighborsCoordinates(int row, int column) {

        if (row > MAX_ROW || column > MAX_COLUMN) {
            throw new IllegalArgumentException("Coordinates are out ouf scope for a given grid")
        }

        def coordinates = [

                new Tuple2(row - 1, column - 1),
                new Tuple2(row - 1, column    ),
                new Tuple2(row - 1, column + 1),

                new Tuple2(row    , column - 1),
                new Tuple2(row    , column + 1),

                new Tuple2(row + 1, column - 1),
                new Tuple2(row + 1, column    ),
                new Tuple2(row + 1, column + 1)

                ].findAll({  // take only valid points

                    xy -> (xy.first  >= MIN_ROW && xy.first  <= MAX_ROW)     \
                          &&                                                 \
                          (xy.second >= MIN_COLUMN && xy.second <= MAX_COLUMN)
                })

        return coordinates
    }
}
