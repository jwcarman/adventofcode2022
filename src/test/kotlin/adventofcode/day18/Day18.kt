/*
 * Copyright (c) 2023 James Carman
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

package adventofcode.day18

import adventofcode.util.geom.solid.Point3D
import adventofcode.util.graph.Graphs
import adventofcode.util.readAsString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day18 {
    @Test
    fun example1() {
        assertThat(calculatePart1(readAsString("day18-example.txt"))).isEqualTo(64)
    }

    @Test
    fun part1() {
        assertThat(calculatePart1(readAsString("day18.txt"))).isEqualTo(3390)
    }

    @Test
    fun example2() {
        assertThat(calculatePart2(readAsString("day18-example.txt"))).isEqualTo(58)
    }

    @Test
    fun part2() {
        assertThat(calculatePart2(readAsString("day18.txt"))).isEqualTo(2058)
    }

    fun calculatePart1(input: String): Int {
        val points = parsePoints(input)

        return points.sumOf { it.neighbors().filter { n -> n !in points }.size }
    }

    private fun parsePoints(input: String) = input.lines()
        .map { it.split(',') }
        .map { Point3D(it[0].toInt(), it[1].toInt(), it[2].toInt()) }

    fun calculatePart2(input: String): Int {
        val points = parsePoints(input)

        val xBounds = (points.minOf { it.x } - 1)..(points.maxOf { it.x } + 1)
        val yBounds = (points.minOf { it.y } - 1)..(points.maxOf { it.y } + 1)
        val zBounds = (points.minOf { it.z } - 1)..(points.maxOf { it.z } + 1)

        val air = mutableSetOf<Point3D>()
        xBounds.forEach { x ->
            yBounds.forEach { y ->
                zBounds.forEach { z ->
                    val point = Point3D(x, y, z)
                    if (point !in points) {
                        air.add(point)
                    }
                }
            }
        }
        val validNeighbors = cluster(air).filter { cluster ->
            cluster.any {
                it.x == xBounds.first ||
                        it.x == xBounds.last ||
                        it.y == yBounds.first ||
                        it.y == yBounds.last ||
                        it.z == zBounds.first ||
                        it.z == zBounds.last
            }
        }
            .flatten()
            .toSet()

        return points.flatMap { it.neighbors() }.count { it in validNeighbors }
    }

    private fun cluster(original:Set<Point3D>):List<Set<Point3D>> {
        val queue = mutableListOf<Point3D>()
        val clusters = mutableListOf<Set<Point3D>>()
        queue.addAll(original)
        while(queue.isNotEmpty()) {
            val point = queue.removeFirst()
            val reachable = Graphs.reachable(point) { it.neighbors().filter { n -> n in original } }
            queue.removeAll(reachable)
            clusters.add(reachable)
        }
        return clusters
    }
}