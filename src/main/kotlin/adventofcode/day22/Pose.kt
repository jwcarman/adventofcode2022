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

package adventofcode.day22

import adventofcode.util.geom.Point2D
import adventofcode.util.grid.Grid

class Pose(val position: Point2D, val facing: Facing) {
    fun password(): Long {
        return 1000L * (position.y + 1) + 4 * (position.x + 1) + facing.value()
    }

    fun walk(grid: Grid<Char>) = sequence {
        var curr = position
        while (true) {
            yield(curr)
            curr = Point2D((curr.x + facing.dx()).mod(grid.width()), (curr.y + facing.dy()).mod(grid.height()))
        }
    }

    override fun toString(): String {
        return "$position - ${facing.name}"
    }
}