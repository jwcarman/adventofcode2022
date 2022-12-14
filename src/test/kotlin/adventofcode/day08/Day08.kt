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

package adventofcode.day08

import adventofcode.util.readAsLines
import adventofcode.util.table.Table
import adventofcode.util.takeWhileInclusive
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day08 {
    @Test
    fun example1() {
        assertEquals(21, calculatePart1(readAsLines("day08-example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(1803, calculatePart1(readAsLines("day08.txt")))
    }

    @Test
    fun example2() {
        assertEquals(8, calculatePart2(readAsLines("day08-example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(268912, calculatePart2(readAsLines("day08.txt")))
    }

    private fun calculatePart1(input: List<String>): Int {
        val forrest = Table(input.map { it.toList().map { c -> c - '0' } })
        return forrest.cells().filter { it.isVisible() }.count()
    }

    private fun calculatePart2(input: List<String>): Int {
        val forrest = Table(input.map { it.toList().map { c -> c - '0' } })
        return forrest.cells().maxOf { it.scenicScore() }
    }


    private fun Table<Int>.Cell.scenicScore(): Int {
        val maxHeight = value()

        fun List<Table<Int>.Cell>.viewingDistance() = takeWhileInclusive { it.value() < maxHeight }.count()

        return westOf().viewingDistance() *
                eastOf().viewingDistance() *
                northOf().viewingDistance() *
                southOf().viewingDistance()
    }


    private fun Table<Int>.Cell.isVisible(): Boolean {
        val height = value()
        return westOf().all { it.value() < height } ||
                eastOf().all { it.value() < height } ||
                northOf().all { it.value() < height } ||
                southOf().all { it.value() < height }
    }
}



