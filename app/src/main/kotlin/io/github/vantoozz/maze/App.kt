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
        Maze.grid(size)
            .openRight(2,0)
            .print()
        Maze.grid(size)
            .openBottom(1, 1)
            .openRight(1, 0)
            .print()

        Maze.grid(size)
            .openBottom(1, 1)
            .openRight(1, 1)
            .print()

        Maze.grid(size)
            .openBottom(0, 1)
            .openRight(1, 0)
            .print()

        Maze.grid(size)
            .openBottom(0, 1)
            .openRight(1, 1)
            .print()

        Maze.grid(size)
            .openBottom(0, 1)
            .openRight(1, 1)
            .openRight(0, 0)
            .print()

        Maze.grid(size)
            .openBottom(0, 0)
            .openRight(1, 1)
            .openRight(0, 0)
            .openRight(0, 1)
            .print()

        Maze.grid(3)
            .openRight(0, 0)
            .openRight(0, 1)
            .openBottom(0, 0)
            .openBottom(0, 1)
            .openBottom(0, 2)
            .openBottom(1, 1)
            .openRight(2, 1)
            .print()
//
        Maze.empty(size).print()
//
        Maze.grid(3)
            .openBottom(0, 0)
            .openBottom(1, 2)
            .openBottom(1, 0)
            .openRight(2, 0)
            .openRight(2, 1)
            .openRight(1, 1)
            .print()

        Maze.grid(3)
            .openBottom(0, 1)
            .openBottom(1, 1)
            .openRight(0, 0)
            .openRight(2, 0)
            .openBottom(1, 0)
            .print()

        Maze.grid(3)
            .openRight(0, 0)
            .openRight(0, 1)
            .openBottom(0,2)
            .openBottom(1,2)
            .openRight(2, 1)
            .openRight(2, 0)
            .openBottom(1,0)
            .openRight(1,0)
            .print()

        makeMaze(
            Maze.grid(size),
            ArrayDeque<Coordinates>().apply {
                addLast(Coordinates(0, 0))
            }
        )

    }
}

internal tailrec fun makeMaze(initialMaze: Maze, stack: ArrayDeque<Coordinates>): Maze {
    val visitedMaze = initialMaze.visit(stack.last())

    if (visitedMaze.completed()) {
        return visitedMaze
    }


    val updatedMaze = visitedMaze.randomUnvisitedNeighbour(stack.last())?.let {nextStep->
        visitedMaze.openBetween(stack.last(), nextStep).also {
            stack.addLast(nextStep)
        }
    } ?: visitedMaze.also {
        stack.removeLast()
    }

    updatedMaze.print()

    return makeMaze(updatedMaze, stack)
}

data class Coordinates(
    val y: Int,
    val x: Int,
)
