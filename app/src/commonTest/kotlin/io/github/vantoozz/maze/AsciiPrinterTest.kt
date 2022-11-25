package io.github.vantoozz.maze

import io.github.vantoozz.maze.printer.AsciiPrinter
import kotlin.test.Test
import kotlin.test.assertEquals

internal class AsciiPrinterTest {

    @Test
    fun `empty maze`() {
        assertEquals(
            """
            ┌──┐
            │  │
            │  │
            └──┘
            """.trimIndent(),
            Maze.empty(3)
                .printWith(AsciiPrinter())
        )
    }

    @Test
    fun `grid maze`() {
        assertEquals(
            """
            ┌┬┬┐
            ├┼┼┤
            ├┼┼┤
            └┴┴┘
            """.trimIndent(),
            Maze.grid(3)
                .printWith(AsciiPrinter())
        )
    }

    @Test
    fun `maze 1`() {
        assertEquals(
            """
            ┌┬┬┐
            │├┼┤
            ├┼┼┤
            └┴┴┘
            """.trimIndent(),
            Maze.grid(3)
                .openBottom(0, 0)
                .printWith(AsciiPrinter())
        )
    }

    @Test
    fun `maze 2`() {
        assertEquals(
            """
            ┌──┐
            ├─╴│
            │╶─┤
            └──┘
            """.trimIndent(),
            Maze.grid(3)
                .openRight(0, 0)
                .openRight(0, 1)
                .openBottom(0, 2)
                .openRight(1, 1)
                .openRight(1, 0)
                .openBottom(1, 0)
                .openRight(2, 0)
                .openRight(2, 1)
                .printWith(AsciiPrinter())
        )
    }

    @Test
    fun `maze 3`() {
        assertEquals(
            """
            ┌─┬┐
            │╷├┤
            ├┼┼┤
            └┴┴┘
            """.trimIndent(),
            Maze.grid(3)
                .openBottom(0, 0)
                .openRight(0, 0)
                .openBottom(0, 1)
                .printWith(AsciiPrinter())
        )
    }
}
