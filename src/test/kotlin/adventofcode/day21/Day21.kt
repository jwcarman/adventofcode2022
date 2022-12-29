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

package adventofcode.day21

import adventofcode.util.readAsString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day21 {
    @Test
    fun example1() {
        assertThat(calculatePart1(readAsString("day21-example.txt"))).isEqualTo(152L)
    }

    @Test
    fun part1() {
        assertThat(calculatePart1(readAsString("day21.txt"))).isEqualTo(194501589693264L)
    }

    @Test
    fun example2() {
        assertThat(calculatePart2(readAsString("day21-example.txt"))).isEqualTo(301L)
    }

    @Test
    fun part2() {
        assertThat(calculatePart2(readAsString("day21.txt"))).isEqualTo(3887609741189L)
    }

    fun calculatePart1(input:String): Long {
        val expressions = parseExpressions(input)
        val solver = MonkeyMath(expressions)
        return solver.evaluate("root")
    }

    fun calculatePart2(input:String): Long {
        val expressions = parseExpressions(input)
        val solver = MonkeyMath(expressions - "humn")
        return solver.solveEquation(expressions["root"]!!)
    }

    private fun parseExpressions(input: String) = input
        .lines()
        .map { it.replace(": ", ":").split(":") }
        .associate { it[0] to it[1] }
}