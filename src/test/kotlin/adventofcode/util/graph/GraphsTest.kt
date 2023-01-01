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

package adventofcode.util.graph

import adventofcode.util.geom.plane.Point2D
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GraphsTest {
    @Test
    fun reachableWithZeroMaxDepthReturnsOriginal() {
        assertThat(Graphs.reachable(Point2D.origin(), 0, Point2D::neighbors)).containsExactly(Point2D.origin())
    }

    @Test
    fun reachableStopsAtMaxDepth() {
        assertThat(Graphs.reachable(Point2D.origin(), 1, Point2D::neighbors)).hasSize(5)
    }

    @Test
    fun reachableStopsWhenAllVisited() {
        val searchSpace = setOf(
            Point2D.origin(),
            Point2D(1, 0),
            Point2D(0, 1),
            Point2D(-1, 0),
            Point2D(0, -1)
        )

        assertThat(Graphs.reachable(Point2D.origin()) {it.neighbors().filter { it in searchSpace }}).hasSize(5)
    }
}