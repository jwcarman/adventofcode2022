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

package adventofcode.day12

import adventofcode.util.graph.Graph
import adventofcode.util.graph.SparseGraph
import adventofcode.util.readAsString
import adventofcode.util.table.Table
import org.junit.jupiter.api.Test
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

    private fun buildGraph(input: String, isEdge: (Table<Char>.Cell, Table<Char>.Cell) -> Boolean): Graph<Table<Char>.Cell, Unit> {
        val table = Table(input.lines().map { it.toCharArray().toList() })
        val graph = SparseGraph<Table<Char>.Cell, Unit>()
        table.cells().forEach { graph.addVertex(it) }
        table.cells().forEach { from ->
            from.neighbors()
                .filter { to -> isEdge(from, to) }
                .forEach { graph.addEdge(from, it, Unit, 1.0) }
        }
        return graph
    }

    private fun calculatePart1(input: String): Int {
        val graph = buildGraph(input) { from, to -> checkNeighborFromStart(from, to) }
        val start = graph.vertices().first { it.value() == 'S' }
        val end = graph.vertices().first { it.value() == 'E' }
        val shortestPaths = graph.shortestPaths(start)
        return shortestPaths.pathTo(end).size
    }

    private fun calculatePart2(input: String): Int {
        val graph = buildGraph(input) { from, to -> checkNeighborFromEnd(from, to) }
        val end = graph.vertices().first { it.value() == 'E' }
        val shortestPaths = graph.shortestPaths(end)
        val closest = graph.vertices()
            .filter { normalize(it.value()) == 'a' }
            .minBy { shortestPaths.distanceTo(it) }
        return shortestPaths.pathTo(closest).size
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