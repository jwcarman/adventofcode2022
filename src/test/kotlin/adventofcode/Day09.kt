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

import adventofcode.util.head
import adventofcode.util.readAsLines
import adventofcode.util.tail
import org.junit.Test
import kotlin.math.abs
import kotlin.test.assertEquals

typealias Rope = List<Day09.Point2D>

class Day09 {

    @Test
    fun example1() {
        assertEquals(13, calculatePart1(readAsLines("day09-example1.txt")))
    }

    @Test
    fun part1() {
        val instructions = readAsLines("day09.txt")
        assertEquals(6044, calculatePart1(instructions))
    }

    @Test
    fun example2() {
        assertEquals(36, calculatePart2(readAsLines("day09-example2.txt")))
    }

    @Test
    fun part2() {
        assertEquals(2384, calculatePart2(readAsLines("day09.txt")))
    }

    private fun calculatePart1(instructions: List<String>): Int {
        return ropeSimulation(2, instructions).size
    }

    private fun calculatePart2(instructions: List<String>): Int {
        return ropeSimulation(10, instructions).size
    }

    private fun Rope.moveHead(direction: Char): Rope {
        if(isEmpty()) {
            return emptyList()
        }
        val h = head().move(direction)
        return h + tail().convergeOn(h)
    }

    private fun Rope.convergeOn(head: Point2D): Rope {
        if (isEmpty()) {
            return emptyList()
        }
        val h = head().convergeOn(head)
        return h + tail().convergeOn(h)
    }


    private fun ropeSimulation(ropeLength: Int, instructions: List<String>): Set<Point2D> {
        var rope = buildRope(ropeLength)
        val visited = mutableSetOf<Point2D>()
        visited.add(rope.last())
        instructions.forEach { instruction ->
            val direction = instruction.substringBefore(' ')[0]
            val steps = instruction.substringAfter(' ').toInt()
            repeat(steps) {
                rope = rope.moveHead(direction)
                visited.add(rope.last())
            }
        }
        return visited
    }

    private fun buildRope(ropeLength: Int): Rope {
        if (ropeLength == 0) {
            return listOf()
        }
        return Point2D(0, 0) + buildRope(ropeLength - 1)
    }

    data class Point2D(private val x: Int, private val y: Int) {

        fun move(direction: Char): Point2D {
            return when (direction) {
                'U' -> Point2D(x, y + 1)
                'D' -> Point2D(x, y - 1)
                'L' -> Point2D(x - 1, y)
                'R' -> Point2D(x + 1, y)
                else -> {
                    throw IllegalArgumentException("Direction $direction not supported!")
                }
            }
        }

        operator fun plus(tail: List<Point2D>): List<Point2D> = listOf(this) + tail

        fun convergeOn(head: Point2D): Point2D {
            val dx = head.x - x
            val dy = head.y - y
            if (abs(dx) > 1 && abs(dy) > 1) {
                return Point2D(x + (dx / 2), y + (dy / 2))
            }
            if (abs(dx) > 1) {
                return Point2D(x + (dx / 2), head.y)
            }
            if (abs(dy) > 1) {
                return Point2D(head.x, y + (dy / 2))
            }
            return this
        }
    }
}