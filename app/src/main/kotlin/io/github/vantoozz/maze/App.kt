package io.github.vantoozz.maze

import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import io.github.vantoozz.kli.commands.SimpleCommand
import io.github.vantoozz.kli.kli

fun main(args: Array<String>) = kli(Generate()).main(args)

private class Generate : SimpleCommand() {

    private val size
            by option("-s", "--size", help = "Maze size")
                .int()
                .required()

    override fun handle() {

        printMaze(
            Maze.grid(size)
                .openBottom(1, 1)
                .openRight(1, 0)
        )
        printMaze(
            Maze.grid(size)
                .openBottom(1, 1)
                .openRight(1, 1)
        )
        printMaze(
            Maze.grid(size)
                .openBottom(0, 1)
                .openRight(1, 0)
        )
        printMaze(
            Maze.grid(size)
                .openBottom(0, 1)
                .openRight(1, 1)
        )
        printMaze(
            Maze.grid(size)
                .openBottom(0, 1)
                .openRight(1, 1)
                .openRight(0, 0)
        )
        printMaze(
            Maze.empty(size)
        )
    }

    private fun printMaze(maze: Maze) {
        repeat(size + 1) { y ->
            repeat(size + 1) { x ->
                when (y) {
                    0 -> when (x) {
                        0 -> print('┌')
                        size -> print('┐')
                        else -> if (maze.cell(y, x).borders.left) print('┬') else print('─')
                    }
                    size -> when (x) {
                        0 -> print('└')
                        size -> print('┘')
                        else -> if (maze.cell(y - 1, x).borders.left) print('┴') else print('─')
                    }
                    else -> when (x) {
                        0 -> if (maze.cell(y, x).borders.top) print('├') else print('│')
                        size -> if (maze.cell(y, x - 1).borders.top) print('┤') else print('│')
                        else -> {
                            var sign = ' '

                            if (maze.cell(y - 1, x - 1).borders.bottom) {
                                if (maze.cell(y - 1, x - 1).borders.right) {
                                    sign = if (maze.cell(y, x - 1).borders.right) {
                                        if (maze.cell(y - 1, x).borders.bottom) {
                                            '┼'
                                        } else {
                                            '┤'
                                        }
                                    } else {
                                        if (maze.cell(y - 1, x).borders.bottom) {
                                            '┴'
                                        } else {
                                            '┘'
                                        }
                                    }
                                } else {
                                    sign = if (maze.cell(y, x - 1).borders.right) {
                                        if (maze.cell(y - 1, x).borders.bottom) {
                                            '┬'
                                        } else {
                                            '┐'
                                        }
                                    } else {
                                        '─'
                                    }
                                }
                            } else {
                                if (maze.cell(y - 1, x).borders.right) {
                                    if (maze.cell(y - 1, x).borders.bottom) {
                                        sign = if (maze.cell(y - 1, x - 1).borders.right) {

                                            if (maze.cell(y, x).borders.left) {
                                                '├'
                                            } else {
                                                '└'
                                            }
                                        } else {
                                            '┌'
                                        }
                                    } else {
                                        sign = if (maze.cell(y, x).borders.left) {
                                            '│'
                                        } else {
                                            ' '
                                        }
                                    }
                                } else {
                                    sign =   ' '
                                }
                            }

                            print(sign)
                        }
                    }
                }

            }
            print('\n')
        }
        print('\n')
    }
}
