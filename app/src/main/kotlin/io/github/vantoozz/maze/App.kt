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
        makeMaze(
            Maze.grid(size),
            ArrayDeque<Coordinates>().apply {
                addLast(Coordinates(0, 0))
            }
        ).print()

    }
}

internal tailrec fun makeMaze(initialMaze: Maze, stack: ArrayDeque<Coordinates>): Maze {
    val visitedMaze = initialMaze.visit(stack.last())

    if (visitedMaze.completed()) {
        return visitedMaze
    }


    val updatedMaze = visitedMaze.randomUnvisitedNeighbour(stack.last())?.let { nextStep ->
        visitedMaze.openBetween(stack.last(), nextStep).also {
            stack.addLast(nextStep)
        }
    } ?: visitedMaze.also {
        stack.removeLast()
    }

    return makeMaze(updatedMaze, stack)
}

data class Coordinates(
    val y: Int,
    val x: Int,
)
