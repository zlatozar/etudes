## 'Game of life' rules

1. The immediate neighbors of a cell are those cells occupying the eight horizontally,
vertically, and diagonally adjacent cells.

2. If a LIFE cell has fewer than two immediate neighbors, it dies of loneliness. If a LIFE
cell has more than three immediate neighbors, it dies of overcrowding.

3. If an empty square has exactly three LIFE cells as immediate neighbors, a new cell is
born in the square.

4. Births and deaths all take place exactly at the change of generations. Thus a dying cell
may help birth a new one, but a newborn cell may not resurrect a dying cell, nor may one
dying cell stave off death for another by lowering the local population density.