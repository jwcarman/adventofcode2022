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


class Table<T>(private val values: List<List<T>>) {
    inner class Cell(private val x: Int, private val y: Int) {
        fun isEdge() = x == 0 || y == 0 || x == width() - 1 || y == height() - 1

        fun value() = values[y][x]

        fun westOf() = (x - 1 downTo 0).map { Cell(it, y) }
        fun eastOf() = ((x + 1) until width()).map { Cell(it, y) }
        fun northOf() = (y - 1 downTo 0).map { Cell(x, it) }
        fun southOf() = (y + 1 until height()).map { Cell(x, it) }
    }

    fun cells() = sequence {
        for (y in 0 until height()) {
            for (x in 0 until width()) {
                yield(Cell(x, y))
            }
        }
    }

    fun width() = values[0].size

    fun height() = values.size
}