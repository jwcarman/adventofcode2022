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

fun <T : Any> List<T>.toGrid(width: Int) = ListGrid(width, this)

class ListGrid<T : Any>(private val width: Int, values: List<T>) : AbstractGrid<T>() {
    private val values: List<T>
    private val height: Int

    init {
        if (values.size % width != 0) {
            throw IllegalArgumentException("Values length is not a multiple of provided width $width.")
        }
        this.values = values
        this.height = values.size / width
    }

    constructor(width: Int, vararg values: T) : this(width, values.toList())

    override fun getImpl(x: Int, y: Int) = values[gridIndex(x, y)]
    override fun width() = width
    override fun height() = height
    private fun gridIndex(x: Int, y: Int) = width * y + x
    override fun toString() = values.chunked(width).joinToString("\n") { it.toString() }

}