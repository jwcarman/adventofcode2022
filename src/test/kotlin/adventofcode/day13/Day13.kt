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

package adventofcode.day13

import adventofcode.util.isNumeric
import adventofcode.util.readAsString
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private const val EMPTY_LIST = "[]"
private const val DIVIDER_PACKET_1 = "[[2]]"
private const val DIVIDER_PACKET_2 = "[[6]]"

class Day13 {

    @Test
    fun example1() {
        assertEquals(13, calculatePart1(readAsString("day13-example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(5330, calculatePart1(readAsString("day13.txt")))
    }

    @Test
    fun example2() {
        assertEquals(140, calculatePart2(readAsString("day13-example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(27648, calculatePart2(readAsString("day13.txt")))
    }
    private fun comparePackets(left: String, right: String): Int {
        when {
            left.isEmptyPacketList() && right.isEmptyPacketList() -> return 0
            left.isNumeric() && right.isNumeric() -> return left.toInt() - right.toInt()
            left.isNumeric() -> return comparePackets("[$left]", right)
            right.isNumeric() -> return comparePackets(left, "[$right]")
            left.isEmptyPacketList() -> return -1
            right.isEmptyPacketList() -> return 1
            else -> {
                val compare = comparePackets(left.packetListHead(), right.packetListHead())
                if (compare == 0) {
                    return comparePackets(left.packetListTail(), right.packetListTail())
                }
                return compare
            }
        }
    }
    private fun calculatePart1(input: String): Int {
        return input.split("\n\n")
            .asSequence()
            .map { it.split("\n") }
            .withIndex()
            .filter { comparePackets(it.value[0], it.value[1]) < 0 }
            .map { it.index + 1 }
            .sum()
    }

    private fun calculatePart2(input: String): Int {
        val lines = ("$DIVIDER_PACKET_1\n$DIVIDER_PACKET_2\n\n$input").lines()
            .filter { it.isNotEmpty() }
            .sortedWith { l, r -> comparePackets(l, r) }
        return (lines.indexOf(DIVIDER_PACKET_1) + 1) * (lines.indexOf(DIVIDER_PACKET_2) + 1)
    }

    private fun String.isEmptyPacketList(): Boolean = EMPTY_LIST == this

    private fun String.indexOfTopLevelComma(): Int {
        var level = 0
        for (i in indices) {
            when (get(i)) {
                '[' -> level++
                ']' -> level--
                ',' -> {
                    if (level == 1) {
                        return i
                    }
                }
            }
        }
        return -1
    }


    private fun String.packetListHead(): String {
        val ndx = indexOfTopLevelComma()
        if (ndx == -1) {
            return substring(1, length - 1)
        }
        return substring(1, ndx)
    }

    private fun String.packetListTail(): String {
        val ndx = indexOfTopLevelComma()
        if (ndx == -1) {
            return EMPTY_LIST
        }
        return "[${substring(ndx + 1)}"
    }
}