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

import adventofcode.util.readAsLines
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day04 {

    private fun IntRange.fullyContains(other: IntRange) = first <= other.first && last >= other.last

    private fun IntRange.overlaps(other: IntRange) = last >= other.first && first <= other.last

    @Test
    fun example1() {
        val count = calculatePart1(readAsLines("day04-example.txt"))
        assertEquals(2, count)
    }

    @Test
    fun part1() {
        assertEquals(536, calculatePart1(readAsLines("day04.txt")))
    }

    @Test
    fun example2() {
        val count = calculatePart2(readAsLines("day04-example.txt"))
        assertEquals(4, count)
    }

    @Test
    fun part2() {
        assertEquals(845, calculatePart2(readAsLines("day04.txt")))
    }

    private fun calculatePart1(lines: List<String>): Int =
        lines
            .map { parseRangePair(it) }
            .count { pair -> pair.first.fullyContains(pair.second) || pair.second.fullyContains(pair.first) }

    private fun calculatePart2(lines: List<String>): Int =
        lines
            .map { parseRangePair(it) }
            .count { pair -> pair.first.overlaps(pair.second) }

    private fun parseRangePair(line: String): Pair<IntRange, IntRange> {
        val (first1, second1, first2, second2) = Regex("(\\d+)-(\\d+),(\\d+)-(\\d+)")
            .matchEntire(line)!!.destructured
        return Pair(first1.toInt()..second1.toInt(), first2.toInt()..second2.toInt())
    }
}