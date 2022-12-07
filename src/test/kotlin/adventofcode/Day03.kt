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

class Day03 {
    private fun Char.priority() = if (isUpperCase()) code - 'A'.code + 27 else code - 'a'.code + 1
    private fun commonChar(groups: List<String>): Char =
        groups.tail().fold(groups.head().toSet()) { set, str -> set.intersect(str.toSet()) }.first()

    private fun String.splitIntoCompartments() = chunked(length/2)
    @Test
    fun example1() {
        assertEquals(157, calculatePart1(readAsLines("day03-example.txt")))
    }

    @Test
    fun part1() {
        println(calculatePart1(readAsLines("day03.txt")))
    }

    @Test
    fun example2() {
        assertEquals(70, calculatePart2(readAsLines("day03-example.txt")))
    }

    @Test
    fun part2() {
        println(calculatePart2(readAsLines("day03.txt")))
    }

    private fun calculatePart2(rucksacks: List<String>) = rucksacks
        .chunked(3)
        .map { commonChar(it) }
        .sumOf { it.priority() }

    private fun calculatePart1(rucksacks: List<String>) = rucksacks
        .map { it.splitIntoCompartments() }
        .map { commonChar(it) }
        .sumOf { it.priority() }
}