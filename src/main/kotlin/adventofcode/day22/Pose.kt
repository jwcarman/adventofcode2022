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

package adventofcode.day22

import adventofcode.util.geom.plane.Point2D

private const val SOLID_WALL = '#'

private const val OPEN_SPACE = ' '

class Pose(val face: Face, val position: Point2D, val facing: EdgeType) {

    fun calculatePassword(): Int {
        val (x, y) = face.grid.underlyingPoint(position.x, position.y)
        return 1000 * (y + 1) + 4 * (x + 1) + facing.value()
    }

    fun followInstruction(instruction: String, rule: WrappingRule): Pose {
        return when (instruction) {
            "R" -> turnRight()
            "L" -> turnLeft()
            else -> goForward(instruction.toInt(), rule)
        }
    }

    private fun next(rule: WrappingRule): Pose {
        val next = Pose(face, position + facing, facing)
        if (next.position !in face.grid) {
            return rule.wrap(this)
        }
        return next
    }

    private fun goForward(nTiles: Int, rule: WrappingRule): Pose {
        val steps = generateSequence(this) { p -> p.next(rule) }
        return steps
            .filter { it.value() != OPEN_SPACE }
            .take(nTiles + 1)
            .takeWhile { it.value() != SOLID_WALL }
            .last()

    }

    private fun turnRight() = Pose(face, position, facing.turnRight())
    private fun turnLeft() = Pose(face, position, facing.turnLeft())
    private fun value() = face.grid[position]

    override fun toString(): String {
        return "$position - ${facing.name}"
    }
}