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

import adventofcode.day22.EdgeType.RIGHT
import adventofcode.util.grid.TextGrid
import adventofcode.util.grid.nullView
import adventofcode.util.readAsString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day22 {
    @Test
    fun example1() {
        assertThat(calculatePart1(readAsString("day22-example.txt"))).isEqualTo(6032)
    }

    @Test
    fun part1() {
        assertThat(calculatePart1(readAsString("day22.txt"))).isEqualTo(26558)
    }

    @Test
    fun example2() {
        assertThat(calculatePart2(readAsString("day22-example.txt"), 4)).isEqualTo(5031)
    }

    @Test
    fun part2() {
        assertThat(calculatePart2(readAsString("day22.txt"), 50)).isEqualTo(110400)
    }

    fun calculatePart1(input: String): Int {
        val splits = input.split("\n\n")
        val grid = TextGrid(splits[0].lines())
        val instructions = parseInstructions(splits[1])
        val start = grid.coordinates().first { grid[it] == '.' }
        val rule = FlatWrappingRule()
        val finish = followInstructions(Pose(Face(grid.nullView(), FaceType.FRONT), start, RIGHT), instructions, rule)
        return finish.calculatePassword()

    }

    fun calculatePart2(input: String, sideLength: Int): Int {
        val splits = input.split("\n\n")
        val map = TextGrid(splits[0].lines())
        val instructions = parseInstructions(splits[1])

        val rule = CubeWrappingRule(map, sideLength)
        val frontFace = rule[FaceType.FRONT]
        val (startPosition, _) = frontFace.grid.coordinatesWithValues().first { it.second == '.' }
        val finish = followInstructions(Pose(frontFace, startPosition, RIGHT), instructions, rule)
        return finish.calculatePassword()
    }

    private fun followInstructions(start: Pose, instructions: List<String>, rule: WrappingRule): Pose {
        return instructions.fold(start) { pose, instruction -> pose.followInstruction(instruction, rule) }
    }

    private fun parseInstructions(path: String): List<String> {
        return path.replace("R", " R ")
            .replace("L", " L ")
            .split(' ')
    }
}