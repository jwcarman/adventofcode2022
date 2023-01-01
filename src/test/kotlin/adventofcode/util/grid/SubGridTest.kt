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

class SubGridTest {

    @Test
    fun subGridWidthShouldBeAsSpecified() {
        val original = TextGrid(listOf("123", "456", "789"))

        val sub = original.subGrid(1, 0, 2, 3)
        assertThat(sub.width()).isEqualTo(2)
    }

    @Test
    fun subGridHeightShouldBeAsSpecified() {
        val original = TextGrid(listOf("123", "456", "789"))

        val sub = original.subGrid(Point2D(1, 0), 2, 3)
        assertThat(sub.height()).isEqualTo(3)
    }

    @Test
    fun subGridShouldReturnCorrectValues() {
        val original = TextGrid(listOf("123", "456", "789"))

        val sub = original.subGrid(1, 0, 2, 3)

        assertThat(sub.values().toList()).isEqualTo(listOf('2', '3', '5', '6', '8', '9'))
    }

    @Test
    fun subGridShouldReturnCorrectRows() {
        val original = TextGrid(listOf("123", "456", "789"))

        val sub = original.subGrid(1, 0, 2, 3)

        assertThat(sub.rowAt(0).toList()).isEqualTo(listOf('2', '3'))
        assertThat(sub.rowAt(1).toList()).isEqualTo(listOf('5', '6'))
        assertThat(sub.rowAt(2).toList()).isEqualTo(listOf('8', '9'))
    }

    @Test
    fun subGridShouldReturnCorrectColumns() {
        val original = TextGrid(listOf("123", "456", "789"))

        val sub = original.subGrid(1, 0, 2, 3)

        assertThat(sub.columnAt(0).toList()).isEqualTo(listOf('2', '5', '8'))
        assertThat(sub.columnAt(1).toList()).isEqualTo(listOf('3', '6', '9'))
    }

    @Test
    fun subGridShouldNotAllowInvalidValues() {
        val original = TextGrid(listOf("123", "456", "789"))

        assertFailsWith<IllegalArgumentException> { original.subGrid(-1, 0, 1, 1) }
        assertFailsWith<IllegalArgumentException> { original.subGrid(0, -1, 1, 1) }
        assertFailsWith<IllegalArgumentException> { original.subGrid(3, 0, 1, 1) }
        assertFailsWith<IllegalArgumentException> { original.subGrid(0, 3, 1, 1) }

        assertFailsWith<IllegalArgumentException> { original.subGrid(0, 0, 4, 1) }
        assertFailsWith<IllegalArgumentException> { original.subGrid(0, 0, 1, 4) }
    }
}