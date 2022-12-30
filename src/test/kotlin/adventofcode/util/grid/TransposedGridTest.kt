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

class TransposedGridTest {

    @Test
    fun transposeReturnsTransposedGrid() {
        val grid = TextGrid(listOf("12", "34"))
        val transpose = grid.transpose()
        assertThat(transpose[0,0]).isEqualTo('1')
        assertThat(transpose[0,1]).isEqualTo('2')
        assertThat(transpose[1,0]).isEqualTo('3')
        assertThat(transpose[1,1]).isEqualTo('4')

        assertThat(transpose.height()).isEqualTo(grid.width())
        assertThat(transpose.width()).isEqualTo(grid.height())

        assertThat(transpose.rowAt(0).toList()).isEqualTo(grid.columnAt(0).toList())
        assertThat(transpose.rowAt(1).toList()).isEqualTo(grid.columnAt(1).toList())

        assertThat(transpose.columnAt(0).toList()).isEqualTo(grid.rowAt(0).toList())
        assertThat(transpose.columnAt(1).toList()).isEqualTo(grid.rowAt(1).toList())
    }
}