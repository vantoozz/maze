package io.github.vantoozz.maze

internal data class Cell(
    val borders: Borders,
) {
    internal data class Borders(
        val top: Boolean = false,
        val right: Boolean = false,
        val bottom: Boolean = false,
        val left: Boolean = false,
    )

    companion object {
        fun closedTopLeft() = Cell(Borders(top = true, left = true))
        fun closedTopRight() = Cell(Borders(top = true, right = true))
        fun closedTop() = Cell(Borders(top = true))
        fun closedBottomLeft() = Cell(Borders(bottom = true, left = true))
        fun closedBottomRight() = Cell(Borders(bottom = true, right = true))
        fun closedBottom() = Cell(Borders(bottom = true))
        fun closedLeft() = Cell(Borders(left = true))
        fun closedRight() = Cell(Borders(right = true))
        fun open() = Cell(Borders())
        fun closed() = Cell(Borders(top = true, right = true, bottom = true, left = true))
    }
}

internal data class Maze(
    private val size: Int,
    private val cells: Map<Int, Map<Int, Cell>>,
) {
    init {
        if (size < 2) {
            throw RuntimeException("Maze size cannot be smaller that 2")
        }

        repeat(size) { y ->
            cells[y]?.let { row ->
                repeat(size) { x ->
                    if (row[x] == null) {
                        throw RuntimeException("No cell [$y][$x]")
                    }
                    if (y > 0) {
                        if (cells[y]?.get(x)?.borders?.top != cells[y - 1]?.get(x)?.borders?.bottom) {
                            throw RuntimeException("Inconsistent cell at $y : $x")
                        }
                    }
                    if (x > 0) {
                        if (cells[y]?.get(x)?.borders?.left != cells[y]?.get(x - 1)?.borders?.right) {
                            throw RuntimeException("Inconsistent cell at $y : $x")
                        }
                    }
                    if (y == size - 1) {
                        if (cells[y]?.get(x)?.borders?.bottom != true) {
                            throw RuntimeException("Inconsistent cell at $y : $x")
                        }
                    }
                    if (y == 0) {
                        if (cells[y]?.get(x)?.borders?.top != true) {
                            throw RuntimeException("Inconsistent cell at $y : $x")
                        }
                    }
                    if (x == size - 1) {
                        if (cells[y]?.get(x)?.borders?.right != true) {
                            throw RuntimeException("Inconsistent cell at $y : $x")
                        }
                    }
                    if (x == 0) {
                        if (cells[y]?.get(x)?.borders?.left != true) {
                            throw RuntimeException("Inconsistent cell at $y : $x")
                        }
                    }
                }
            } ?: throw RuntimeException("No row [$y]")
        }
    }

    fun cell(y: Int, x: Int) = cells[y]?.get(x) ?: throw RuntimeException("No such cell $y : $x ")

    fun openRight(y: Int, x: Int): Maze {
        val map = cells.toMutableMap()
        val row = map[y]?.toMutableMap() ?: mutableMapOf()
        row[x] = Cell(cell(y, x).borders.copy(right = false))
        cells[y]?.get(x + 1)?.let {
            row.put(x + 1, Cell(it.borders.copy(left = false)))

        }

        return Maze(size, map.apply {
            put(y, row)
        })
    }

    fun openBottom(y: Int, x: Int): Maze {
        val map = cells.toMutableMap()
        val row = map[y]?.toMutableMap() ?: mutableMapOf()
        row[x] = Cell(cell(y, x).borders.copy(bottom = false))
        val nextRow = map[y + 1]?.toMutableMap()?.apply {
            get(x)?.let {
                put(x, Cell(it.borders.copy(top = false)))
            }
        }

        return Maze(size, map.apply {
            put(y, row)
            nextRow?.let {
                put(y + 1, nextRow)
            }
        })
    }

    companion object {
        fun empty(size: Int) = emptySequence(size).toMazeMap(size)

        fun grid(size: Int) = gridSequence(size).toMazeMap(size)

        private fun Sequence<Triple<Int, Int, Cell>>.toMazeMap(size: Int) =
            fold(mutableMapOf<Int, MutableMap<Int, Cell>>()) { carry, (y, x, cell) ->
                carry.also { map ->
                    map[y] = (map[y] ?: mutableMapOf()).also { row ->
                        row[x] = cell
                    }
                }
            }.let {
                Maze(size, it)
            }

        private fun emptySequence(size: Int) = sequence {
            repeat(size) { y ->
                repeat(size) { x ->
                    yield(
                        Triple(
                            y, x,
                            when (y) {
                                0 -> when (x) {
                                    0 -> Cell.closedTopLeft()
                                    size - 1 -> Cell.closedTopRight()
                                    else -> Cell.closedTop()
                                }
                                size - 1 -> when (x) {
                                    0 -> Cell.closedBottomLeft()
                                    size - 1 -> Cell.closedBottomRight()
                                    else -> Cell.closedBottom()
                                }
                                else -> when (x) {
                                    0 -> Cell.closedLeft()
                                    size - 1 -> Cell.closedRight()
                                    else -> Cell.open()
                                }
                            }
                        )
                    )
                }
            }
        }

        private fun gridSequence(size: Int) = sequence {
            repeat(size) { y ->
                repeat(size) { x ->
                    yield(
                        Triple(y, x, Cell.closed())
                    )
                }
            }
        }
    }

}
