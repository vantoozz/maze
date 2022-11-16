package io.github.vantoozz.maze

import kotlin.math.abs

internal data class Cell(
    val borders: Borders,
    val visited: Boolean,
) {
    internal data class Borders(
        val top: Boolean = false,
        val right: Boolean = false,
        val bottom: Boolean = false,
        val left: Boolean = false,
    )

    fun openTop() = copy(
        borders = borders.copy(top = false)
    )

    fun openRight() = copy(
        borders = borders.copy(right = false)
    )

    fun openBottom() = copy(
        borders = borders.copy(bottom = false)
    )

    fun openLeft() = copy(
        borders = borders.copy(left = false)
    )


    val closedRight = borders.right
    val closedBottom = borders.bottom

    companion object {
        fun closedTopLeft() = Cell(Borders(top = true, left = true), false)
        fun closedTopRight() = Cell(Borders(top = true, right = true), false)
        fun closedTop() = Cell(Borders(top = true), false)
        fun closedBottomLeft() = Cell(Borders(bottom = true, left = true), false)
        fun closedBottomRight() = Cell(Borders(bottom = true, right = true), false)
        fun closedBottom() = Cell(Borders(bottom = true), false)
        fun closedLeft() = Cell(Borders(left = true), false)
        fun closedRight() = Cell(Borders(right = true), false)
        fun open() = Cell(Borders(), false)
        fun closed() = Cell(Borders(top = true, right = true, bottom = true, left = true), false)
    }
}

internal data class Maze(
    val size: Int,
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
    private fun cell(coordinates: Coordinates) = cell(coordinates.y, coordinates.x)

    fun openRight(y: Int, x: Int): Maze {
        val map = cells.toMutableMap()
        val row = map[y]?.toMutableMap() ?: mutableMapOf()
        row[x] = cell(y, x).openRight()
        cells[y]?.get(x + 1)?.let {
            row.put(x + 1, it.openLeft())

        }

        return Maze(size, map.apply {
            put(y, row)
        })
    }

    fun openBottom(y: Int, x: Int): Maze {
        val map = cells.toMutableMap()
        val row = map[y]?.toMutableMap() ?: mutableMapOf()
        row[x] = cell(y, x).openBottom()
        val nextRow = map[y + 1]?.toMutableMap()?.apply {
            get(x)?.let {
                put(x, it.openTop())
            }
        }

        return Maze(size, map.apply {
            put(y, row)
            nextRow?.let {
                put(y + 1, nextRow)
            }
        })
    }

    fun visit(coordinates: Coordinates): Maze {
        val map = cells.toMutableMap()
        val row = map[coordinates.y]?.toMutableMap() ?: mutableMapOf()
        row[coordinates.x] = cell(coordinates).copy(visited = true)
        return Maze(size, map.apply {
            put(coordinates.y, row)
        })
    }

    fun completed() = size * size == cells.entries.fold(0) { count, row ->
        count + row.value.values.count { it.visited }
    }

    fun openBetween(one: Coordinates, two: Coordinates) =
        if (one.y == two.y && abs(one.x - two.x) == 1)
            openRight(one.y, minOf(one.x, two.x))
        else if (one.x == two.x && abs(one.y - two.y) == 1)
            openBottom(minOf(one.y, two.y), one.x)
        else throw RuntimeException("Not neighbours $one $two")


    fun randomUnvisitedNeighbour(coordinates: Coordinates) =
        setOf(
            Pair(coordinates.y - 1, coordinates.x),
            Pair(coordinates.y + 1, coordinates.x),
            Pair(coordinates.y, coordinates.x - 1),
            Pair(coordinates.y, coordinates.x + 1)
        )
            .asSequence()
            .filterNot { it.first < 0 }
            .filterNot { it.first >= size }
            .filterNot { it.second < 0 }
            .filterNot { it.second >= size }
            .map { it to cell(it.first, it.second) }
            .filterNot { it.second.visited }
            .toList()
            .randomOrNull()
            ?.let {
                Coordinates(it.first.first, it.first.second)
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
