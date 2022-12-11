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
import adventofcode.util.readAsString
import adventofcode.util.tail
import org.junit.Test
import kotlin.test.assertEquals

class Day05 {
    @Test
    fun example1() {
        val input = readAsString("day05-example.txt")
        assertEquals("CMZ", calculatePart1(input))
    }

    @Test
    fun part1() {
        val input = readAsString("day05.txt")
        assertEquals("QPJPLMNNR", calculatePart1(input))
    }

    @Test
    fun example2() {
        val input = readAsString("day05-example.txt")
        assertEquals("MCD", calculatePart2(input))
    }

    @Test
    fun part2() {
        val input = readAsString("day05.txt")
        assertEquals("BQDNWJPVJ", calculatePart2(input))
    }


    private fun calculatePart1(input: String): String {
        return calculateAnswer(input, CrateMover9000())
    }

    private fun calculatePart2(input: String): String {
        return calculateAnswer(input, CrateMover9001())
    }

    private fun calculateAnswer(input: String, crane: Crane): String {
        val splits = input.split("\n\n")
        val stacksMap = parseStacksMap(splits[0])
        splits[1].lines()
            .forEach { instruction ->
                val (amount, srcNum, destNum) = Regex("move (\\d+) from (\\d+) to (\\d+)").matchEntire(instruction)!!.destructured
                val src = stacksMap[srcNum.toInt()]!!
                val dest = stacksMap[destNum.toInt()]!!
                crane.moveCrates(src, dest, amount.toInt())
            }
        return stacksMap.keys.sorted()
            .map { k -> stacksMap[k]?.head() }
            .joinToString(separator = "")
    }


    private fun parseStacksMap(input: String): Map<Int, CargoStack> {
        val stacksMap = mutableMapOf<Int, CargoStack>()
        input
            .replace("]    ", "] [-]")
            .replace("    [", "[-] [")
            .replace("     ", " [-] ")
            .replace("[", "")
            .replace("]", "")
            .lines().reversed().tail()
            .forEach { line ->
                line.split(' ').forEachIndexed{ index, label ->
                    if("-" != label) {
                        val stack = stacksMap.getOrPut(index + 1, ::mutableListOf)
                        stack.push(label.first())
                    }
                }
            }
        return stacksMap.toMap()
    }

    interface Crane {
        fun moveCrates(src: CargoStack, dest: CargoStack, amount: Int)
    }

    class CrateMover9000 : Crane {
        override fun moveCrates(src: CargoStack, dest: CargoStack, amount: Int) {
            repeat(amount) {
                dest.push(src.pop())
            }
        }
    }

    class CrateMover9001 : Crane {
        override fun moveCrates(src: CargoStack, dest: CargoStack, amount: Int) {
            val tmp = mutableListOf<Char>()
            repeat(amount) {
                tmp.push(src.pop())
            }
            repeat(amount) {
                dest.push(tmp.pop())
            }
        }
    }


}

typealias CargoStack = MutableList<Char>

fun CargoStack.push(element: Char) = add(0, element)

fun CargoStack.pop() = removeFirst()

