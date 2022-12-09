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

package adventofcode.util

data class Coordinate(val x: Int, val y: Int)
class Table<T>(private val values: List<List<T>>) {
    fun isEdge(c: Coordinate): Boolean = c.x == 0 || c.y == 0 || c.x == width() - 1 || c.y == height() - 1

    fun coords() = sequence<Coordinate> {
        for (y in 0 until height()) {
            for (x in 0 until width()) {
                yield(Coordinate(x, y))
            }
        }
    }

    fun width() = values[0].size

    fun height() = values.size

    fun valueAt(c: Coordinate) = values[c.y][c.x]
    fun westOf(c: Coordinate) = (c.x - 1 downTo 0).map { Coordinate(it, c.y) }
    fun eastOf(c: Coordinate) = ((c.x + 1) until width()).map { Coordinate(it, c.y) }
    fun northOf(c: Coordinate) = (c.y - 1 downTo 0).map { Coordinate(c.x, it) }
    fun southOf(c: Coordinate) = (c.y + 1 until height()).map { Coordinate(c.x, it) }
}