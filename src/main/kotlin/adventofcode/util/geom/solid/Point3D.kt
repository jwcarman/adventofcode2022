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

package adventofcode.util.geom.solid

import kotlin.math.abs

data class Point3D(val x: Int, val y: Int, val z: Int) : Comparable<Point3D> {
    fun neighbors() = listOf(
        Point3D(x - 1, y, z),
        Point3D(x + 1, y, z),
        Point3D(x, y - 1, z),
        Point3D(x, y + 1, z),
        Point3D(x, y, z - 1),
        Point3D(x, y, z + 1)
    )

    override fun compareTo(other: Point3D) = listOf(z - other.z, y - other.y, x - other.x).firstOrNull { it == 0 } ?: 0

    fun isAdjacent(other: Point3D) = manhattanDistance(other) == 1

    fun translate(dx:Int, dy:Int, dz:Int) = Point3D(x + dx, y + dy, z + dz)

    fun manhattanDistance(other: Point3D) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
}