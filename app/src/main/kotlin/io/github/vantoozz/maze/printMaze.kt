package io.github.vantoozz.maze

internal fun Maze.asJson(): String = sequence {
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

internal fun Maze.asString() = sequence {
    repeat(height + 1) { y ->

        yield(sequence {

            repeat(width + 1) { x ->
                when (y) {
                    0 -> when (x) {
                        0 -> yield('┌')
                        width -> yield('┐')
                        else -> if (cell(y, x - 1).closedRight) yield('┬') else yield('─')
                    }
                    height -> when (x) {
                        0 -> yield('└')

                        width -> yield('┘')
                        else -> if (cell(y - 1, x - 1).closedRight) yield('┴') else yield('─')
                    }
                    else -> {
                        when (x) {
                            0 -> if (cell(y - 1, x).closedBottom) yield('├') else yield('│')
                            width -> if (cell(y - 1, x - 1).closedBottom) yield('┤') else yield('│')
                            else -> {

                                val upperCell = cell(y - 1, x)
                                val leftCell = cell(y, x - 1)
                                val upperLeftCell = cell(y - 1, x - 1)

                                val bits = mutableSetOf<Int>()

                                if (leftCell.closedRight) bits.add(0b1000)
                                if (upperCell.closedBottom) bits.add(0b0100)
                                if (upperLeftCell.closedBottom) bits.add(0b0010)
                                if (upperLeftCell.closedRight) bits.add(0b0001)

                                val chars = listOf(
                                    ' ',
                                    '╵',
                                    '╴',
                                    '┘',
                                    '╶',
                                    '└',
                                    '─',
                                    '┴',
                                    '╷',
                                    '│',
                                    '┐',
                                    '┤',
                                    '┌',
                                    '├',
                                    '┬',
                                    '┼',
                                )
                                yield(chars[bits.sum()])
                            }
                        }
                    }
                }
            }
        }.joinToString(""))
    }
}.joinToString("\n")
