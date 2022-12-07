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

class Day01 {

    @Test
    fun example1() {
        assertEquals(24000, calculatePart1(readAsString("day01-example.txt")))
    }

    @Test
    fun part1() {
        println(calculatePart1(readAsString("day01.txt")))
    }

    @Test
    fun example2() {
        assertEquals(45000, calculatePart2(readAsString("day01-example.txt")))
    }

    @Test
    fun part2() {
        println(calculatePart2(readAsString("day01.txt")))
    }


    private fun calculatePart1(input: String): Int = readCalorieCounts(input).first()
    private fun calculatePart2(input: String): Int = readCalorieCounts(input).take(3).sum()


    fun readCalorieCounts(input: String): List<Int> = input
        .split("\n\n")
        .map { chunk -> chunk.split("\n").sumOf { it.toInt() } }
        .sortedDescending()

}