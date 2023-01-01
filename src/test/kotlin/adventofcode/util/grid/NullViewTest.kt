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

class NullViewTest {
    @Test
    fun nullViewShouldReturnOriginalValues() {
        val grid = ListGrid(2, 1, 2, 3, 4, 5, 6).nullView()
        assertThat(grid.width()).isEqualTo(2)
        assertThat(grid.height()).isEqualTo(3)
        assertThat(grid.values().toList()).isEqualTo(listOf(1, 2, 3, 4, 5, 6))
        assertThat(grid.size()).isEqualTo(6)
        assertThat(grid.underlyingPoint(1, 2)).isEqualTo(Point2D(1, 2))
    }
}