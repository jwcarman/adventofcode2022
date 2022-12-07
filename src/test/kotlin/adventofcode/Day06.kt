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

class Day06 {

    @Test
    fun example1() {
        assertEquals(7, calculatePart1(Input.readAsString("day06-example.txt")))
    }

    @Test
    fun part1() {
        println(calculatePart1(Input.readAsString("day06.txt")))
    }

    @Test
    fun example2() {
        assertEquals(19, calculatePart2(Input.readAsString("day06-example.txt")))
    }

    @Test
    fun part2() {
        println(calculatePart2(Input.readAsString("day06.txt")))
    }

    private fun String.uniqueCharCount(): Int {
        val set = mutableSetOf<Char>()
        set.addAll(toCharArray().asList())
        return set.size
    }

    private fun calculatePart1(input: String): Int {
        return findFirstUniqueCharacterWindow(input, 4)
    }

    private fun calculatePart2(input:String): Int {
        return findFirstUniqueCharacterWindow(input, 14)
    }

    private fun findFirstUniqueCharacterWindow(input: String, windowSize: Int): Int {
        return input.windowed(windowSize)
            .indexOfFirst { it.uniqueCharCount() == windowSize } + windowSize
    }
}