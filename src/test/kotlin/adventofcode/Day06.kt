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

import adventofcode.util.readAsString
import org.junit.Test
import kotlin.test.assertEquals

class Day06 {

    private fun String.findUniqueChars(windowSize: Int): Int =
        windowed(windowSize).indexOfFirst { it.toSet().size == windowSize } + windowSize

    @Test
    fun example1() {
        assertEquals(7, calculatePart1(readAsString("day06-example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(1647, calculatePart1(readAsString("day06.txt")))
    }

    @Test
    fun example2() {
        assertEquals(19, calculatePart2(readAsString("day06-example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(2447, calculatePart2(readAsString("day06.txt")))
    }

    private fun calculatePart1(input: String): Int = input.findUniqueChars(4)
    private fun calculatePart2(input: String): Int = input.findUniqueChars(14)
}