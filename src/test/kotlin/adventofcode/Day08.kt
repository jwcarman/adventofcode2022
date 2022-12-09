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

import adventofcode.util.Coordinate
import adventofcode.util.Table
import org.junit.Test
import kotlin.test.assertEquals

typealias Forrest = Table<Int>

class Day08 {
    @Test
    fun example1() {
        assertEquals(21, calculatePart1(readAsLines("day08-example.txt")))
    }

    @Test
    fun part1() {
        println(calculatePart1(readAsLines("day08.txt")))
    }

    @Test
    fun example2() {
        assertEquals(8, calculatePart2(readAsLines("day08-example.txt")))
    }

    @Test
    fun part2() {
        println(calculatePart2(readAsLines("day08.txt")))
    }

    private fun calculatePart1(input: List<String>): Int {
        val forrest = Forrest(input.map { it.toList().map { c -> c - '0' } })
        return forrest.coords().filter { forrest.isVisible(it) }.count()
    }

    private fun calculatePart2(input: List<String>): Int {
        val forrest = Forrest(input.map { it.toList().map { c -> c - '0' } })
        return forrest.coords().maxOf { forrest.scenicScoreOf(it) }
    }

    private fun Forrest.viewingDistance(maxHeight: Int, coordinates: List<Coordinate>): Int {
        val visible = coordinates.takeWhile { valueAt(it) < maxHeight }
        if (visible.isEmpty()) {
            return 1
        }
        if (isEdge(visible.last())) {
            return visible.size
        }
        return visible.size + 1
    }

    private fun Forrest.scenicScoreOf(c: Coordinate): Int {
        if (isEdge(c)) {
            return 0
        }
        val height = valueAt(c)
        return viewingDistance(height, westOf(c)) *
                viewingDistance(height, eastOf(c)) *
                viewingDistance(height, northOf(c)) *
                viewingDistance(height, southOf(c))
    }

    private fun Forrest.isVisible(c: Coordinate): Boolean {
        if (isEdge(c)) {
            return true
        }
        val height = valueAt(c)
        return westOf(c).all { valueAt(it) < height } ||
                eastOf(c).all { valueAt(it) < height } ||
                northOf(c).all { valueAt(it) < height } ||
                southOf(c).all { valueAt(it) < height }
    }
}



