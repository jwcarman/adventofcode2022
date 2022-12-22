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

package adventofcode.day14

import adventofcode.util.geom.Point2D
import adventofcode.util.readAsString
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14 {
    @Test
    fun example1() {
        assertEquals(24, calculatePart1(readAsString("day14-example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(614, calculatePart1(readAsString("day14.txt")))
    }

    @Test
    fun example2() {
        assertEquals(93, calculatePart2(readAsString("day14-example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(26170, calculatePart2(readAsString("day14.txt")))
    }

    private val dropPoint = Point2D(500, 0)

    private fun calculatePart1(input: String): Int {
        val reservoir = parseReservoir(input)
        var count = 0
        while (reservoir.dropSand(dropPoint)) {
            count++
        }
        return count
    }

    private fun calculatePart2(input: String): Int {
        val reservoir = parseReservoir(input)
        reservoir.addFloor()
        var count = 0
        while (!reservoir.isBlocked(dropPoint)) {
            reservoir.dropSand(dropPoint)
            count++
        }
        return count
    }

    private fun parseReservoir(input: String): Reservoir {
        val reservoir = Reservoir()
        input.lines()
            .map { line ->
                line.split(" -> ")
                    .map { Point2D(it.substringBefore(',').toInt(), it.substringAfter(',').toInt()) }
                    .toList()
            }
            .forEach { reservoir.addRockStructure(it) }
        return reservoir
    }
}