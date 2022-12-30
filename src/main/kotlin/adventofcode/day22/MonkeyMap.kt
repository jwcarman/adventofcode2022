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
import adventofcode.util.grid.TextGrid

private const val SPACE = ' '

private const val OPEN_TILE = '.'

private const val SOLID_WALL = '#'

class MonkeyMap(input: String) {

    private val grid: Grid<Char>

    init {
        this.grid = TextGrid(input.lines())
        println("Parsed a ${grid.width()}x${grid.height()} map")
        println(grid)
    }

    private fun followInstruction(pose: Pose, instruction: String): Pose {

        val finish = when (instruction) {
            "R" -> Pose(pose.position, pose.facing.turnRight())
            "L" -> Pose(pose.position, pose.facing.turnLeft())
            else -> {
                val dest = pose.walk(grid)
                    .filter { grid[it] != SPACE }
                    .take(instruction.toInt() + 1)
                    .takeWhile { grid[it] != SOLID_WALL }
                    .last()
                Pose(dest, pose.facing)
            }
        }
        return finish
    }

    fun decodePassword(path: String): Long {
        val x = grid.rowAt(0).indexOf(OPEN_TILE)
        val instructions = parseInstructions(path)
        val finish = instructions.fold(Pose(Point2D(x, 0), Facing.RIGHT), ::followInstruction)
        return finish.password()
    }

    private fun parseInstructions(path: String): List<String> {
        return path.replace("R", " R ")
            .replace("L", " L ")
            .split(SPACE)
    }
}