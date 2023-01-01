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

package adventofcode.util.geom.plane

import kotlin.math.abs

data class Point2D(val x: Int, val y: Int) : Comparable<Point2D> {

    override fun compareTo(other: Point2D) = listOf(y - other.y, x - other.x).firstOrNull { it != 0 } ?: 0

    fun neighbors() = listOf(
        Point2D(x - 1, y),
        Point2D(x + 1, y),
        Point2D(x, y - 1),
        Point2D(x, y + 1)
    )


    fun manhattanDistance(other: Point2D) = abs(x - other.x) + abs(y - other.y)

    fun isAdjacent(other: Point2D) = manhattanDistance(other) == 1

    override fun toString(): String {
        return "($x,$y)"
    }

    fun translate(dx: Int, dy: Int) = Point2D(x + dx, y + dy)

    companion object {
        fun origin() = Point2D(0, 0)
    }
}