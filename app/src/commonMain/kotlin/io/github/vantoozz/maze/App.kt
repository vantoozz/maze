package io.github.vantoozz.maze

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.defaultLazy
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.int
import io.github.vantoozz.maze.printer.AsciiPrinter
import io.github.vantoozz.maze.printer.JsonPrinter

fun main(args: Array<String>) = App().main(args)

private class App : CliktCommand() {

    private val width
            by option("-w", "--width", help = "Maze width")
                .int()
                .required()

    private val height
            by option("-h", "--height", help = "Maze height")
                .int()
                .defaultLazy { width }

    private val format
            by option("-f", "--format", help = "Output format")
                .enum<Format>()
                .default(Format.ASCII)

    override fun run() = println(
        Maze.random(height, width)
            .printWith(
                when (format) {
                    Format.ASCII -> AsciiPrinter()
                    Format.JSON -> JsonPrinter()
                }
            )
    )

    private enum class Format {
        ASCII,
        JSON,
    }
}
