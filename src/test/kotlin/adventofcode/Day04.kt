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

package adventofcode

import org.junit.Test
import kotlin.test.assertEquals

class Day04 {

    private fun IntRange.fullyContains(other: IntRange) = first <= other.first && last >= other.last

    private fun IntRange.overlaps(other: IntRange) = last >= other.first && first <= other.last

    @Test
    fun part1() {
        println(calculatePart1(Input.readAsLines("day04.txt")))
    }

    @Test
    fun part2() {
        println(calculatePart2(Input.readAsLines("day04.txt")))
    }

    @Test
    fun example1() {
        val count = calculatePart1(Input.readAsLines("day04-example.txt"))
        assertEquals(2, count)
    }

    @Test
    fun example2() {
        val count = calculatePart2(Input.readAsLines("day04-example.txt"))
        assertEquals(4, count)
    }

    private fun calculatePart1(lines: List<String>): Int =
        lines
            .map { parseRangePairs(it) }
            .count { pair -> pair.first.fullyContains(pair.second) || pair.second.fullyContains(pair.first) }

    private fun calculatePart2(lines: List<String>): Int =
        lines
            .map { parseRangePairs(it) }
            .count { pair -> pair.first.overlaps(pair.second) }

    private fun parseRangePairs(line: String): Pair<IntRange, IntRange> {
        val splits = line.split(",")
        return Pair(parseRange(splits[0]), parseRange(splits[1]))
    }

    private fun parseRange(str: String): IntRange {
        val bounds = str.split("-")
            .map { it.toInt() }
        return IntRange(bounds[0], bounds[1])
    }
}