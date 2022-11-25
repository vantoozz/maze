package io.github.vantoozz.maze.printer

import io.github.vantoozz.maze.Maze

internal interface Printer {
    fun Maze.print(): String
}
