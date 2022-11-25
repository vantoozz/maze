package io.github.vantoozz.maze

import io.github.vantoozz.maze.printer.JsonPrinter
import kotlin.test.Test
import kotlin.test.assertEquals

internal class JsonPrinterTest {

    @Test
    fun `empty maze`() {
        assertEquals(
            """
            [
            [1,1,1,1,1,1,1],
            [1,0,0,0,0,0,1],
            [1,0,0,0,0,0,1],
            [1,0,0,0,0,0,1],
            [1,0,0,0,0,0,1],
            [1,0,0,0,0,0,1],
            [1,1,1,1,1,1,1]
            ]
            """.trimIndent(),
            Maze.empty(3)
                .printWith(JsonPrinter())
        )
    }

    @Test
    fun `grid maze`() {
        assertEquals(
            """
            [
            [1,1,1,1,1,1,1],
            [1,0,1,0,1,0,1],
            [1,1,1,1,1,1,1],
            [1,0,1,0,1,0,1],
            [1,1,1,1,1,1,1],
            [1,0,1,0,1,0,1],
            [1,1,1,1,1,1,1]
            ]
            """.trimIndent(),
            Maze.grid(3)
                .printWith(JsonPrinter())
        )
    }

    @Test
    fun `maze 1`() {
        assertEquals(
            """
            [
            [1,1,1,1,1,1,1],
            [1,0,1,0,1,0,1],
            [1,0,1,1,1,1,1],
            [1,0,1,0,1,0,1],
            [1,1,1,1,1,1,1],
            [1,0,1,0,1,0,1],
            [1,1,1,1,1,1,1]
            ]
            """.trimIndent(),
            Maze.grid(3)
                .openBottom(0, 0)
                .printWith(JsonPrinter())
        )
    }

    @Test
    fun `maze 2`() {
        assertEquals(
            """
            [
            [1,1,1,1,1,1,1],
            [1,0,0,0,0,0,1],
            [1,1,1,1,1,0,1],
            [1,0,0,0,0,0,1],
            [1,0,1,1,1,1,1],
            [1,0,0,0,0,0,1],
            [1,1,1,1,1,1,1]
            ]
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
                .printWith(JsonPrinter())
        )
    }

    @Test
    fun `maze 3`() {
        assertEquals(
            """
            [
            [1,1,1,1,1,1,1],
            [1,0,0,0,1,0,1],
            [1,0,1,0,1,1,1],
            [1,0,1,0,1,0,1],
            [1,1,1,1,1,1,1],
            [1,0,1,0,1,0,1],
            [1,1,1,1,1,1,1]
            ]
            """.trimIndent(),
            Maze.grid(3)
                .openBottom(0, 0)
                .openRight(0, 0)
                .openBottom(0, 1)
                .printWith(JsonPrinter())
        )
    }
}
