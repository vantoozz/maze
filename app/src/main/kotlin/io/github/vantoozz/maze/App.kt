package io.github.vantoozz.maze

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.defaultLazy
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import io.github.vantoozz.maze.printer.AsciiPrinter

fun main(args: Array<String>) {
    App().main(args)
}


private class App : CliktCommand() {

    private val width
            by option("-w", "--width", help = "Maze width")
                .int()
                .required()

    private val height
            by option("-h", "--height", help = "Maze height")
                .int()
                .defaultLazy { width }

    override fun run() {
        println(Maze.random(height, width).printWith(AsciiPrinter()))
    }
}
