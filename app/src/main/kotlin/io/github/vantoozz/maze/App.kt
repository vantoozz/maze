package io.github.vantoozz.maze

import com.github.ajalt.clikt.parameters.options.defaultLazy
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import io.github.vantoozz.kli.commands.SimpleCommand
import io.github.vantoozz.kli.kli

fun main(args: Array<String>) = kli(Generate()).main(args)

private class Generate : SimpleCommand() {

    private val width
            by option("-w", "--width", help = "Maze width")
                .int()
                .required()

    private val height
            by option("-h", "--height", help = "Maze height")
                .int()
                .defaultLazy { width }

    override fun handle() {
        val start = Coordinates(0, 0)
        val stack = ArrayDeque<Coordinates>()
            .apply {
                addLast(start)
            }

        val result = makeMaze(
            Maze.grid(height, width),
            stack,
            LongestPath(stack.size, start)
        )
        result.first.print()
        println(result.second)

    }
}

internal tailrec fun makeMaze(
    initialMaze: Maze,
    stack: ArrayDeque<Coordinates>,
    longestPath: LongestPath,
): Pair<Maze, LongestPath> {
    val visitedMaze = initialMaze.visit(stack.last())

    if (visitedMaze.completed()) {
        return visitedMaze to longestPath
    }


    val updatedMaze = visitedMaze.randomUnvisitedNeighbour(stack.last())?.let { nextStep ->
        visitedMaze.openBetween(stack.last(), nextStep).also {
            stack.addLast(nextStep)
        }
    } ?: visitedMaze.also {
        stack.removeLast()
    }

    return makeMaze(
        updatedMaze, stack, if (stack.size > longestPath.length) {
            LongestPath(stack.size, stack.last())
        } else longestPath
    )
}

internal data class Coordinates(
    val y: Int,
    val x: Int,
)

internal data class LongestPath(
    val length: Int,
    val coordinates: Coordinates,
)
