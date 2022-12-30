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

class RotatedGridTest {
    @Test
    fun rotateRightShouldSwapHeightAndWidth() {
        val grid = TextGrid(listOf("123", "456"))
        val rotated = grid.rotateRight()
        assertThat(rotated.width()).isEqualTo(grid.height())
        assertThat(rotated.height()).isEqualTo(grid.width())
    }

    @Test
    fun rotateRightShouldReturnCorrectValues() {
        val grid = TextGrid(listOf("123", "456"))
        val rotated = grid.rotateRight()
        assertThat(rotated[0,0]).isEqualTo('4')
        assertThat(rotated[1,0]).isEqualTo('1')
        assertThat(rotated[0,1]).isEqualTo('5')
        assertThat(rotated[1,1]).isEqualTo('2')
        assertThat(rotated[0,2]).isEqualTo('6')
        assertThat(rotated[1,2]).isEqualTo('3')
    }

    @Test
    fun rotateLeftShouldSwapHeightAndWidth() {
        val grid = TextGrid(listOf("123", "456"))
        val rotated = grid.rotateLeft()
        assertThat(rotated.width()).isEqualTo(grid.height())
        assertThat(rotated.height()).isEqualTo(grid.width())
    }

    @Test
    fun rotateLeftShouldReturnCorrectValues() {
        val grid = TextGrid(listOf("123", "456"))
        val rotated = grid.rotateLeft()
        assertThat(rotated[0,0]).isEqualTo('3')
        assertThat(rotated[1,0]).isEqualTo('6')
        assertThat(rotated[0,1]).isEqualTo('2')
        assertThat(rotated[1,1]).isEqualTo('5')
        assertThat(rotated[0,2]).isEqualTo('1')
        assertThat(rotated[1,2]).isEqualTo('4')
    }

}