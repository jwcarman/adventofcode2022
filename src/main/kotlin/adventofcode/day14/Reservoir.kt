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

import adventofcode.util.geom.Line2D
import adventofcode.util.geom.Point2D

class Reservoir {

    private val blocked = mutableSetOf<Point2D>()

    fun addFloor() {
        val floorY = blocked.maxOf { it.y } + 2
        val floorXs = blocked.minOf { it.x }..blocked.maxOf { it.x }
        addRockStructure(listOf(
            Point2D(floorXs.first - 1000, 0),
            Point2D(floorXs.first - 1000, floorY),
            Point2D(floorXs.last + 1000, floorY),
            Point2D(floorXs.last + 1000, 0)
        ))
    }

    fun addRockStructure(coordinates: List<Point2D>) {
        if (coordinates.isNotEmpty()) {
            if (coordinates.size == 1) {
                blocked += coordinates.first()
            } else {
                blocked.addAll(coordinates.zipWithNext()
                    .map { (p1, p2) -> Line2D(p1, p2) }
                    .flatMap { it.points() })
            }
        }
    }

    private val candidatesCache = mutableMapOf<Point2D, List<Point2D>>()

    private fun candidatesOf(current: Point2D): List<Point2D> {
        return candidatesCache.computeIfAbsent(current) { c ->
            listOf(
                c.translate(0, 1),
                c.translate(-1, 1),
                c.translate(1, 1),
                c
            )
        }
    }

    private fun nextSandPosition(current: Point2D): Point2D {

        return candidatesOf(current).first { it !in blocked }
    }

    fun dropSand(start: Point2D): Boolean {
        val maxY = blocked.maxOf { it.y }
        var current = start
        while (current.y < maxY) {
            val next = nextSandPosition(current)
            if (next == current) {
                blocked.add(current)
                return true
            }
            current = next
        }
        return false
    }

    fun isBlocked(point: Point2D) = blocked.contains(point)

    fun isEmpty() = blocked.isEmpty()
}