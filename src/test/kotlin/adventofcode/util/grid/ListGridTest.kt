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

class ListGridTest {
    @Test
    fun shouldConstructWithProperDimensions() {
        val grid = listOf(1, 2, 3, 4, 5, 6).toGrid(2)
        assertThat(grid.width()).isEqualTo(2)
        assertThat(grid.height()).isEqualTo(3)
    }

    @Test
    fun constructingWithInvalidListThrowsIllegalArgumentException() {
        assertFailsWith<IllegalArgumentException> { ListGrid(2, listOf(1, 2, 3, 4, 5)) }
    }

    @Test
    fun shouldExtractProperListContents() {
        val grid = ListGrid(3, listOf(1, 2, 3, 4, 5, 6))
        assertThat(grid[0, 0]).isEqualTo(1)
        assertThat(grid[1, 0]).isEqualTo(2)
        assertThat(grid[2, 0]).isEqualTo(3)
        assertThat(grid[0, 1]).isEqualTo(4)
        assertThat(grid[1, 1]).isEqualTo(5)
        assertThat(grid[2, 1]).isEqualTo(6)
    }

    @Test
    fun getShouldReturnProperValuesForPoint2D() {
        val grid = ListGrid(2, listOf(1, 2, 3, 4))
        assertThat(grid[Point2D(0, 0)]).isEqualTo(1)
        assertThat(grid[Point2D(1, 0)]).isEqualTo(2)
        assertThat(grid[Point2D(0, 1)]).isEqualTo(3)
        assertThat(grid[Point2D(1, 1)]).isEqualTo(4)
    }

    @Test
    fun rowAtShouldReturnRowContents() {
        val grid = ListGrid(2, listOf(1, 2, 3, 4))
        assertThat(grid.rowAt(0).toList()).isEqualTo(listOf(1, 2))
        assertThat(grid.rowAt(1).toList()).isEqualTo(listOf(3, 4))
    }

    @Test
    fun columnAtShouldReturnColumnContents() {
        val grid = ListGrid(2, listOf(1, 2, 3, 4))
        assertThat(grid.columnAt(0).toList()).isEqualTo(listOf(1, 3))
        assertThat(grid.columnAt(1).toList()).isEqualTo(listOf(2, 4))
    }

    @Test
    fun toStringShouldReturnStringValueOfLists() {
        val grid = ListGrid(2, listOf(1, 2, 3, 4))
        assertThat(grid.toString()).isEqualTo("[1, 2]\n[3, 4]")
    }
}