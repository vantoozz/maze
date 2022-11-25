package io.github.vantoozz.maze

import io.github.vantoozz.maze.printer.JsonPrinter
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class AsJsonTest {

    @ParameterizedTest
    @MethodSource("examples")
    fun `it converts maze to string`(
        maze: Maze,
        expected: String,
    ) {
        assertEquals(expected, maze.printWith(JsonPrinter()))
    }

    companion object {
        @JvmStatic
        fun examples(): List<Arguments> =
            listOf(
                Arguments.of(
                    Maze.empty(3),
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
                ),
                Arguments.of(
                    Maze.grid(3),
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
                ),
                Arguments.of(
                    Maze.grid(3)
                        .openBottom(0, 0),
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
                ),
                Arguments.of(
                    Maze.grid(3)
                        .openBottom(0, 0)
                        .openRight(0, 0)
                        .openBottom(0, 1),
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
                ),
            )
    }
}
