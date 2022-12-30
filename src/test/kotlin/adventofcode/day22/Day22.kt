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

import adventofcode.util.grid.ListGrid
import adventofcode.util.grid.TextGrid
import adventofcode.util.readAsString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day22 {
    @Test
    fun example1() {
        assertThat(calculatePart1(readAsString("day22-example.txt"))).isEqualTo(6032L)
    }

    @Test
    fun part1() {
        assertThat(calculatePart1(readAsString("day22.txt"))).isEqualTo(26558L)
    }

    @Test
    fun example2() {
        assertThat(calculatePart2(readAsString("day22-example.txt"), 4)).isEqualTo(5031L)
    }

    @Test
    fun part2() {
        assertThat(calculatePart2(readAsString("day22.txt"), 50)).isEqualTo(0)
    }

    fun calculatePart1(input: String): Long {
        val splits = input.split("\n\n")
        val map = splits[0]
        val path = splits[1]
        val monkeyMap = MonkeyMap(map)
        return monkeyMap.decodePassword(path)
    }

    fun calculatePart2(input: String, sideLength: Int): Int {
        val splits = input.split("\n\n")
        val map = splits[0]
        val grid = TextGrid(map.lines())
        val occupied = generateOccupiedGrid(grid, sideLength)

        println(occupied)


        return 0
    }

    private fun generateOccupiedGrid(
        grid: TextGrid,
        sideLength: Int
    ): ListGrid<Boolean> {
        val xGrids = grid.width() / sideLength
        val yGrids = grid.height() / sideLength

        val occupied = ListGrid(
            xGrids,
            (0 until yGrids).map { y -> (0 until xGrids).map { x -> grid[x * sideLength, y * sideLength] != ' ' } }
                .flatten().toList()
        )
        return occupied
    }
}