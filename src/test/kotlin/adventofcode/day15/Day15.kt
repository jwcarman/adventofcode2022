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

package adventofcode.day15

import adventofcode.util.geom.plane.Point2D
import adventofcode.util.head
import adventofcode.util.readAsString
import adventofcode.util.removeAll
import adventofcode.util.tail
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.math.min
import kotlin.test.assertEquals

class Day15 {

    private fun Point2D.tuningFrequency(): Long = 4000000L * x + y

    @Test
    fun example1() {
        assertEquals(26, calculatePart1(10, readAsString("day15-example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(5144286, calculatePart1(2000000, readAsString("day15.txt")))
    }

    @Test
    fun example2() {
        assertEquals(56000011, calculatePart2(20, readAsString("day15-example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(10229191267339L, calculatePart2(4000000, readAsString("day15.txt")))
    }

    private fun calculatePart1(y: Int, input: String): Int {
        val sensors = parseSensors(input)
        val beaconXs = sensors.map { it.beaconPoint }.filter { it.y == y }.map { it.x }
        return sensors.flatMap { it.horizontalScanRange(y) }.distinct().count { it !in beaconXs }
    }

    private fun calculatePart2(maxCoordinate: Int, input: String): Long {
        val sensors = parseSensors(input)
        val coordinates = 0..maxCoordinate

        val (y, range) = coordinates.asSequence()
            .map { y ->
                val ranges = sensors
                    .asSequence()
                    .map { it.horizontalScanRange(y) }
                    .filter { !it.isEmpty() }
                    .filter { it.overlapsWith(coordinates) }
                    .map { it.clip(coordinates) }
                    .sortedBy { it.first }
                    .toList()
                val merged = merge(ranges)
                Pair(y, merged)
            }
            .first { it.second != coordinates }

        return if (range.first == 0) {
            Point2D(range.last + 1, y).tuningFrequency()
        } else {
            Point2D(0, y).tuningFrequency()
        }
    }

    private fun IntRange.mergeWith(right: IntRange) = min(first, right.first)..max(last, right.last)

    @Test
    fun checkMergeWith() {
        assertThat((0..2).mergeWith(3..4)).isEqualTo(0..4)
        assertThat((0..5).mergeWith(3..4)).isEqualTo(0..5)
        assertThat((0..5).mergeWith(-1..4)).isEqualTo(-1..5)
    }

    private fun IntRange.clip(bounds: IntRange) = IntRange(max(first, bounds.first), min(last, bounds.last))

    @Test
    fun checkClip() {
        assertThat((0..5).clip(1..2)).isEqualTo(1..2)
        assertThat((0..1).clip(1..2)).isEqualTo(1..1)
        assertThat((2..7).clip(1..3)).isEqualTo(2..3)
    }

    private fun IntRange.isAdjacentTo(right: IntRange) = right.first <= last + 1

    @Test
    fun checkIsAdjacentTo() {
        assertThat((0..4).isAdjacentTo(5..6)).isTrue
        assertThat((0..4).isAdjacentTo(4..6)).isTrue
        assertThat((0..4).isAdjacentTo(3..6)).isTrue
        assertThat((0..4).isAdjacentTo(6..6)).isFalse
    }

    private fun IntRange.overlapsWith(other: IntRange) = first <= other.last && last >= other.first

    @Test
    fun checkOverlapsWith() {
        assertThat((3..4).overlapsWith(2..5)).isTrue
        assertThat((2..5).overlapsWith(3..4)).isTrue
        assertThat((3..4).overlapsWith(3..4)).isTrue

        assertThat((3..4).overlapsWith(4..6)).isTrue
        assertThat((3..4).overlapsWith(3..6)).isTrue


        assertThat((3..4).overlapsWith(2..3)).isTrue
        assertThat((3..4).overlapsWith(2..4)).isTrue

        assertThat((3..4).overlapsWith(2..5)).isTrue
        assertThat((3..4).overlapsWith(3..4)).isTrue

        assertThat((3..4).overlapsWith(5..6)).isFalse
        assertThat((3..4).overlapsWith(2..2)).isFalse
        assertThat((3..4).overlapsWith(IntRange.EMPTY)).isFalse
    }

    private fun merge(ranges: List<IntRange>): IntRange {
        return merge(ranges.head(), ranges.tail())
    }

    private fun merge(accumulator: IntRange, rest: List<IntRange>): IntRange {
        if (rest.isEmpty() || !accumulator.isAdjacentTo(rest.head())) {
            return accumulator
        }
        return merge(accumulator.mergeWith(rest.head()), rest.tail())
    }


    private fun parseSensors(input: String): MutableSet<Sensor> {
        val sensors = mutableSetOf<Sensor>()
        input.removeAll("Sensor at x=")
            .replace(", y=", " ")
            .replace(": closest beacon is at x=", " ")
            .lines()
            .forEach { line ->
                val splits = line.split(" ")
                val sensor = Point2D(splits[0].toInt(), splits[1].toInt())
                val beacon = Point2D(splits[2].toInt(), splits[3].toInt())
                sensors.add(Sensor(sensor, beacon))
            }
        return sensors
    }

}