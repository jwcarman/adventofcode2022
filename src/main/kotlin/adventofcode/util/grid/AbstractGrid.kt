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

abstract class AbstractGrid<T : Any> : Grid<T> {
    final override fun get(x: Int, y: Int): T {
        val xBounds = xRange()
        if (x !in xBounds) {
            throw IllegalArgumentException("Values for x must be in $xBounds.")
        }
        val yBounds = yRange()
        if (y !in yBounds) {
            throw IllegalArgumentException("Values for y must be in $yBounds.")
        }
        return getImpl(x, y)
    }

    protected abstract fun getImpl(x: Int, y: Int): T
}