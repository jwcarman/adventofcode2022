/*
 * Copyright (c) 2023 James Carman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package adventofcode.util.grid

import adventofcode.util.geom.plane.Point2D
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class TextGridTest {
    @Test
    fun toStringShouldReturnLinesSeparatedByNewline() {
        val grid = listOf("12", "34").toGrid()
        assertThat(grid.toString()).isEqualTo("12\n34")
    }

    @Test
    fun getShouldReturnProperValues() {
        val grid = TextGrid(listOf("12", "34"))
        assertThat(grid[0, 0]).isEqualTo('1')
        assertThat(grid[1, 0]).isEqualTo('2')
        assertThat(grid[0, 1]).isEqualTo('3')
        assertThat(grid[1, 1]).isEqualTo('4')
    }

    @Test
    fun getShouldReturnProperValuesForPoint2D() {
        val grid = TextGrid(listOf("12", "34"))
        assertThat(grid[Point2D(0, 0)]).isEqualTo('1')
        assertThat(grid[Point2D(1, 0)]).isEqualTo('2')
        assertThat(grid[Point2D(0, 1)]).isEqualTo('3')
        assertThat(grid[Point2D(1, 1)]).isEqualTo('4')
    }

    @Test
    fun valuesShouldReturnRowsFirst() {
        val grid = TextGrid(listOf("12", "34"))
        assertThat(grid.values().toList()).isEqualTo(listOf('1', '2', '3', '4'))
    }

    @Test
    fun coordinatesShouldReturnRowsFirst() {
        val grid = TextGrid(listOf("12", "34"))
        assertThat(grid.coordinates().toList()).isEqualTo(
            listOf(
                Point2D(0, 0),
                Point2D(1, 0),
                Point2D(0, 1),
                Point2D(1, 1)
            )
        )
    }

    @Test
    fun widthShouldBeWidestRow() {
        val grid = TextGrid(listOf("12", "456"))
        assertThat(grid.width()).isEqualTo(3)
        assertThat(grid[2, 0]).isEqualTo(' ')
    }

    @Test
    fun heightShouldBeNumberOfLines() {
        val grid = TextGrid(listOf("12", "34"))
        assertThat(grid.height()).isEqualTo(2)
    }

    @Test
    fun shouldUseCustomPadChar() {
        val grid = TextGrid(listOf("12", "456"), 'X')
        assertThat(grid[2, 0]).isEqualTo('X')
    }

    @Test
    fun rowAtShouldReturnRowContents() {
        val grid = TextGrid(listOf("12", "34"))
        assertThat(grid.rowAt(0).toList()).isEqualTo(listOf('1', '2'))
        assertThat(grid.rowAt(1).toList()).isEqualTo(listOf('3', '4'))
    }

    @Test
    fun columnAtShouldReturnColumnContents() {
        val grid = TextGrid(listOf("12", "34"))
        assertThat(grid.columnAt(0).toList()).isEqualTo(listOf('1', '3'))
        assertThat(grid.columnAt(1).toList()).isEqualTo(listOf('2', '4'))
    }

    @Test
    fun getOutOfBoundsThrowsIllegalArgumentException() {
        val grid = TextGrid(listOf("12", "34"))
        assertFailsWith<IllegalArgumentException> { grid[-1, 0] }
        assertFailsWith<IllegalArgumentException> { grid[2, 0] }
        assertFailsWith<IllegalArgumentException> { grid[0, -1] }
        assertFailsWith<IllegalArgumentException> { grid[0, 2] }
    }
}