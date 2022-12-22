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

package adventofcode.day10

import adventofcode.util.readAsString
import org.junit.jupiter.api.Test
import kotlin.math.abs
import kotlin.test.assertEquals

class Day10 {

    private val example2Output = """
        ##..##..##..##..##..##..##..##..##..##..
        ###...###...###...###...###...###...###.
        ####....####....####....####....####....
        #####.....#####.....#####.....#####.....
        ######......######......######......####
        #######.......#######.......#######.....
    """.trimIndent()

    private val part2Output = """
        ###..####.####.#..#.####.####.#..#..##..
        #..#....#.#....#.#..#....#....#..#.#..#.
        #..#...#..###..##...###..###..####.#..#.
        ###...#...#....#.#..#....#....#..#.####.
        #.#..#....#....#.#..#....#....#..#.#..#.
        #..#.####.####.#..#.####.#....#..#.#..#.
    """.trimIndent()

    @Test
    fun example1() {
        assertEquals(13140, calculatePart1(readAsString("day10-example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(14820, calculatePart1(readAsString("day10.txt")))
    }

    @Test
    fun example2() {
        assertEquals(example2Output, calculatePart2(readAsString("day10-example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(part2Output, calculatePart2(readAsString("day10.txt")))
    }

    private fun calculatePart1(input: String): Int {
        return registerValues(input.lines())
            .mapIndexed { index, value -> (index + 1) * value }
            .filterIndexed { index, _ -> (index - 19) % 40 == 0 }
            .take(6)
            .sum()
    }

    private fun calculatePart2(input: String): String {
        return registerValues(input.lines())
            .foldIndexed("") { index, acc, value ->
                if (abs(index % 40 - value) < 2) {
                    "$acc#"
                } else {
                    "$acc."
                }
            }
            .chunked(40)
            .joinToString(separator = "\n")
    }

    private fun registerValues(program: List<String>) = sequence {
        var value = 1
        program.forEach { instruction ->
            if (instruction == "noop") {
                yield(value)
            } else {
                yield(value)
                yield(value)
                value += instruction.substringAfter(' ').toInt()
            }
        }
    }


}