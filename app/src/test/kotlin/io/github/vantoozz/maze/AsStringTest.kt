package io.github.vantoozz.maze

import kotlin.test.Test
import kotlin.test.assertEquals

internal class AsStringTest {
    @Test
    fun `empty maze`() {
        assertEquals(
            """
            ┌──┐
            │  │
            │  │
            └──┘
            """.trimIndent(),
            Maze.empty(3).asString()
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
            Maze.grid(3).asString()
        )
    }
}
