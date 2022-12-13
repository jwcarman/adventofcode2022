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

import adventofcode.util.Table
import adventofcode.util.graph.Graphs.shortestPaths
import adventofcode.util.readAsString
import org.junit.Test
import kotlin.test.assertEquals

class Day12 {

    @Test
    fun example1() {
        assertEquals(31, calculatePart1(readAsString("day12-example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(497, calculatePart1(readAsString("day12.txt")))
    }

    @Test
    fun example2() {
        assertEquals(29, calculatePart2(readAsString("day12-example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(492, calculatePart2(readAsString("day12.txt")))
    }

    private fun calculatePart1(input: String): Int {
        val table = Table(input.lines().map { it.toCharArray().toList() })
        val start = table.cells().first { it.value() == 'S' }
        val end = table.cells().first { it.value() == 'E' }
        val shortestPaths = shortestPaths(
            table.cells().toSet(),
            { it.neighbors().filter { n -> checkNeighborFromStart(it, n) } },
            { _, _ -> 1 },
            start
        )
        return shortestPaths.distanceTo(end)
    }

    private fun calculatePart2(input: String): Int {
        val table = Table(input.lines().map { it.toCharArray().toList() })
        val end = table.cells().first { it.value() == 'E' }
        val shortestPaths = shortestPaths(
            table.cells().toSet(),
            { it.neighbors().filter { n -> checkNeighborFromEnd(it, n) } },
            { _, _ -> 1 },
            end
        )
        return table.cells()
            .filter { normalize(it.value()) == 'a' }
            .map { shortestPaths.distanceTo(it) }
            .filter { it > 0 }
            .min()
    }

    private fun normalize(height: Char): Char {
        if (height == 'E') {
            return 'z'
        }
        if (height == 'S') {
            return 'a'
        }
        return height
    }

    private fun checkNeighborFromStart(src: Table<Char>.Cell, neighbor: Table<Char>.Cell): Boolean {
        val srcHeight = normalize(src.value())
        val neighborHeight = normalize(neighbor.value())
        return neighborHeight <= srcHeight + 1
    }

    private fun checkNeighborFromEnd(src: Table<Char>.Cell, neighbor: Table<Char>.Cell): Boolean {
        val srcHeight = normalize(src.value())
        val neighborHeight = normalize(neighbor.value())
        return neighborHeight >= srcHeight - 1
    }
}