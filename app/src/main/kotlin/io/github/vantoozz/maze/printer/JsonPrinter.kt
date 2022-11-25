package io.github.vantoozz.maze.printer

import io.github.vantoozz.maze.Maze

internal class JsonPrinter : Printer {
    override fun Maze.print(): String = sequence {
        repeat(height) { y ->
            yield(sequence {

                repeat(width) { x ->

                    val upperCell = cellOrNull(y - 1, x)
                    val leftCell = cellOrNull(y, x - 1)
                    val upperLeftCell = cellOrNull(y - 1, x - 1)

                    val bits = mutableSetOf<Int>()

                    if (upperCell == null) {
                        bits.add(0b1000)
                        bits.add(0b0100)
                    } else if (upperCell.closedBottom) {
                        bits.add(0b1000)
                        bits.add(0b0100)
                    }


                    if (leftCell == null) {
                        bits.add(0b1000)
                        bits.add(0b0010)
                    } else if (leftCell.closedRight) {
                        bits.add(0b1000)
                        bits.add(0b0010)
                    }

                    if (upperLeftCell?.closedBottom == true) {
                        bits.add(0b1000)
                    }
                    if (upperLeftCell?.closedRight == true) {
                        bits.add(0b1000)
                    }

                    yield(bits.sum())
                }
            }.toList())
        }
    }.toList().map { row ->
        row.map { cellInt ->
            cellInt.toString(radix = 2)
                .padStart(4, '0')
                .let {
                    it.take(2) to it.takeLast(2)
                }

        }.fold(mutableListOf("", "")) { rows, cell ->
            rows.also {
                it[0] += cell.first
                it[1] += cell.second
            }
        }.map {
            it + "1"
        }
    }.flatten().let {
        it + "1".repeat(width * 2 + 1)
    }.joinToString(",\n") {
        "[${it.toCharArray().joinToString(",")}]"
    }.let {
        "[\n$it\n]"
    }
}
