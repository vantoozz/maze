package io.github.vantoozz.maze

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
