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


        Maze.empty(size).print()
    }
}
