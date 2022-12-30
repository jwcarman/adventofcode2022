/*
 * Copyright (c) 2022 James Carman
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

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class ArrayGridTest {
    @Test
    fun constructorShouldThrowIllegalArgumentWhenJaggedArray() {
        assertFailsWith<IllegalArgumentException> { ArrayGrid(arrayOf(arrayOf(1, 2, 3), arrayOf(4, 5))) }
    }

    @Test
    fun constructorShouldSucceedWithRectangularValues() {
        val grid = ArrayGrid(arrayOf(arrayOf(1, 2, 3), arrayOf(4, 5, 6)))
        assertThat(grid.values().toList()).isEqualTo(listOf(1, 2, 3, 4, 5, 6))
    }

    @Test
    fun rowAtShouldReturnCorrectValues() {
        val grid = arrayOf(arrayOf(1, 2, 3), arrayOf(4, 5, 6)).toGrid()
        assertThat(grid.rowAt(0).toList()).isEqualTo(listOf(1, 2, 3))
        assertThat(grid.rowAt(1).toList()).isEqualTo(listOf(4, 5, 6))
    }
}