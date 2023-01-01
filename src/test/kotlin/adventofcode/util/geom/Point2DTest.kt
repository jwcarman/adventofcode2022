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

package adventofcode.util.geom

import adventofcode.util.geom.plane.Point2D
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Point2DTest {
    @Test
    fun translateShouldReturnSameWhenZeros() {
        val point = Point2D(4, 4)
        assertEquals(point, point.translate(0, 0))
    }

    @Test
    fun translateShouldReturnTranslatedValue() {
        val point = Point2D(4, 4)
        assertEquals(Point2D(5, 5), point.translate(1, 1))
        assertEquals(Point2D(5, 4), point.translate(1, 0))
        assertEquals(Point2D(4, 5), point.translate(0, 1))
    }

    @Test
    fun toStringShouldReturnStringRepresentation() {
        assertEquals("(4,4)", Point2D(4, 4).toString())
        assertEquals("(-4,4)", Point2D(-4, 4).toString())
    }

    @Test
    fun manhattanDistanceToSelfIsZero() {
        val point = Point2D(5, 5)
        assertThat(point.manhattanDistance(point)).isZero
    }

    @Test
    fun manhattanDistanceIsPositiveWithNegativeDiffs() {
        val point = Point2D(5, 5)
        assertThat(point.manhattanDistance(Point2D(3, 3))).isEqualTo(4)
    }

    @Test
    fun manhattanDistanceMatchesLinearDistanceAlongX() {
        val point = Point2D(5, 5)
        assertThat(point.manhattanDistance(Point2D(3, 5))).isEqualTo(2)
    }

    @Test
    fun manhattanDistanceMatchesLinearDistanceAlongY() {
        val point = Point2D(5, 5)
        assertThat(point.manhattanDistance(Point2D(5, 3))).isEqualTo(2)
    }

    @Test
    fun neighborsReturnsAllNeighbors() {
        val point = Point2D(0, 0)
        assertThat(point.neighbors()).containsExactlyInAnyOrder(
            Point2D(0, 1),
            Point2D(0, -1),
            Point2D(1, 0),
            Point2D(-1, 0)

        )
    }

    @Test
    fun comparisonsCheck() {
        assertThat(Point2D(0,0)).isLessThan(Point2D(1,0))
        assertThat(Point2D(0,0)).isLessThan(Point2D(0,1))
        assertThat(Point2D(0,0).compareTo(Point2D(0,0))).isZero
    }
}