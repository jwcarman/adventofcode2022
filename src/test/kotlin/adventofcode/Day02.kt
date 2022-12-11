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
import org.junit.Test
import kotlin.test.assertEquals

const val WIN = 6
const val LOSE = 0
const val DRAW = 3

const val ROCK = 1
const val PAPER = 2
const val SCISSORS = 3

class Day02 {

    private val part1Scores = mapOf(
        "A X" to ROCK + DRAW,
        "A Y" to PAPER + WIN,
        "A Z" to SCISSORS + LOSE,
        "B X" to ROCK + LOSE,
        "B Y" to PAPER + DRAW,
        "B Z" to SCISSORS + WIN,
        "C X" to ROCK + WIN,
        "C Y" to PAPER + LOSE,
        "C Z" to SCISSORS + DRAW
    )

    private val part2Scores = mapOf(
        "A X" to LOSE + SCISSORS,
        "A Y" to DRAW + ROCK,
        "A Z" to WIN + PAPER,
        "B X" to LOSE + ROCK,
        "B Y" to DRAW + PAPER,
        "B Z" to WIN + SCISSORS,
        "C X" to LOSE + PAPER,
        "C Y" to DRAW + SCISSORS,
        "C Z" to WIN + ROCK
    )

    @Test
    fun example1() {
        assertEquals(15, calculatePart1(readAsLines("day02-example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(11873, calculatePart1(readAsLines("day02.txt")))
    }

    @Test
    fun example2() {
        assertEquals(12, calculatePart2(readAsLines("day02-example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(12014, calculatePart2(readAsLines("day02.txt")))
    }

    private fun calculatePart1(lines: List<String>) = lines.sumOf { part1Scores.getOrDefault(it, 0) }
    private fun calculatePart2(lines: List<String>) = lines.sumOf { part2Scores.getOrDefault(it, 0) }
}