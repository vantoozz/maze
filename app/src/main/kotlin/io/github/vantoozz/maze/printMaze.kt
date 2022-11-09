package io.github.vantoozz.maze

internal fun Maze.print() {
    repeat(size + 1) { y ->
        repeat(size + 1) { x ->
            when (y) {
                0 -> when (x) {
                    0 -> print('┌')
                    size -> print('┐')
                    else -> if (cell(y, x - 1).closedRight) print('┬') else print('─')
                }
                size -> when (x) {
                    0 -> print('└')
                    size -> print('┘')
                    else -> if (cell(y - 1, x - 1).closedRight) print('┴') else print('─')
                }
                else -> {
                    when (x) {
                        0 -> if (cell(y - 1, x).closedBottom) print('├') else print('│')
                        size -> if (cell(y - 1, x - 1).closedBottom) print('┤') else print('│')
                        else -> {

                            val upperCell = cell(y - 1, x)
                            val leftCell = cell(y, x - 1)
                            val upperLeftCell = cell(y - 1, x - 1)

                            val bit0 = leftCell.closedRight
                            val bit1 = upperCell.closedBottom
                            val bit3 = upperLeftCell.closedRight
                            val bit2 = upperLeftCell.closedBottom
                            val bit4 = upperCell.closedRight

                            val signs = if (bit0) {
                                listOf('┼', '┬', '├', ' ', '┌', ' ', '┤', '┐', '│', ' ')
                            } else {
                                listOf('┴', '─', '└', ' ', '┌', ' ', '┘', '─', ' ', ' ')
                            }

                            val sign =
                                if (bit1) {
                                    if (bit2) {
                                        if (bit3) {
                                            signs[0]
                                        } else {
                                            signs[1]
                                        }
                                    } else {
                                        if (bit3) {
                                            if (bit4) {
                                                signs[2]
                                            } else {
                                                signs[3]
                                            }
                                        } else {
                                            if (bit4) {
                                                signs[4]
                                            } else {
                                                signs[5]
                                            }
                                        }
                                    }
                                } else {
                                    if (bit2) {
                                        if (bit3) {
                                            signs[6]
                                        } else {
                                            signs[7]
                                        }
                                    } else {
                                        if (bit4) {
                                            signs[8]
                                        } else {
                                            signs[9]
                                        }
                                    }
                                }

                            print(sign)
                        }
                    }
                }
            }
        }
        print('\n')
    }
    print('\n')
}
