package io.github.vantoozz.maze

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class AsStringTest {

    @ParameterizedTest
    @MethodSource("examples")
    fun `it converts maze to string`(
        maze: Maze,
        expected: String,
    ) {
        assertEquals(expected, maze.asString())
    }

    companion object {
        @JvmStatic
        fun examples(): List<Arguments> =
            listOf(
                Arguments.of(
                    Maze.empty(3),
                    """
                    ┌──┐
                    │  │
                    │  │
                    └──┘
                    """.trimIndent(),
                ),
                Arguments.of(
                    Maze.grid(3),
                    """
                    ┌┬┬┐
                    ├┼┼┤
                    ├┼┼┤
                    └┴┴┘
                    """.trimIndent(),
                ),
                Arguments.of(
                    Maze.grid(3)
                        .openBottom(0, 0),
                    """
                    ┌┬┬┐
                    │├┼┤
                    ├┼┼┤
                    └┴┴┘
                    """.trimIndent(),
                ),
                Arguments.of(
                    Maze.grid(3)
                        .openRight(0, 0)
                        .openRight(0, 1)
                        .openBottom(0, 2)
                        .openRight(1, 1)
                        .openRight(1, 0)
                        .openBottom(1, 0)
                        .openRight(2, 0)
                        .openRight(2, 1),
                    """
                    ┌──┐
                    ├─╴│
                    │╶─┤
                    └──┘
                    """.trimIndent(),
                ),
            )
    }
}
