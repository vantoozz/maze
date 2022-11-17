package io.github.vantoozz.maze

internal fun Maze.print() {
    println(asString())
}

internal fun Maze.asString() = sequence {
    repeat(size + 1) { y ->
        repeat(size + 1) { x ->
            when (y) {
                0 -> when (x) {
                    0 -> yield('┌')
                    size -> yield('┐')
                    else -> if (cell(y, x - 1).closedRight) yield('┬') else yield('─')
                }
                size -> when (x) {
                    0 -> yield('└')
                    size -> yield('┘')
                    else -> if (cell(y - 1, x - 1).closedRight) yield('┴') else yield('─')
                }
                else -> {
                    when (x) {
                        0 -> if (cell(y - 1, x).closedBottom) yield('├') else yield('│')
                        size -> if (cell(y - 1, x - 1).closedBottom) yield('┤') else yield('│')
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
        yield('\n')
    }
    yield('\n')
}.joinToString("")
