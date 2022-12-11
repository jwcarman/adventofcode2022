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

import adventofcode.util.readAsString
import adventofcode.util.removeAll
import org.junit.Test
import kotlin.test.assertEquals

class Day11 {

    @Test
    fun example1() {
        assertEquals(10605, calculatePart1(readAsString("day11-example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(316888, calculatePart1(readAsString("day11.txt")))
    }

    @Test
    fun example2() {
        assertEquals(2713310158L, calculatePart2(readAsString("day11-example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(35270398814L, calculatePart2(readAsString("day11.txt")))
    }

    private fun calculatePart1(input: String): Long {
        val monkeys = parseMonkeys(input)
        return playKeepAway(20, monkeys) { it / 3 }
    }

    private fun calculatePart2(input: String): Long {
        val monkeys = parseMonkeys(input)
        val productOfDivisors = monkeys.map { it.testDivisor }.reduce(Long::times)
        return playKeepAway(10000, monkeys) { it % productOfDivisors }
    }

    private fun playKeepAway(rounds: Int, monkeys: List<Monkey>, worryLevelFn: (Long) -> Long): Long {
        val inspectionCounts = monkeys.map { 0L }.toMutableList()
        repeat(rounds) {
            monkeys.forEachIndexed { i, monkey ->
                while (monkey.items.isNotEmpty()) {
                    val (recipient, worryLevel) = monkey.inspectItem(worryLevelFn)
                    monkeys[recipient].items.add(worryLevel)
                    inspectionCounts[i]++
                }
            }
        }
        return monkeyBusiness(inspectionCounts)
    }

    private fun monkeyBusiness(inspectionCounts: MutableList<Long>) =
        inspectionCounts.sortedDescending().take(2).reduce(Long::times)

    private fun parseMonkeys(input: String) = input.split("\n\n").map { parseMonkey(it) }

    data class Monkey(
        val items: MutableList<Long>,
        val operation: (Long) -> Long,
        val testDivisor: Long,
        val pass: Int,
        val fail: Int
    ) {
        fun inspectItem(worryLevelFn: (Long) -> Long): Pair<Int, Long> {
            val value = worryLevelFn(operation(items.removeFirst()))
            return if (value % testDivisor == 0L) {
                Pair(pass, value)
            } else {
                Pair(fail, value)
            }
        }
    }

    private fun parseMonkey(input: String): Monkey {
        val fields = input.replace("\n", "\t")
            .removeAll(
                "  Starting items: ",
                "  Operation: new = ",
                "  Test: divisible by ",
                "    If true: throw to monkey ",
                "    If false: throw to monkey "
            )
            .split("\t")

        return Monkey(
            items = fields[1].removeAll(" ").split(",").map { it.toLong() }.toMutableList(),
            operation = parseOperation(fields[2]),
            testDivisor = fields[3].toLong(),
            pass = fields[4].toInt(),
            fail = fields[5].toInt()
        )
    }

    private fun parseOperation(expression: String): (Long) -> Long {
        return when {
            expression == "old * old" -> { old -> old * old }
            expression.startsWith("old *") -> { old -> old * parseOperand(expression) }
            else -> { old -> old + parseOperand(expression) }
        }
    }

    private fun parseOperand(expression: String) = expression.substringAfterLast(' ').toLong()
}